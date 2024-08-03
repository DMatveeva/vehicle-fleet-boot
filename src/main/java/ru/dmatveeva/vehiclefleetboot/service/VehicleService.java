package ru.dmatveeva.vehiclefleetboot.service;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
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
import ru.dmatveeva.vehiclefleetboot.util.SecurityUtils;
import ru.dmatveeva.vehiclefleetboot.util.VehicleUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class VehicleService {

    @Autowired
    VehicleRepository vehicleRepository;

    @Autowired
    ManagerRepository managerRepository;

    @Autowired
    Producer producer;

    public List<VehicleTo> getAll() {
        log.info("Get all vehicles");
        List<Vehicle> vehicles = new ArrayList<>();
        Manager manager = managerRepository.findByLogin(SecurityUtils.getUserName());
        List<Enterprise> enterprises = manager.getEnterprises();
        for (Enterprise enterprise: enterprises) {
            vehicles.addAll(vehicleRepository.findAllByEnterprise(enterprise));
        }
        log.info("All vehicles {}", vehicles);
        return VehicleUtils.getVehicleTos(vehicles);
    }

    @Autowired
    VehicleModelRepository vehicleModelRepository;

    @Autowired
    EnterpriseRepository enterpriseRepository;

    @SneakyThrows
    public Vehicle create(VehicleTo vehicleTo) {
        log.info("Create new vehicle");

        Manager manager = managerRepository.findByLogin(SecurityUtils.getUserName());
        List<Enterprise> enterprises = manager.getEnterprises();
        VehicleUtils.checkEnterpriseIsConsistent(manager, vehicleTo.getEnterpriseId());

        Vehicle newVehicle = VehicleUtils.getVehicleFromTo(vehicleTo);
        var model = vehicleModelRepository.findById(vehicleTo.getModelId()).orElseThrow();
        var enterprise = enterpriseRepository.findById(vehicleTo.getEnterpriseId()).orElseThrow();
        newVehicle.setVehicleModel(model);
        newVehicle.setEnterprise(enterprise);
        Vehicle created = vehicleRepository.save(newVehicle);
        log.info("Vehicle with id {} created", created.getId());
        return created;
    }
}
