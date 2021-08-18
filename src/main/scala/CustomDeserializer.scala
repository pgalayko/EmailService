import EmailService.MessageFromKafka
import org.apache.kafka.common.serialization.Deserializer

import java.io.{ ByteArrayInputStream, ObjectInputStream }
import java.util

class CustomDeserializer extends Deserializer[MessageFromKafka] {

  override def configure(configs: util.Map[String, _], isKey: Boolean): Unit = {}

  override def deserialize(topic: String, bytes: Array[Byte]): MessageFromKafka = {
    val byteIn = new ByteArrayInputStream(bytes)
    val objIn  = new ObjectInputStream(byteIn)
    val obj    = objIn.readObject().asInstanceOf[MessageFromKafka]
    byteIn.close()
    objIn.close()
    obj
  }

  override def close(): Unit = {}
}
