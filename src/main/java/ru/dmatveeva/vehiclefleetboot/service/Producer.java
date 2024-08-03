package ru.dmatveeva.vehiclefleetboot.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import ru.dmatveeva.vehiclefleetboot.to.GenerateRequestDto;

@Slf4j
@Component
public class Producer {

    @Value("${topic.vehicle.name}")
    String vehicleTopic;
    @Value("${topic.track.name}")
    String trackTopic;
    @Autowired
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public Producer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendGenerateTrackMessage(GenerateRequestDto generateRequestDto) {
        kafkaTemplate.send(trackTopic, generateRequestDto);
        log.info("Generate message sent, {}", generateRequestDto.getVehicleId());
    }
}