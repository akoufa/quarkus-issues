package org.acme

import jakarta.enterprise.context.ApplicationScoped
import org.apache.avro.generic.GenericRecord
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.eclipse.microprofile.reactive.messaging.Acknowledgment
import org.eclipse.microprofile.reactive.messaging.Incoming

@ApplicationScoped
class KafkaConfig {
    @Incoming("notifications")
    @Acknowledgment(Acknowledgment.Strategy.POST_PROCESSING)
    suspend fun receiveRecord(record: ConsumerRecord<String, GenericRecord>) {
        println("Received record: ${record.value()}")
    }
}
