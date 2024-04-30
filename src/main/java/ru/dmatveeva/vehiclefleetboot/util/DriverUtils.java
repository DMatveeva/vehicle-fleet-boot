package ru.dmatveeva.vehiclefleetboot.util;

import ru.dmatveeva.vehiclefleetboot.entity.Driver;
import ru.dmatveeva.vehiclefleetboot.to.DriverTo;

import java.util.List;
import java.util.stream.Collectors;

public class DriverUtils {
    public static List<DriverTo> getDriverTos(List<Driver> drivers) {
        return drivers.stream()
                .map(DriverUtils::getDriverTo)
                .collect(Collectors.toList());
    }

    private static DriverTo getDriverTo(Driver driver) {
        return new DriverTo(driver.getId(),
                driver.getFirstName(),
                driver.getSecondName(),
                driver.getSalaryUSD(),
                driver.getDrivingExperienceYears(),
                driver.getEnterprise().getId(),
                driver.getVehicle().getId(),
                driver.isActive());
    }
}
