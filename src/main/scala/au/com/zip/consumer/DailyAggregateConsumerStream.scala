package au.com.zip.consumer

import java.time.Duration
import org.apache.kafka.streams.scala.{Serdes => ScalaSerdes}
import au.com.zip.admin.{createBaseProps, dailyDeclinesAggregatesTopic, dailySuccessAggregatesTopic}
import au.com.zip.encoders.{DailyCardGroupingKey, SimpleCaseClassDeserializer, SimpleCaseClassSerializer}
import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.streams.{KafkaStreams, StreamsBuilder, StreamsConfig}
import org.apache.kafka.streams.kstream.Printed
import org.apache.kafka.streams.scala.kstream._

object DailyAggregateConsumerStream extends App {
  new DailyAggregateConsumerStream
}

class DailyAggregateConsumerStream {

  val builder = new StreamsBuilder()
  val props = createBaseProps()
  props.put(StreamsConfig.APPLICATION_ID_CONFIG, "daily-agg-consumer-stream")
  props.put(StreamsConfig.CACHE_MAX_BYTES_BUFFERING_CONFIG, "0")

  implicit val cardGroupingKeySerde = Serdes.serdeFrom(new SimpleCaseClassSerializer[DailyCardGroupingKey], new SimpleCaseClassDeserializer[DailyCardGroupingKey])

  builder.stream(dailySuccessAggregatesTopic, Consumed.`with`(ScalaSerdes.String, ScalaSerdes.Long))
    .print(Printed.toSysOut())

  builder.stream(dailyDeclinesAggregatesTopic, Consumed.`with`(ScalaSerdes.String, ScalaSerdes.Long))
    .print(Printed.toSysOut())

  val streams = new KafkaStreams(builder.build(), props)
  streams.start()

  sys.ShutdownHookThread {
    streams.close(Duration.ofSeconds(10))
  }

}
