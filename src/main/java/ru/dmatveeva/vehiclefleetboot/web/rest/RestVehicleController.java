package ru.dmatveeva.vehiclefleetboot.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.dmatveeva.vehiclefleetboot.entity.Enterprise;
import ru.dmatveeva.vehiclefleetboot.entity.Manager;
import ru.dmatveeva.vehiclefleetboot.entity.vehicle.Vehicle;
import ru.dmatveeva.vehiclefleetboot.repository.ManagerRepository;
import ru.dmatveeva.vehiclefleetboot.repository.VehicleRepository;
import ru.dmatveeva.vehiclefleetboot.service.VehicleService;
import ru.dmatveeva.vehiclefleetboot.service.generate.VehicleGeneratorService;
import ru.dmatveeva.vehiclefleetboot.to.VehicleGeneratorParamsTo;
import ru.dmatveeva.vehiclefleetboot.to.VehicleTo;
import ru.dmatveeva.vehiclefleetboot.util.VehicleUtils;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = RestVehicleController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestVehicleController {

    Logger logger = LoggerFactory.getLogger(RestVehicleController.class);

    static final String REST_URL = "/rest/vehicles";
    private final VehicleGeneratorService vehicleGeneratorService;


    private final VehicleRepository vehicleRepository;


    public RestVehicleController(VehicleGeneratorService vehicleGeneratorService, VehicleRepository vehicleRepository) {
        this.vehicleGeneratorService = vehicleGeneratorService;
        this.vehicleRepository = vehicleRepository;
    }

    @Autowired
    ManagerRepository managerRepository;

    @GetMapping()
    public List<VehicleTo> getAll() {
        logger.info("Get all vehicles");
        List<Vehicle> vehicles = new ArrayList<>();
    //    Manager manager = SecurityUtil.getAuthManager();
        Manager manager = managerRepository.findByLogin("amy.lee@gmail.com");
        List<Enterprise> enterprises = manager.getEnterprise();
        for (Enterprise enterprise: enterprises) {
            vehicles.addAll(vehicleRepository.findAllByEnterprise(enterprise));
        }
        logger.info("All vehicles {}", vehicles);
        return VehicleUtils.getVehicleTos(vehicles);
    }

   /* @GetMapping("/pagination/{offset}/{pageSize}")
    public List<VehicleTo> getAllPaginated(TimeZone timezone,
                                           @PathVariable int offset, @PathVariable int pageSize) {
        List<Vehicle> vehicles = new ArrayList<>();
        List<VehicleTo> vehicleTos = new ArrayList<>();
        Manager manager = SecurityUtil.getAuthManager();
        List<Enterprise> enterprises = manager.getEnterprise();
        *//*for (Enterprise enterprise: enterprises) {
            List<Vehicle> vehiclesByEnterprise = vehicleRepository.getByEnterprisePaginated(enterprise, offset, pageSize);
            vehicles.addAll(vehicleRepository.getByEnterprisePaginated(enterprise, offset, pageSize));
            vehicleTos.addAll(VehicleUtils.getVehicleTosWithLocalTime(vehicles, timezone.getID()));
            if (vehiclesByEnterprise.size() == pageSize) {
                break;
            }
        }
*//*
        return vehicleTos;
    }*/

    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VehicleTo> create(@RequestBody VehicleTo vehicleTo) {
        logger.info("Create new vehicle");
       /* var vehicle = createOrUpdate(vehicleTo);
        logger.info("Vehicle with id {} created", vehicle.getBody().getId());*/
        return createOrUpdate(vehicleTo);
    }

    @PostMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<VehicleTo> update(@RequestBody VehicleTo vehicleTo) {
        return createOrUpdate(vehicleTo);
    }

    @Autowired
    VehicleService vehicleService;
    private  ResponseEntity<VehicleTo> createOrUpdate(VehicleTo vehicleTo) {
        //Manager manager = SecurityUtil.getAuthManager();
     //   Manager manager = managerRepository.findByLogin("amy.lee@gmail.com");
       // VehicleUtils.checkEnterpriseIsConsistent(manager, vehicleTo.getEnterpriseId());

        Vehicle newVehicle = VehicleUtils.getVehicleFromTo(vehicleTo);

      //  Vehicle created = vehicleRepository.save(newVehicle, vehicleTo.getModelId(), vehicleTo.getEnterpriseId());
      //  Vehicle created = vehicleRepository.save(newVehicle);
        Vehicle created = vehicleService.create(vehicleTo);
        VehicleTo createdTo = VehicleUtils.getVehicleTo(created);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(createdTo);
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
       // Manager manager = SecurityUtil.getAuthManager();
        Manager manager = managerRepository.findByLogin("amy.lee@gmail.com");
        Vehicle vehicle = vehicleRepository.findById(id).orElseThrow();
        VehicleUtils.checkEnterpriseIsConsistent(manager, vehicle.getEnterprise().getId());
        vehicleRepository.deleteById(id);
    }


    @PostMapping(value = "/generate", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void generate(@RequestBody VehicleGeneratorParamsTo vehicleGeneratorParams) {
        vehicleGeneratorService.generateVehiclesForEnterprises(vehicleGeneratorParams.getEntepriseIds(),
                vehicleGeneratorParams.getNumVehicles());
    }

}
