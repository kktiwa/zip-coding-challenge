package au.com.zip.consumer

import java.time.Duration

import au.com.zip.admin.{createBaseProps, monthlyAllStatusAggregatesStore, monthlyDeclinesAggregatesTopic, monthlySuccessAggregatesTopic}
import au.com.zip.encoders.{MonthlyCardGroupingKey, SimpleCaseClassDeserializer, SimpleCaseClassSerializer}
import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.kstream.Printed
import org.apache.kafka.streams.scala.kstream._
import org.apache.kafka.streams.scala.ImplicitConversions._
import org.apache.kafka.streams.scala.kstream.Consumed
import org.apache.kafka.streams.scala.{Serdes => ScalaSerdes}
import org.apache.kafka.streams.{KafkaStreams, StreamsBuilder}

object MonthlyAggregateConsumerStream extends App {
  new MonthlyAggregateConsumerStream
}

class MonthlyAggregateConsumerStream {

  import org.apache.kafka.streams.StreamsConfig

  val builder = new StreamsBuilder()
  val props = createBaseProps()
  props.put(StreamsConfig.APPLICATION_ID_CONFIG, "monthly-agg-consumer-stream")
  props.put(StreamsConfig.CACHE_MAX_BYTES_BUFFERING_CONFIG, "0")

  implicit val cardGroupingKeySerde = Serdes.serdeFrom(new SimpleCaseClassSerializer[MonthlyCardGroupingKey], new SimpleCaseClassDeserializer[MonthlyCardGroupingKey])

  val success: KStream[MonthlyCardGroupingKey, Long] = builder.stream(monthlySuccessAggregatesTopic, Consumed.`with`(cardGroupingKeySerde, ScalaSerdes.Long))
  val declines: KStream[MonthlyCardGroupingKey, Long] = builder.stream(monthlyDeclinesAggregatesTopic, Consumed.`with`(cardGroupingKeySerde, ScalaSerdes.Long))
  val allStatus: KStream[MonthlyCardGroupingKey, Long] = builder.stream(monthlyAllStatusAggregatesStore, Consumed.`with`(cardGroupingKeySerde, ScalaSerdes.Long))

  success.print(Printed.toSysOut())
  declines.print(Printed.toSysOut())
  allStatus.print(Printed.toSysOut())

  val streams = new KafkaStreams(builder.build(), props)
  streams.start()

  sys.ShutdownHookThread {
    streams.close(Duration.ofSeconds(10))
  }

}