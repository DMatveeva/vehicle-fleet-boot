package ru.dmatveeva.vehiclefleetboot.service;

import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.dmatveeva.vehiclefleetboot.entity.Enterprise;
import ru.dmatveeva.vehiclefleetboot.entity.Manager;
import ru.dmatveeva.vehiclefleetboot.entity.vehicle.Vehicle;
import ru.dmatveeva.vehiclefleetboot.repository.EnterpriseRepository;
import ru.dmatveeva.vehiclefleetboot.repository.ManagerRepository;
import ru.dmatveeva.vehiclefleetboot.repository.VehicleModelRepository;
import ru.dmatveeva.vehiclefleetboot.repository.VehicleRepository;
import ru.dmatveeva.vehiclefleetboot.to.VehicleTo;
import ru.dmatveeva.vehiclefleetboot.util.VehicleUtils;
import ru.dmatveeva.vehiclefleetboot.web.rest.RestVehicleController;

import java.util.ArrayList;
import java.util.List;

@Service
public class VehicleService {

    @Autowired
    VehicleRepository vehicleRepository;

    @Autowired
    ManagerRepository managerRepository;

    @Autowired
    Producer producer;


    Logger logger = LoggerFactory.getLogger(RestVehicleController.class);


    public List<VehicleTo> getAll() {
        logger.info("Get all vehicles");
        List<Vehicle> vehicles = new ArrayList<>();
    //    Manager manager = SecurityUtil.getAuthManager();
        Manager manager = managerRepository.findByLogin("amy.lee@gmail.com");
        List<Enterprise> enterprises = manager.getEnterprises();
        for (Enterprise enterprise: enterprises) {
            vehicles.addAll(vehicleRepository.findAllByEnterprise(enterprise));
        }
        logger.info("All vehicles {}", vehicles);
        return VehicleUtils.getVehicleTos(vehicles);
    }

    @Autowired
    VehicleModelRepository vehicleModelRepository;

    @Autowired
    EnterpriseRepository enterpriseRepository;

    @SneakyThrows
    public Vehicle create(VehicleTo vehicleTo) {
        logger.info("Create new vehicle");

   //     Manager manager = SecurityUtil.getAuthManager();
     //   VehicleUtils.checkEnterpriseIsConsistent(manager, vehicleTo.getEnterpriseId());

        Vehicle newVehicle = VehicleUtils.getVehicleFromTo(vehicleTo);
        var model = vehicleModelRepository.findById(vehicleTo.getModelId()).orElseThrow();
        var enterprise = enterpriseRepository.findById(vehicleTo.getEnterpriseId()).orElseThrow();
        newVehicle.setVehicleModel(model);
        newVehicle.setEnterprise(enterprise);

        Vehicle created = vehicleRepository.save(newVehicle);
       // producer.sendMessage(VehicleUtils.getVehicleTo(created));

        logger.info("Vehicle with id {} created", created.getId());
        return created;
    }

}
