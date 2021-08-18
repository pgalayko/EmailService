import EmailService.MessageFromKafka
import org.apache.kafka.clients.consumer.KafkaConsumer

import java.util.Properties

object Consumer {

  def apply(consumerConfig: ConsumerConfig): KafkaConsumer[String, MessageFromKafka] = {
    val props = new Properties()
    props.put(consumerConfig.server.key, consumerConfig.server.value)

    props.put(consumerConfig.keyDeserializer.key, consumerConfig.keyDeserializer.value)
    props.put(consumerConfig.valueDeserializer.key, consumerConfig.valueDeserializer.value)
    props.put(consumerConfig.groupId.key, consumerConfig.groupId.value)

    new KafkaConsumer[String, MessageFromKafka](props)
  }
}
