package ru.dmatveeva.vehiclefleetboot.web.rest.generator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.dmatveeva.vehiclefleetboot.entity.Enterprise;
import ru.dmatveeva.vehiclefleetboot.entity.vehicle.Vehicle;
import ru.dmatveeva.vehiclefleetboot.repository.EnterpriseRepository;
import ru.dmatveeva.vehiclefleetboot.repository.VehicleRepository;
import ru.dmatveeva.vehiclefleetboot.service.generate.TrackGeneratorService;
import ru.dmatveeva.vehiclefleetboot.to.GenerateRequestDto;

import java.util.List;

@RestController
@RequestMapping(value = "/rest/generator/track", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestTrackGeneratorController {

    @Autowired
    VehicleRepository vehicleRepository;

    @Autowired
    TrackGeneratorService trackGeneratorService;

    @Autowired
    EnterpriseRepository enterpriseRepository;


    @PostMapping("/generate")
    public ResponseEntity<Boolean> generateTrack(@RequestBody GenerateRequestDto generateRequestDto) {
        trackGeneratorService.generateTrackInRealTime(generateRequestDto);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/generate/losangeles")
    public ResponseEntity<Boolean> generateTracksForLosAngeles(@RequestParam int numTracks) {
        Enterprise enterprise = enterpriseRepository.findById(100002).orElseThrow();
        List<Vehicle> vehicles = vehicleRepository.findAllByEnterprise(enterprise);
        trackGeneratorService.generateTracksForVehicles(vehicles, numTracks, "Los Angeles");
        return ResponseEntity.ok().build();
    }

    @PostMapping("/generate/lasvegas")
    public ResponseEntity<Boolean> generateTracksForLasVegas(@RequestParam int numTracks) {
        Enterprise enterprise = enterpriseRepository.findById(100003).orElseThrow();
        List<Vehicle> vehicles = vehicleRepository.findAllByEnterprise(enterprise);
        trackGeneratorService.generateTracksForVehicles(vehicles, numTracks, "Las Vegas");
        return ResponseEntity.ok().build();
    }
}