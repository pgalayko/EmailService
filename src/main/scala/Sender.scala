import akka.actor.{ Actor, ActorLogging, Props }
import org.apache.commons.mail.{ DefaultAuthenticator, SimpleEmail }

object Sender {
  case class EmailParams(emailAndID: EmailAndId, stage: String, status: String, date: String)

  def props(): Props = Props(new Sender())
}

class Sender extends Actor with ActorLogging {
  import Sender._
  import ConfigReader._
  override def receive: Receive = {
    case EmailParams(email, stage, status, date) =>
      log.info("Trying to send a message")
      sendEmail(EmailParams(email, stage, status, date))
      log.info("Message delivered")
    case x => log.warning(s"Received unknown message: $x.")
  }

  def sendEmail(emailParams: EmailParams): String = {

    val email = new SimpleEmail()
    email.setHostName(senderConfig.hostName)
    email.setSmtpPort(senderConfig.smtpPort)
    email.setAuthenticator(new DefaultAuthenticator(senderConfig.userName, senderConfig.password))
    email.setSSLOnConnect(senderConfig.sslOnCon)
    email.setFrom(senderConfig.from)
    email.setSubject(senderConfig.subject)
    email.setMsg(
      s"Information on the processing of user data. User ID: ${emailParams.emailAndID.userID}. Stage: ${emailParams.stage}, " +
        s"Status: ${emailParams.stage}, Date: ${emailParams.date}."
    )
    email.addTo(emailParams.emailAndID.clientEmail)
    email.send()
  }
}
