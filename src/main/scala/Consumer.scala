import EmailService.MessageFromKafka
import akka.actor.ActorSystem
import org.apache.kafka.clients.consumer.{ConsumerRecord, KafkaConsumer}

import java.time.Duration.ofSeconds
import java.util
import java.util.Properties
import scala.jdk.CollectionConverters.IterableHasAsScala

object Consumer {

  def apply(consumerConfig: ConsumerConfig): KafkaConsumer[String, MessageFromKafka] = {
// TODO: case class ConsumerConfig()
    val props = new Properties()
    props.put(consumerConfig.server.key, consumerConfig.server.value)

    props.put(consumerConfig.keyDeserializer.key, consumerConfig.keyDeserializer.value)
    props.put(consumerConfig.valueDeserializer.key, consumerConfig.valueDeserializer.value)
    props.put(consumerConfig.groupId.key, consumerConfig.groupId.value)

    new KafkaConsumer[String, MessageFromKafka](props)
  }
}