package au.com.zip.stream

import java.time.Duration
import au.com.zip.admin._
import au.com.zip.encoders._
import au.com.zip.util.Logging.log
import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.scala.{Serdes => ScalaSerdes}
import org.apache.kafka.streams.{KafkaStreams, StreamsBuilder}
import org.apache.kafka.streams.kstream.Materialized
import org.apache.kafka.streams.scala.ImplicitConversions._
import org.apache.kafka.streams.scala.kstream.{Consumed, _}

object DailyAggregatesStream extends App {

  import org.apache.kafka.streams.StreamsConfig

  val props = createBaseProps()
  props.put(StreamsConfig.APPLICATION_ID_CONFIG, "daily-agg-stream")
  props.put(StreamsConfig.CACHE_MAX_BYTES_BUFFERING_CONFIG, "0")

  implicit val cardRequestKeySerde = Serdes.serdeFrom(new SimpleCaseClassSerializer[CardRequestKey], new SimpleCaseClassDeserializer[CardRequestKey])
  implicit val authorizationResponseSerde = Serdes.serdeFrom(new SimpleCaseClassSerializer[CardAuthorizationResponse], new SimpleCaseClassDeserializer[CardAuthorizationResponse])
  implicit val cardGroupingKeySerde = Serdes.serdeFrom(new SimpleCaseClassSerializer[DailyCardGroupingKey], new SimpleCaseClassDeserializer[DailyCardGroupingKey])

  val builder = new StreamsBuilder()
  val cardSuccessStream: KStream[CardRequestKey, CardAuthorizationResponse] = builder.stream(successfulTransactionsTopic, Consumed.`with`(cardRequestKeySerde, authorizationResponseSerde))
  val cardDeclinedStream: KStream[CardRequestKey, CardAuthorizationResponse] = builder.stream(declinedTransactionsTopic, Consumed.`with`(cardRequestKeySerde, authorizationResponseSerde))

  cardSuccessStream
    .map((req, _) => (DailyCardGroupingKey(req.cardNumber, req.txnDateTime), 1L))
    .groupByKey(Grouped.`with`(cardGroupingKeySerde, ScalaSerdes.Long))
    .count()(Materialized.as(dailySuccessAggregateStore))
    .toStream
    .map((x, y) => (x.cardNumber, y))
    .to(dailySuccessAggregatesTopic)(Produced.`with`(ScalaSerdes.String, ScalaSerdes.Long))

  log(s"Successfully written to topic $dailySuccessAggregatesTopic")

  cardDeclinedStream
    .map((req, _) => (DailyCardGroupingKey(req.cardNumber, req.txnDateTime), 1L))
    .groupByKey(Grouped.`with`(cardGroupingKeySerde, ScalaSerdes.Long))
    .count()(Materialized.as(dailyDeclinesAggregateStore))
    .toStream
    .map((x, y) => (x.cardNumber, y))
    .to(dailyDeclinesAggregatesTopic)(Produced.`with`(ScalaSerdes.String, ScalaSerdes.Long))

  log(s"Successfully written to topic $dailyDeclinesAggregatesTopic")

  val streams = new KafkaStreams(builder.build(), props)
  streams.start()

  sys.ShutdownHookThread {
    streams.close(Duration.ofSeconds(10))
  }

}
