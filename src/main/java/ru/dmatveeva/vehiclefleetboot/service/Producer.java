package ru.dmatveeva.vehiclefleetboot.service;

import com.fasterxml.jackson.databind.ObjectMapper;
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
    private final ObjectMapper objectMapper;
    @Autowired
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public Producer(ObjectMapper objectMapper, KafkaTemplate<String, Object> kafkaTemplate) {
        this.objectMapper = objectMapper;
        this.kafkaTemplate = kafkaTemplate;
    }

   /* public void sendMessage(VehicleTo vehicleTo) throws JsonProcessingException {
        String vehicleAsString = objectMapper.writeValueAsString(vehicleTo);
        kafkaTemplate.send(vehicleTopic, vehicleAsString);

        log.info("msg sent");
    }*/

    public void sendGenerateTrackMessage(GenerateRequestDto generateRequestDto) {
        kafkaTemplate.send(trackTopic, generateRequestDto);
        log.info("Generate message sent, {}", generateRequestDto.getId());
    }
}
