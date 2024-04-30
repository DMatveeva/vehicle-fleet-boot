package ru.dmatveeva.vehiclefleetboot.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
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

import java.util.List;

@RestController
@RequestMapping(value = RestTrackGeneratorController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestTrackGeneratorController {


    static final String REST_URL = "/track/generate";

    @Autowired VehicleRepository vehicleRepository;

    @Autowired TrackGeneratorService trackGeneratorService;

    @Autowired EnterpriseRepository enterpriseRepository;


    /*@PostMapping("tracks/generate/vehicle/{id}")
    public ResponseEntity<Boolean> generateTrack(
            @PathVariable int id,
            @RequestParam double[] start,
            @RequestParam double[] finish,
            @RequestParam int lengthKm,
            @RequestParam int maxSpeedKmH,
            @RequestParam int maxAccelerationMSS) {

        Vehicle vehicle = vehicleRepository.findById(id).orElseThrow();
        trackGeneratorService.generateTrackInRealTime(vehicle, start, finish, lengthKm, maxSpeedKmH, maxAccelerationMSS);

        return ResponseEntity.ok().build();
    }*/

    @PostMapping("tracks/generate/vehicle/{id}")
    public ResponseEntity<Boolean> generateTrack( @PathVariable int id, @RequestBody String body) {

        Vehicle vehicle = vehicleRepository.findById(id).orElseThrow();
        trackGeneratorService.generateTrackInRealTime(body);

        return ResponseEntity.ok().build();
    }

    @PostMapping("tracks/generate/losangeles")
    public ResponseEntity<Boolean> generateTracksForLosAngeles(@RequestParam int numTracks) {

        Enterprise enterprise = enterpriseRepository.findById(100002).orElseThrow();
        List<Vehicle> vehicles = vehicleRepository.findAllByEnterprise(enterprise);
        trackGeneratorService.generateTracksForVehicles(vehicles, numTracks, "Los Angeles");
        return ResponseEntity.ok().build();
    }

    @PostMapping("tracks/generate/losangeles/vehicle/{vehicleId}")
    public ResponseEntity<Boolean> generateTracksForLosAngeles(@PathVariable int vehicleId, @RequestParam int numTracks) {
        Vehicle vehicle = vehicleRepository.findById(vehicleId).orElseThrow();
        trackGeneratorService.generateTracksForVehicles(List.of(vehicle), numTracks, "Los Angeles");
        return ResponseEntity.ok().build();
    }

    @PostMapping("tracks/generate/lasvegas/")
    public ResponseEntity<Boolean> generateTracksForLasVegas(@RequestParam int numTracks) {
        Enterprise enterprise = enterpriseRepository.findById(100003).orElseThrow();
        List<Vehicle> vehicles = vehicleRepository.findAllByEnterprise(enterprise);
        trackGeneratorService.generateTracksForVehicles(vehicles, numTracks, "Las Vegas");
        return ResponseEntity.ok().build();
    }
}
