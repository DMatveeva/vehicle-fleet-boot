package ru.dmatveeva.vehiclefleetboot.web.rest.generator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dmatveeva.vehiclefleetboot.entity.Track;
import ru.dmatveeva.vehiclefleetboot.entity.vehicle.Vehicle;
import ru.dmatveeva.vehiclefleetboot.repository.EnterpriseRepository;
import ru.dmatveeva.vehiclefleetboot.repository.TrackRepository;
import ru.dmatveeva.vehiclefleetboot.repository.VehicleRepository;
import ru.dmatveeva.vehiclefleetboot.service.GeneratorProducer;
import ru.dmatveeva.vehiclefleetboot.service.generate.ReactiveTrackGeneratorService;
import ru.dmatveeva.vehiclefleetboot.to.GenerateRequestDto;
import ru.dmatveeva.vehiclefleetboot.to.TrackTo;
import ru.dmatveeva.vehiclefleetboot.util.TrackUtils;

import java.time.LocalDateTime;

@RestController
@RequestMapping(value = "/rest/generator/track", produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class RestTrackGeneratorController {

    @Autowired
    VehicleRepository vehicleRepository;

    @Autowired
    ReactiveTrackGeneratorService trackGeneratorService;

    @Autowired
    EnterpriseRepository enterpriseRepository;

    @Autowired
    TrackRepository trackRepository;

    @Autowired
    GeneratorProducer generatorProducer;


    @PostMapping("/generate")
    public ResponseEntity<TrackTo> generateTrack(@RequestBody GenerateRequestDto generateRequestDto) {
        Vehicle vehicle = vehicleRepository.findById(generateRequestDto.getVehicleId()).orElseThrow();
        Track track = new Track();
        track.setVehicle(vehicle);
        track.setStarted(LocalDateTime.now());
        Track savedTrack = trackRepository.save(track);
        generateRequestDto.setTrackId(savedTrack.getId());

        log.trace("Generating track {} for vehicle {}", savedTrack.getId(), generateRequestDto.getVehicleId());

        generatorProducer.sendGenerateTrackMessage(generateRequestDto);
        return ResponseEntity.ok(TrackUtils.getTrackTo(savedTrack));
    }

//    @PostMapping("/generate/losangeles")
//    public ResponseEntity<Boolean> generateTracksForLosAngeles(@RequestParam int numTracks) {
//        Enterprise enterprise = enterpriseRepository.findById(100002).orElseThrow();
//        List<Vehicle> vehicles = vehicleRepository.findAllByEnterprise(enterprise);
//        trackGeneratorService.generateTrackRealTime(vehicles, numTracks, "Los Angeles");
//        return ResponseEntity.ok().build();
//    }
//
//    @PostMapping("/generate/lasvegas")
//    public ResponseEntity<Boolean> generateTracksForLasVegas(@RequestParam int numTracks) {
//        Enterprise enterprise = enterpriseRepository.findById(100003).orElseThrow();
//        List<Vehicle> vehicles = vehicleRepository.findAllByEnterprise(enterprise);
//        trackGeneratorService.generateTrackRealTime(vehicles, numTracks, "Las Vegas");
//        return ResponseEntity.ok().build();
//    }
}