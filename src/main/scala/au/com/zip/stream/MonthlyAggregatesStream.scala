package au.com.zip.stream

import java.time.Duration
import au.com.zip.admin._
import au.com.zip.encoders._
import au.com.zip.util.Logging.log
import au.com.zip.util.UtilityFunctions._
import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.kstream.{Consumed, Materialized}
import org.apache.kafka.streams.scala.ImplicitConversions._
import org.apache.kafka.streams.scala.kstream._
import org.apache.kafka.streams.scala.{Serdes => ScalaSerdes}
import org.apache.kafka.streams.{KafkaStreams, StreamsBuilder}

object MonthlyAggregatesStream extends App {

  import org.apache.kafka.streams.StreamsConfig

  val props = createBaseProps()
  props.put(StreamsConfig.APPLICATION_ID_CONFIG, "monthly-agg-stream")
  props.put(StreamsConfig.CACHE_MAX_BYTES_BUFFERING_CONFIG, "0")

  implicit val cardRequestKeySerde = Serdes.serdeFrom(new SimpleCaseClassSerializer[CardRequestKey], new SimpleCaseClassDeserializer[CardRequestKey])
  implicit val cardAuthorizationResponseSerde = Serdes.serdeFrom(new SimpleCaseClassSerializer[CardAuthorizationResponse], new SimpleCaseClassDeserializer[CardAuthorizationResponse])
  implicit val cardGroupingKeySerde = Serdes.serdeFrom(new SimpleCaseClassSerializer[MonthlyCardGroupingKey], new SimpleCaseClassDeserializer[MonthlyCardGroupingKey])

  val builder = new StreamsBuilder()
  val cardSuccessStream: KStream[CardRequestKey, CardAuthorizationResponse] = builder.stream(successfulTransactionsTopic, Consumed.`with`(cardRequestKeySerde, cardAuthorizationResponseSerde))
  val cardDeclinedStream: KStream[CardRequestKey, CardAuthorizationResponse] = builder.stream(declinedTransactionsTopic, Consumed.`with`(cardRequestKeySerde, cardAuthorizationResponseSerde))
  val cardAllStatusStream: KStream[CardRequestKey, CardAuthorizationResponse] = builder.stream(allTransactionsTopic, Consumed.`with`(cardRequestKeySerde, cardAuthorizationResponseSerde))

  cardSuccessStream
    .map((req, _) => (MonthlyCardGroupingKey(req.cardNumber, getMonth(req.txnDateTime)), 1L))
    .groupByKey(Grouped.`with`(cardGroupingKeySerde, ScalaSerdes.Long))
    .count()(Materialized.as(monthlySuccessAggregatesStore))
    .toStream
    .to(monthlySuccessAggregatesTopic)(Produced.`with`(cardGroupingKeySerde, ScalaSerdes.Long))

  log(s"Successfully written to topic $monthlySuccessAggregatesTopic")

  cardDeclinedStream
    .map((req, _) => (MonthlyCardGroupingKey(req.cardNumber, getMonth(req.txnDateTime)), 1L))
    .groupByKey(Grouped.`with`(cardGroupingKeySerde, ScalaSerdes.Long))
    .count()(Materialized.as(monthlyDeclinesAggregatesStore))
    .toStream
    .to(monthlyDeclinesAggregatesTopic)(Produced.`with`(cardGroupingKeySerde, ScalaSerdes.Long))

  log(s"Successfully written to topic $monthlyDeclinesAggregatesTopic")

  cardAllStatusStream
    .map((req, _) => (MonthlyCardGroupingKey(req.cardNumber, getMonth(req.txnDateTime)), 1L))
    .groupByKey(Grouped.`with`(cardGroupingKeySerde, ScalaSerdes.Long))
    .count()(Materialized.as(monthlyAllStatusAggregatesStore))
    .toStream
    .to(monthlyAllStatusAggregatesTopic)(Produced.`with`(cardGroupingKeySerde, ScalaSerdes.Long))

  log(s"Successfully written to topic $monthlyAllStatusAggregatesTopic")

  val streams = new KafkaStreams(builder.build(), props)
  streams.start()

  sys.ShutdownHookThread {
    streams.close(Duration.ofSeconds(10))
  }

}
