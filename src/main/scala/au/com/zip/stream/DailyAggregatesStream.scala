package au.com.zip.stream

import java.time.Duration
import java.util.Properties
import au.com.zip.admin._
import au.com.zip.encoders._
import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.scala.{Serdes => ScalaSerdes}
import org.apache.kafka.streams.{KafkaStreams, StreamsBuilder}
import org.apache.kafka.streams.kstream.{Consumed, Materialized, TimeWindows, WindowedSerdes}
import org.apache.kafka.streams.scala.ImplicitConversions._
import org.apache.kafka.streams.scala.kstream._

object DailyAggregatesStream extends App {

  import org.apache.kafka.streams.StreamsConfig

  val props = new Properties()
  props.put(StreamsConfig.APPLICATION_ID_CONFIG, "AuthorisationV3")
  props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
  props.put(StreamsConfig.CACHE_MAX_BYTES_BUFFERING_CONFIG, "0")


  implicit val cardRequestKeySerde = Serdes.serdeFrom(new SimpleCaseClassSerializer[CardRequestKey], new SimpleCaseClassDeserializer[CardRequestKey])
  implicit val cardAuthorizationResponseSerde = Serdes.serdeFrom(new SimpleCaseClassSerializer[CardAuthorizationResponse], new SimpleCaseClassDeserializer[CardAuthorizationResponse])

  val builder = new StreamsBuilder()
  val cardSuccessStream: KStream[CardRequestKey, CardAuthorizationResponse] = builder.stream(successfulTransactionsTopic, Consumed.`with`(cardRequestKeySerde, cardAuthorizationResponseSerde))
  val cardDeclinedStream: KStream[CardRequestKey, CardAuthorizationResponse] = builder.stream(declinedTransactionsTopic, Consumed.`with`(cardRequestKeySerde, cardAuthorizationResponseSerde))

  val timeWindow = TimeWindows.of(Duration.ofDays(1)).grace(Duration.ofHours(5))
  val windowSerde = new WindowedSerdes.TimeWindowedSerde(ScalaSerdes.String)

  val streams = new KafkaStreams(builder.build(), props)
  streams.start()

  //((req.cardNumber, req.txnDateTime)
  cardSuccessStream
    .map((req, _) => (req.cardNumber, 1L))
    .groupByKey(Grouped.`with`(ScalaSerdes.String, ScalaSerdes.Long))
    .windowedBy(timeWindow)
    .count()(Materialized.as("daily-success-aggregate-store"))
    .toStream
    .to(dailySuccessAggregatesTopic)(Produced.`with`(windowSerde, ScalaSerdes.Long))

  println(s"Successfully written to topic $dailySuccessAggregatesTopic")

  cardDeclinedStream
    .map((req, _) => (req.cardNumber, 1L))
    .groupByKey(Grouped.`with`(ScalaSerdes.String, ScalaSerdes.Long))
    .windowedBy(timeWindow)
    .count()(Materialized.as("daily-declines-aggregate-store"))
    .toStream
    .to(dailyDeclinesAggregatesTopic)(Produced.`with`(windowSerde, ScalaSerdes.Long))

  println(s"Successfully written to topic $dailyDeclinesAggregatesTopic")

  sys.ShutdownHookThread {
    streams.close(Duration.ofSeconds(10))
  }

}
