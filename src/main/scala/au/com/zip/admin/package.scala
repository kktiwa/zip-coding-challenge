package au.com.zip

import java.util.Properties
import org.apache.kafka.clients.producer.ProducerConfig

package object admin {

  val cardRequestTopic = "card-request"
  val cardAuthorizedTopic = "card-authorization-response"
  val successfulTransactionsTopic = "card-success"
  val declinedTransactionsTopic = "card-declines"
  val undefinedTransactionsTopic = "card-undefined"
  val allTransactionsTopic = "all-transactions"

  //store/topics for aggregated metrics
  val dailySuccessAggregatesTopic = "daily-success-aggregates"
  val dailyDeclinesAggregatesTopic = "daily-declined-aggregates"
  val dailySuccessAggregateStore = "daily-success-aggregate-store"
  val dailyDeclinesAggregateStore = "daily-declined-aggregate-store"

  val monthlySuccessAggregatesTopic = "monthly-success-aggregates"
  val monthlySuccessAggregatesStore = "monthly-success-aggregate-store"
  val monthlyDeclinesAggregatesStore = "monthly-declines-aggregate-store"
  val monthlyDeclinesAggregatesTopic = "monthly-declines-aggregates"
  val monthlyAllStatusAggregatesStore = "monthly-all-aggregates-store"
  val monthlyAllStatusAggregatesTopic = "monthly-all-status-aggregates"

  def createBaseProps(): Properties = {
    val props = new Properties()
    props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
    props
  }

  val applicationId = "card-payments"

  //TODO:Use Enums
  val approved = "Approved"
  val declined = "Declined"
  val undefined = "Undefined"

}
