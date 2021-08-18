import ConfigReader._
import akka.actor.ActorSystem

import java.time.Duration.ofSeconds
import java.util
import scala.jdk.CollectionConverters.IterableHasAsScala

object Main extends App {
  lazy val emailContainer: Map[Int, String] = Map((1111, "pgalayko@gmail.com"), (1112, "2@_.com"))

  val system       = ActorSystem("Email-Service")
  val emailService = system.actorOf(EmailService.props(), "email-service")

  val consumer = Consumer.apply(consumerConfig)
  consumer.subscribe(util.Collections.singletonList("test"))

  while (true) {
    val records = consumer.poll(ofSeconds(2))
    records.asScala.foreach(
      Main.emailService ! _.value
    )
  }
}
