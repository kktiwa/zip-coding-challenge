package au.com.zip.consumer

import java.time.Duration
import scala.collection.JavaConverters._
import au.com.zip.admin._
import au.com.zip.encoders._
import au.com.zip.service.{Notification, NotificationService}
import au.com.zip.util.Logging.log
import org.apache.kafka.clients.consumer.{ConsumerConfig, ConsumerRecords, KafkaConsumer}

object AuthorizedTransactionConsumer extends App {
  new PaymentGatewayConsumer
}

class PaymentGatewayConsumer {

  val props = createBaseProps()
  props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "au.com.zip.encoders.SimpleCaseClassDeserializer")
  props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "au.com.zip.encoders.SimpleCaseClassDeserializer")
  props.put(ConsumerConfig.GROUP_ID_CONFIG, "payment-gateway-consumer")
  val topics = Seq(cardAuthorizedTopic)

  val consumer: KafkaConsumer[CardRequestKey, GatewayResponse] = new KafkaConsumer[CardRequestKey, GatewayResponse](props)
  consumer.subscribe(topics.asJava)
  log(s"Subscribing to topics: ${topics.mkString(", ")}")

  while (true) {
    val records: ConsumerRecords[CardRequestKey, GatewayResponse] = consumer.poll(Duration.ofMillis(100))
    records.iterator().asScala.foreach(record => {
      val key = record.key()
      val response = record.value()
      //Send notification to customers for success and decline status
      if (Seq(approved, declined).contains(response.status)) {
        NotificationService.sendNotification(Notification(key.customerId, key.txnDateTime, response))
      }
    })
  }
}
