import akka.actor.{Actor, ActorLogging, Props}
import org.apache.commons.mail.{DefaultAuthenticator, SimpleEmail}

object Sender {
  case class EmailParams(emailAndID: EmailAndId, stage: String, status: String, date: String)

  def props(): Props = Props(new Sender())
}

class Sender extends Actor with ActorLogging {
  import Sender._
  override def receive: Receive = {
    case EmailParams(email, stage, status, date) =>
      log.info("Trying to send a message")
      sendEmail(EmailParams(email, stage, status, date))
      log.info("Message delivered")
    case x => log.warning(s"Received unknown message: $x.")
  }

  def sendEmail(emailParams: EmailParams): String = {
//    val sysConfig = context.system.settings.config
    val readStringConfig: String => String = context.system.settings.config.getString

    val userName = readStringConfig("email-service.sender.username")
    val userPassword = readStringConfig("email-service.sender.password")

    // TODO: Вытащить все основные параметры для отправки в конфиг
    val email = new SimpleEmail()
    email.setHostName("smtp.gmail.com")
    email.setSmtpPort(465)
    email.setAuthenticator(new DefaultAuthenticator(userName, userPassword))
    email.setSSLOnConnect(true)
    email.setFrom("pgalayko@gmail.com")
    email.setSubject("User Data processing")
    email.setMsg(s"Information on the processing of user data. User ID: ${emailParams.emailAndID.userID}. Stage: ${emailParams.stage}, " +
      s"Status: ${emailParams.stage}, Date: ${emailParams.date}.")
    email.addTo(emailParams.emailAndID.clientEmail)
    email.send()
  }
}
