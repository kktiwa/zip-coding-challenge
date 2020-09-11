package au.com.zip

import java.util.Properties

package object admin {

  val cardRequestTopic = "card-request"
  val cardAuthorizedTopic = "card-authorization-response"
  val successfulTransactionsTopic = "card-success"
  val declinedTransactionsTopic = "card-declines"
  val undefinedTransactionsTopic = "card-undefined"

  //topics for aggregated metrics
  val dailySuccessAggregatesTopic = "daily-success-aggregates"
  val dailyDeclinesAggregatesTopic = "daily-declined-aggregates"

  def createBaseProps(): Properties = {
    val props = new Properties()
    props.put("bootstrap.servers", "localhost:9092")
    props
  }

  //TODO:Use Enums
  val approved = "Approved"
  val declined = "Declined"
  val undefined = "Undefined"

}
