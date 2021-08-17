import EmailService.MessageFromKafka
import akka.actor.ActorSystem

object Main extends App {
  lazy val emailContainer: Map[Int, String] = Map((1111 -> "pgalayko@gmail.com"), (1112 -> "2@_.com"))

  val system = ActorSystem("Email-Service")
  val emailService = system.actorOf(EmailService.props(), "email-service")

  emailService ! MessageFromKafka(FullID(1111, 12345), "stage", "status", "date")

}
