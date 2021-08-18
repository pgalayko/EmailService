import com.typesafe.config.{ Config, ConfigFactory }
object ConfigReader {

  val configForConsumer: Config = ConfigFactory.load("application.conf").getConfig("consumer.properties")

  def getInfo(configPath: String, stringPath: String): String = {
    configForConsumer.getConfig(configPath).getString(stringPath)
  }

  /**
  Configuration for consumer method
   */
  val serverKey: String   = getInfo("server", "server-key")
  val serverValue: String = getInfo("server", "server-value")

  val key_deserializerKey: String   = getInfo("deserializer.key-deserializer", "key")
  val key_deserializerValue: String = getInfo("deserializer.key-deserializer", "value")

  val value_deserializerKey: String   = getInfo("deserializer.value-deserializer", "key")
  val value_deserializerValue: String = getInfo("deserializer.value-deserializer", "value")

  val groupIdKey: String   = getInfo("group-id", "key")
  val groupIdValue: String = getInfo("group-id", "value")

  val consumerConfig: ConsumerConfig = ConsumerConfig(
    Server(serverKey, serverValue),
    KeyDeserializer(key_deserializerKey, key_deserializerValue),
    ValueDeserializer(value_deserializerKey, value_deserializerValue),
    GroupId(groupIdKey, groupIdValue)
  )

  /**
  Configuration for email sender actor
   */
  val configForSender: Config = ConfigFactory.load("application.conf").getConfig("email-service")
  def getSenderInfo(stringPath: String): String = {
    configForSender.getConfig("sender").getString(stringPath)
  }

  val hostName: String  = getSenderInfo("host-name")
  val smtpPort: Int     = configForSender.getConfig("sender").getInt("smtp-port")
  val username: String  = getSenderInfo("username")
  val password: String  = getSenderInfo("password")
  val sslOnCon: Boolean = configForSender.getConfig("sender").getBoolean("ssl-on-connect")
  val from: String      = getSenderInfo("email-from")
  val subject: String   = getSenderInfo("email-subject")

  val senderConfig: EmailConfig = EmailConfig(hostName, smtpPort, username, password, sslOnCon, from, subject)
}
case class ConsumerConfig(
  server: Server,
  keyDeserializer: KeyDeserializer,
  valueDeserializer: ValueDeserializer,
  groupId: GroupId)
case class Server(key: String, value: String)
case class KeyDeserializer(key: String, value: String)
case class ValueDeserializer(key: String, value: String)
case class GroupId(key: String, value: String)

case class EmailConfig(
  hostName: String,
  smtpPort: Int,
  userName: String,
  password: String,
  sslOnCon: Boolean,
  from: String,
  subject: String)
