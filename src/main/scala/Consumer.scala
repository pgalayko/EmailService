import EmailService.MessageFromKafka
import akka.actor.ActorSystem
import org.apache.kafka.clients.consumer.KafkaConsumer

import java.util
import scala.collection.JavaConverters._

object Consumer extends App {

  import java.util.Properties

  lazy val emailContainer: Map[Int, String] = Map((1111 -> "pgalayko@gmail.com"), (1112 -> "2@_.com"))

  val system = ActorSystem("Email-Service")
  val emailService = system.actorOf(EmailService.props(), "email-service")

  val TOPIC = "test"

  val props = new Properties()
  props.put("bootstrap.servers", "localhost:9092")

  props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer")
  props.put("value.deserializer", "CustomDeserializer")
  props.put("group.id", "something")

  val consumer = new KafkaConsumer[String, MessageFromKafka](props)

  consumer.subscribe(util.Collections.singletonList(TOPIC))

  while (true) {
    val records = consumer.poll(50)
    for (record <- records.asScala) {
      Main.emailService ! record
    }
  }
}