package ru.dmatveeva.vehiclefleetboot.web.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.dmatveeva.vehiclefleetboot.entity.Enterprise;
import ru.dmatveeva.vehiclefleetboot.entity.Manager;
import ru.dmatveeva.vehiclefleetboot.entity.vehicle.Vehicle;
import ru.dmatveeva.vehiclefleetboot.repository.ManagerRepository;
import ru.dmatveeva.vehiclefleetboot.repository.VehicleRepository;
import ru.dmatveeva.vehiclefleetboot.service.VehicleService;
import ru.dmatveeva.vehiclefleetboot.to.VehicleTo;
import ru.dmatveeva.vehiclefleetboot.util.SecurityUtils;
import ru.dmatveeva.vehiclefleetboot.util.VehicleUtils;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

@RestController
@RequestMapping(value = RestVehicleController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class RestVehicleController {

    static final String REST_URL = "/rest/vehicle";
    @Autowired private VehicleRepository vehicleRepository;
    @Autowired private ManagerRepository managerRepository;
    @Autowired private VehicleService vehicleService;

    @GetMapping("/{id}")
    public ResponseEntity<?> get(TimeZone timezone, @PathVariable Integer id) {
        Vehicle vehicle;
        var vehicleOpt = vehicleRepository.findById(id);
        if (vehicleOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Vehicle not found");
        }
        vehicle = vehicleOpt.get();
        Manager manager = managerRepository.findByLogin(SecurityUtils.getUserName());
        List<Enterprise> enterprises = manager.getEnterprises();
        boolean hasAccess = enterprises.contains(vehicle.getEnterprise());
        return hasAccess ? ResponseEntity.ok(VehicleUtils.getVehicleToWithLocalTime(vehicle, timezone.getID())) : ResponseEntity.status(HttpStatus.FORBIDDEN).body("Access Denied");
    }

    @GetMapping()
    public List<VehicleTo> getAll(TimeZone timezone) {
        log.info("Get all vehicles");

        Manager manager = managerRepository.findByLogin(SecurityUtils.getUserName());
        List<Enterprise> enterprises = manager.getEnterprises();
        List<Vehicle> vehicles = new ArrayList<>();
        for (Enterprise enterprise: enterprises) {
            vehicles.addAll(vehicleRepository.findAllByEnterprise(enterprise));
        }
        log.info("All vehicles {}", vehicles);
        return VehicleUtils.getVehicleTosWithLocalTime(vehicles, timezone.getID());
    }

    @GetMapping("/pageable")
    public List<VehicleTo> getAll(TimeZone timezone,
                                           @RequestParam int offset, @RequestParam int pageSize) {
        List<VehicleTo> vehicleTos = new ArrayList<>();
        Manager manager = managerRepository.findByLogin(SecurityUtils.getUserName());
        List<Enterprise> enterprises = manager.getEnterprises();
        for (Enterprise enterprise: enterprises) {
            PageRequest p = PageRequest.of(offset, pageSize);
            List<Vehicle> vehiclesByEnterprise = vehicleRepository.getAllByEnterprise(enterprise, p).stream().toList();
            vehicleTos.addAll(VehicleUtils.getVehicleTosWithLocalTime(vehiclesByEnterprise, timezone.getID()));
            if (vehiclesByEnterprise.size() == pageSize) {
                break;
            }
        }
        return vehicleTos;
    }

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VehicleTo> create(TimeZone timezone, @RequestBody VehicleTo vehicleTo) {
        log.info("Create new vehicle");
        return createOrUpdate(vehicleTo, timezone);
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VehicleTo> update(TimeZone timezone, @RequestBody VehicleTo vehicleTo) {
        return createOrUpdate(vehicleTo, timezone);
    }

    private  ResponseEntity<VehicleTo> createOrUpdate(VehicleTo vehicleTo, TimeZone timezone) {

        Vehicle created = vehicleService.create(vehicleTo, timezone);
        VehicleTo createdTo = VehicleUtils.getVehicleTo(created);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(createdTo);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        Manager manager = managerRepository.findByLogin(SecurityUtils.getUserName());
        Vehicle vehicle = vehicleRepository.findById(id).orElseThrow();
        VehicleUtils.checkEnterpriseIsConsistent(manager, vehicle.getEnterprise().getId());
        vehicleRepository.deleteById(id);
    }
}
