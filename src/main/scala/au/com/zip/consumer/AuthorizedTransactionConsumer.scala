package au.com.zip.consumer

import java.time.Duration
import scala.collection.JavaConverters._
import au.com.zip.admin._
import au.com.zip.encoders._
import org.apache.kafka.clients.consumer.{ConsumerConfig, ConsumerRecords, KafkaConsumer}

object AuthorizedTransactionConsumer extends App {
  new PaymentGatewayConsumer
}

class PaymentGatewayConsumer {

  val props = createBaseProps()
  props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "au.com.zip.encoders.SimpleCaseClassDeserializer")
  props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "au.com.zip.encoders.SimpleCaseClassDeserializer")
  props.put(ConsumerConfig.GROUP_ID_CONFIG, "TransactionConsumerTest")
  val topics = Seq(cardAuthorizedTopic)

  val consumer: KafkaConsumer[CardRequestKey, CardAuthorizationResponse] = new KafkaConsumer[CardRequestKey, CardAuthorizationResponse](props)
  consumer.subscribe(topics.asJava)
  println(s"Subscribing to topics: ${topics.mkString(", ")}")

  while (true) {
    val records: ConsumerRecords[CardRequestKey, CardAuthorizationResponse] = consumer.poll(Duration.ofMillis(100))
    records.iterator().asScala.foreach(record => {
      val key = record.key()
      println(s"Customer ${key.customerId} \t Request ${key.requestId} is ${record.value().status} ${Option(record.value().reason).map(s => s"Reason : $s").getOrElse("")}")
    })
  }
}
