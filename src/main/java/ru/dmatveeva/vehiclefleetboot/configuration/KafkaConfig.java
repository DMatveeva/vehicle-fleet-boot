package ru.dmatveeva.vehiclefleetboot.configuration;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic vehicleTopic() {
        return TopicBuilder
                .name("t.vehicle.generate")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic trackTopic() {
        return TopicBuilder
                .name("t.track.generate")
                .partitions(1)
                .replicas(1)
                .build();
    }

}