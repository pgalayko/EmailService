import akka.actor.{Actor, ActorLogging, Props}
import org.apache.commons.mail.{DefaultAuthenticator, SimpleEmail}

object Sender {
  def props(): Props = Props(new Sender())
}

class Sender extends Actor with ActorLogging{
  override def receive: Receive = {
    case EmailParams(email, stage, status, date) =>
      log.info("Trying to send a message")
      sendEmail(EmailParams(email, stage, status, date))
      log.info("Message delivered")
  }

  def sendEmail(emailParams: EmailParams) = {



    val email = new SimpleEmail()
    email.setHostName("smtp.gmail.com")
    email.setSmtpPort(465)
    email.setAuthenticator(new DefaultAuthenticator("pgalayko@gmail.com", "pasha/090819988"))
    email.setSSLOnConnect(true)
    email.setFrom("pgalayko@gmail.com")
    email.setSubject("TestMail")
    email.setMsg(s"Information on the processing of user data. User ID: ${emailParams.emailAndID._2}. Stage: ${emailParams.stage}, " +
      s"Status: ${emailParams.stage}, Date: ${emailParams.date}.")
    email.addTo(emailParams.emailAndID._1)
    email.send()
  }
}
