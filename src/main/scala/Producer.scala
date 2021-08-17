import EmailService.MessageFromKafka

object Producer extends App {

  import org.apache.kafka.clients.producer._

  import java.util.Properties

  val props = new Properties()
  props.put("bootstrap.servers", "localhost:9092")

  props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer")
  props.put("value.serializer", "CustomSerializer")

  val producer = new KafkaProducer[String, MessageFromKafka](props)

  val TOPIC = "test"

  val info = new ProducerRecord(TOPIC, "key", MessageFromKafka(FullID(1111, 1112), "stage", "status", "date"))
  producer.send(info)

  producer.close()
}