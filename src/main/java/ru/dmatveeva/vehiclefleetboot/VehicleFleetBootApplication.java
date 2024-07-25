package ru.dmatveeva.vehiclefleetboot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EntityScan
@EnableWebSecurity
public class VehicleFleetBootApplication {

    static final Logger log = LoggerFactory.getLogger(VehicleFleetBootApplication.class);

    public static void main(String[] args) {
        log.info("Before Starting VehicleFleetBootApplication");
        SpringApplication.run(VehicleFleetBootApplication.class, args);
        log.info("VehicleFleetBootApplication started");

    }

}
