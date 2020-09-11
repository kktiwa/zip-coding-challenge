package au.com.zip.consumer

import java.time.Duration
import scala.collection.JavaConverters._
import au.com.zip.admin._
import au.com.zip.encoders.{CardAuthorizationResponse, CardRequestKey}
import org.apache.kafka.clients.consumer.{ConsumerRecords, KafkaConsumer}

object AuthorizedTransactionConsumer extends App {
  new PaymentGatewayConsumer
}

class PaymentGatewayConsumer {

  val props = createBaseProps()
  props.put("key.deserializer", "au.com.zip.encoders.SimpleCaseClassDeserializer")
  props.put("value.deserializer", "au.com.zip.encoders.SimpleCaseClassDeserializer")
  props.put("group.id", "TransactionConsumerTest")
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
