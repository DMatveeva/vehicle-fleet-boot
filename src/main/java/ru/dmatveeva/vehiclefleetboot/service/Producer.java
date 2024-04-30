package ru.dmatveeva.vehiclefleetboot.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import ru.dmatveeva.vehiclefleetboot.to.VehicleTo;

@Slf4j
@Component
public class Producer {

    @Value("${topic.vehicle.name}")
    String vehicleTopic;

    @Value("${topic.track.name}")
    String trackTopic;

    private final ObjectMapper objectMapper;

    private final KafkaTemplate<String, String> kafkaTemplate;

    public Producer(ObjectMapper objectMapper, KafkaTemplate<String, String> kafkaTemplate) {
        this.objectMapper = objectMapper;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(VehicleTo vehicleTo) throws JsonProcessingException {
        String vehicleAsString = objectMapper.writeValueAsString(vehicleTo);
        kafkaTemplate.send(vehicleTopic, vehicleAsString);

        log.info("msg sent");
    }

    public void sendGenerateTrackMessage(String body) throws JsonProcessingException {

        kafkaTemplate.send(trackTopic, body);

        log.info("msg sent");
    }
}
