package ru.dmatveeva.vehiclefleetboot.util;

import ru.dmatveeva.vehiclefleetboot.entity.AbstractBaseEntity;
import ru.dmatveeva.vehiclefleetboot.entity.Manager;
import ru.dmatveeva.vehiclefleetboot.entity.vehicle.Vehicle;
import ru.dmatveeva.vehiclefleetboot.to.VehicleTo;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.stream.Collectors;

public class VehicleUtils {

    public static List<VehicleTo> getVehicleTos(List<Vehicle> vehicles) {
        return vehicles.stream()
                .map(VehicleUtils::getVehicleTo)
                .collect(Collectors.toList());
    }

    public static List<VehicleTo> getVehicleTosWithLocalTime(List<Vehicle> vehicles, String zone) {
        return vehicles.stream()
                .map(v -> VehicleUtils.getVehicleToWithLocalTime(v, zone))
                .collect(Collectors.toList());
    }

    public static VehicleTo getVehicleTo(Vehicle vehicle) {
        return new VehicleTo(vehicle.getId(),
                vehicle.getVehicleModel().getId(),
                vehicle.getVin(),
                vehicle.getCostUsd(),
                vehicle.getColor(),
                vehicle.getMileage(),
                vehicle.getProductionYear(),
                vehicle.getEnterprise().getId(),
                vehicle.getPurchaseDate());
    }

    public static VehicleTo getVehicleToWithLocalTime(Vehicle vehicle, String zone) {
        LocalDateTime oldDateTime = vehicle.getPurchaseDate();
        LocalDateTime newDateTime = oldDateTime.atZone(ZoneId.of("UTC"))
                .withZoneSameInstant(ZoneId.of(zone))
                .toLocalDateTime();
        return new VehicleTo(vehicle.getId(),
                vehicle.getVehicleModel().getId(),
                vehicle.getVin(),
                vehicle.getCostUsd(),
                vehicle.getColor(),
                vehicle.getMileage(),
                vehicle.getProductionYear(),
                vehicle.getEnterprise().getId(),
                newDateTime);
    }

    public static Vehicle getVehicleFromTo(VehicleTo vehicleTo) {
        return new Vehicle(
                vehicleTo.getId(),
                null,
                vehicleTo.getVin(),
                vehicleTo.getCostUsd(),
                vehicleTo.getColor(),
                vehicleTo.getMileage(),
                vehicleTo.getProductionYear(),
                vehicleTo.getPurchaseDate(),
                null,
                null);
    }

    public static void checkEnterpriseIsConsistent(Manager manager, Integer enterprise_id) {
        boolean isEnterpriseConsistentWithManager = manager.getEnterprise().stream()
                .map(AbstractBaseEntity::getId)
                .anyMatch(id -> id.equals(enterprise_id));
        if (!isEnterpriseConsistentWithManager) {
            throw new IllegalArgumentException("Enterprise's not found for manager.");
        }
    }
}
