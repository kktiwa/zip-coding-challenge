package au.com.zip.service

import au.com.zip.encoders.GatewayResponse
import au.com.zip.util.Logging.log

case class Notification(customerId: String,
                        txnDate: String,
                        cardAuthorizationResponse: GatewayResponse
                       )

object NotificationService {

  //This would ideally be a microservice which lives outside of Kafka and is just invoked via the REST endpoint
  def sendNotification(notification: Notification): Unit = {
    log(s"Your transaction dated ${notification.txnDate} was processed with status ${notification.cardAuthorizationResponse.status} due to ${notification.cardAuthorizationResponse.reason}")
  }

}
