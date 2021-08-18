import EmailService.{ MessageFromKafka, WrongClientID }
import akka.actor.{ Actor, ActorLogging, Props }

object EmailService {
  case class MessageFromKafka(fullID: FullID, stage: String, status: String, date: String)
  case class WrongClientID(id: FullID) extends IllegalStateException(s"Wrong client ID - $id.")

  def props(): Props = Props(new EmailService())
}
// TODO: Реализовать отбор по стадиям обработки
class EmailService extends Actor with ActorLogging {
  override def receive: Receive = {
    case MessageFromKafka(fullID, stage, status, date) =>
      val emailAndID = Main.emailContainer.get(fullID.clientID) match {
        case Some(clientEmail) => EmailAndId(clientEmail, fullID.userID)
        case None              => throw WrongClientID(fullID)
      }
      context.actorOf(Sender.props(), "sender") ! Sender.EmailParams(emailAndID, stage, status, date)
  }
}

case class EmailAndId(clientEmail: String, userID: Int)
