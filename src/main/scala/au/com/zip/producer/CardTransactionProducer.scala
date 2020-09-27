package au.com.zip.producer

import java.util.UUID
import au.com.zip.admin._
import au.com.zip.encoders._
import au.com.zip.util.Logging.log
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig, ProducerRecord}

object CardTransactionProducer extends App {
  new CardTransactionProducer
}

class CardTransactionProducer {

  val props = createBaseProps()
  props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "au.com.zip.encoders.SimpleCaseClassSerializer")
  props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "au.com.zip.encoders.SimpleCaseClassSerializer")
  props.put(ProducerConfig.ACKS_CONFIG, "all")
  val producer: KafkaProducer[CardRequestKey, CardRequestValue] = new KafkaProducer(props)

  val topic = cardRequestTopic
  val id = UUID.randomUUID().toString

  try {
    Seq(
      new ProducerRecord(topic, CardRequestKey("CustID2", "1", "1", "2020-09-20"), CardRequestValue(10.0, "VendorA")),
      new ProducerRecord(topic, CardRequestKey("CustID2", "2", "2", "2020-10-22"), CardRequestValue(20.0, "VendorB")),
      new ProducerRecord(topic, CardRequestKey("CustID2", "3", "3", "2020-12-23"), CardRequestValue(30.0, "VendorC")),
      new ProducerRecord(topic, CardRequestKey("CustID2", "4", "4", "2020-03-24"), CardRequestValue(20.0, "VendorA")),
      new ProducerRecord(topic, CardRequestKey("CustID3", "5", "5", "2020-05-25"), CardRequestValue(20.0, "VendorA")),
      new ProducerRecord(topic, CardRequestKey("CustID1", "6", "6", "2020-06-26"), CardRequestValue(20.0, "VendorB")),
      new ProducerRecord(topic, CardRequestKey("CustID2", "7", "7", "2020-07-27"), CardRequestValue(20.0, "VendorC"))
    ).foreach(e => {
      producer.send(e)
      log(s"Card transaction sent message ${e.key().requestId}")
    })
  }
  catch {
    case e: Exception => producer.abortTransaction()
      e.printStackTrace()
  }

  producer.close()

}