package au.com.zip.producer

import au.com.zip.admin._
import au.com.zip.encoders._
import au.com.zip.util.Logging.log
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig, ProducerRecord}

object PaymentGatewayProducer extends App {
  new PaymentGatewayProducer
}

class PaymentGatewayProducer {

  val producerProperties = createBaseProps()
  producerProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "au.com.zip.encoders.SimpleCaseClassSerializer")
  producerProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "au.com.zip.encoders.SimpleCaseClassSerializer")
  producerProperties.put(ProducerConfig.ACKS_CONFIG, "all")
  val producer: KafkaProducer[CardRequestKey, GatewayResponse] = new KafkaProducer(producerProperties)

  val topic = cardAuthorizedTopic

  try {
    Seq(
      new ProducerRecord(topic, CardRequestKey("CustID2", "1", "101", "2020-09-20"), GatewayResponse("1", "101", "2020-01-20", approved, "")),
      new ProducerRecord(topic, CardRequestKey("CustID2", "2", "201", "2020-10-22"), GatewayResponse("2", "201", "2020-01-22", approved, "")),
      new ProducerRecord(topic, CardRequestKey("CustID2", "2", "101", "2020-09-21"), GatewayResponse("2", "101", "2020-09-21", approved, "")),
      new ProducerRecord(topic, CardRequestKey("CustID2", "3", "301", "2020-12-23"), GatewayResponse("3", "301", "2020-01-23", declined, "")),
      new ProducerRecord(topic, CardRequestKey("CustID2", "4", "401", "2020-03-24"), GatewayResponse("4", "401", "2020-01-24", approved, "")),
      new ProducerRecord(topic, CardRequestKey("CustID3", "5", "501", "2020-05-25"), GatewayResponse("5", "501", "2020-01-25", undefined, "")),
      new ProducerRecord(topic, CardRequestKey("CustID1", "6", "601", "2020-06-26"), GatewayResponse("6", "601", "2020-01-26", approved, "")),
      new ProducerRecord(topic, CardRequestKey("CustID2", "7", "701", "2020-07-27"), GatewayResponse("7", "701", "2020-01-27", declined, ""))
    ).foreach(e => {
      producer.send(e)
      log(s"Payment gateway sent message ${e.key().requestId}")
    })
  }
  catch {
    case e: Exception => producer.abortTransaction()
      e.printStackTrace()
  }

  producer.close()

}
