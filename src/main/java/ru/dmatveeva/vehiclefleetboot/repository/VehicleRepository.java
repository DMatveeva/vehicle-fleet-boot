package ru.dmatveeva.vehiclefleetboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dmatveeva.vehiclefleetboot.entity.Enterprise;
import ru.dmatveeva.vehiclefleetboot.entity.vehicle.Vehicle;

import java.util.List;

public interface VehicleRepository extends JpaRepository<Vehicle, Integer> {

 //   List<Vehicle> findAllOrderById();
    void deleteById(Integer id);
    List<Vehicle> findAllByEnterprise(Enterprise enterprise);
    List<Vehicle> findAllByEnterpriseOrderById(Enterprise enterprise);


//    List<Vehicle> findAllByPa(int offset, int pageSize);

    /*boolean delete(int id);

    Vehicle save(Vehicle vehicle);

    Vehicle get(int id);

    List<Vehicle> getByEnterprise(Enterprise enterprise);

    List<Vehicle> getByEnterprisePaginated(Enterprise enterprise, int offset, int pageSize);

    Vehicle save(Vehicle vehicle, Integer modelId, Integer enterpriseId);*/
}
