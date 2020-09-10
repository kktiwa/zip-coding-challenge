package au.com.zip.admin

import org.apache.kafka.clients.admin.{AdminClient, NewTopic}
import scala.collection.JavaConverters._

object KafkaAdmin extends App {

  val props = createBaseProps()
  val adminClient = AdminClient.create(props)

  createTopic()

  adminClient.listTopics().names().get().asScala.foreach(println)

  def createTopic(): Unit = {
    val topics = Seq(
      new NewTopic(cardRequestTopic, 2, 1),
      new NewTopic(cardAuthorizedTopic, 2, 1),
      new NewTopic(successfulTransactionsTopic, 2, 1),
      new NewTopic(declinedTransactionsTopic, 2, 1),
      new NewTopic(undefinedTransactionsTopic, 2, 1),
      new NewTopic(dailyDeclinesAggregatesTopic, 2, 1),
      new NewTopic(dailySuccessAggregatesTopic, 2, 1)
    ).asJava

    val values = adminClient.createTopics(topics).values()
    values.values().asScala.foreach(_.get())
  }

}
