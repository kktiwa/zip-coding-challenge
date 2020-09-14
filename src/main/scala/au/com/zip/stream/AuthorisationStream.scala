package au.com.zip.stream

import java.time.Duration
import au.com.zip.admin._
import au.com.zip.encoders._
import org.apache.kafka.streams.kstream._
import org.apache.kafka.streams.scala.ImplicitConversions._
import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.{KafkaStreams, StreamsBuilder}
import org.apache.kafka.streams.kstream.Consumed
import org.apache.kafka.streams.scala.kstream.{KStream, KTable}


object AuthorisationStream extends App {

  import org.apache.kafka.streams.StreamsConfig

  val props = createBaseProps()
  props.put(StreamsConfig.APPLICATION_ID_CONFIG, applicationId)
  props.put(StreamsConfig.CACHE_MAX_BYTES_BUFFERING_CONFIG, "0")

  implicit val stringSerde = Serdes.String
  implicit val cardRequestKeySerde = Serdes.serdeFrom(new SimpleCaseClassSerializer[CardRequestKey], new SimpleCaseClassDeserializer[CardRequestKey])
  implicit val cardRequestValueSerde = Serdes.serdeFrom(new SimpleCaseClassSerializer[CardRequestValue], new SimpleCaseClassDeserializer[CardRequestValue])
  implicit val gatewayResponseSerde = Serdes.serdeFrom(new SimpleCaseClassSerializer[GatewayResponse], new SimpleCaseClassDeserializer[GatewayResponse])
  implicit val authorizationResponseSerde = Serdes.serdeFrom(new SimpleCaseClassSerializer[CardAuthorizationResponse], new SimpleCaseClassDeserializer[CardAuthorizationResponse])

  val builder = new StreamsBuilder()
  val cardRequestStream: KStream[CardRequestKey, CardRequestValue] = builder.stream(cardRequestTopic, Consumed.`with`(cardRequestKeySerde, cardRequestValueSerde))
  val cardResponseStream: KStream[CardRequestKey, GatewayResponse] = builder.stream(cardAuthorizedTopic, Consumed.`with`(cardRequestKeySerde, gatewayResponseSerde))

  val successfulTransactions: KStream[CardRequestKey, CardAuthorizationResponse] = cardRequestStream
    .join(cardResponseStream.filter((_, b) => b.status == approved))(joinResult, JoinWindows.of(Duration.ofMinutes(1)))(Joined.`with`(cardRequestKeySerde, cardRequestValueSerde, gatewayResponseSerde))

  successfulTransactions.to(successfulTransactionsTopic)(Produced.`with`(cardRequestKeySerde, authorizationResponseSerde))
  println(s"Successfully written to topic $successfulTransactionsTopic")

  val declinedTransactions: KStream[CardRequestKey, CardAuthorizationResponse] = cardRequestStream
    .join(cardResponseStream.filter((_, b) => b.status == declined))(joinResult, JoinWindows.of(Duration.ofMinutes(1)))(Joined.`with`(cardRequestKeySerde, cardRequestValueSerde, gatewayResponseSerde))

  declinedTransactions.to(declinedTransactionsTopic)(Produced.`with`(cardRequestKeySerde, authorizationResponseSerde))
  println(s"Successfully written to topic $declinedTransactionsTopic")

  val allTransactions = cardRequestStream
    .join(cardResponseStream)(joinResult, JoinWindows.of(Duration.ofMinutes(1)))(Joined.`with`(cardRequestKeySerde, cardRequestValueSerde, gatewayResponseSerde))

  allTransactions.to(allTransactionsTopic)(Produced.`with`(cardRequestKeySerde, authorizationResponseSerde))
  println(s"Successfully written to topic $allTransactionsTopic")

  val streams = new KafkaStreams(builder.build(), props)
  streams.start()

  def joinResult(requestValue: CardRequestValue, gatewayResponse: GatewayResponse): CardAuthorizationResponse = {
    CardAuthorizationResponse(
      gatewayResponse.cardNumber,
      gatewayResponse.requestId,
      requestValue.value,
      requestValue.vendor,
      gatewayResponse.status,
      gatewayResponse.reason
    )
  }

  sys.ShutdownHookThread {
    streams.close(Duration.ofSeconds(10))
  }
}
