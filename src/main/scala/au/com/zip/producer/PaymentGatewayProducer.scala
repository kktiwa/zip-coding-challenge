package au.com.zip.producer

import au.com.zip.admin._
import au.com.zip.encoders.{CardAuthorizationResponse, CardRequestKey}
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig, ProducerRecord}

object PaymentGatewayProducer extends App {
  new PaymentGatewayProducer
}

class PaymentGatewayProducer {

  val producerProperties = createBaseProps()
  producerProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "au.com.zip.encoders.SimpleCaseClassSerializer")
  producerProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "au.com.zip.encoders.SimpleCaseClassSerializer")
  producerProperties.put(ProducerConfig.ACKS_CONFIG, "all")

  val topic = cardAuthorizedTopic

  val producer: KafkaProducer[CardRequestKey, CardAuthorizationResponse] = new KafkaProducer(producerProperties)

  try {
    Seq(
      new ProducerRecord(topic, CardRequestKey("CustID2", "1", "1", "2020-01-20"), CardAuthorizationResponse("1", approved, "")),
      new ProducerRecord(topic, CardRequestKey("CustID2", "2", "2", "2020-01-22"), CardAuthorizationResponse("2", approved, "")),
      new ProducerRecord(topic, CardRequestKey("CustID2", "3", "3", "2020-01-23"), CardAuthorizationResponse("3", declined, "")),
      new ProducerRecord(topic, CardRequestKey("CustID2", "4", "4", "2020-01-24"), CardAuthorizationResponse("4", approved, "")),
      new ProducerRecord(topic, CardRequestKey("CustID3", "5", "5", "2020-01-25"), CardAuthorizationResponse("5", undefined, "")),
      new ProducerRecord(topic, CardRequestKey("CustID1", "6", "6", "2020-01-26"), CardAuthorizationResponse("6", approved, "")),
      new ProducerRecord(topic, CardRequestKey("CustID2", "7", "7", "2020-01-27"), CardAuthorizationResponse("7", declined, ""))
    ).foreach(e => {
      producer.send(e)
      println(s"Sent Message ${e.key().requestId}")
    })
  }
  catch {
    case e: Exception => producer.abortTransaction()
      e.printStackTrace()
  }

  producer.close()

}
