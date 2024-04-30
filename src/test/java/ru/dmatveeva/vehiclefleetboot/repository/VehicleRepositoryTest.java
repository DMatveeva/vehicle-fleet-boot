package ru.dmatveeva.vehiclefleetboot.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;
import ru.dmatveeva.vehiclefleetboot.entity.vehicle.Vehicle;
import ru.dmatveeva.vehiclefleetboot.service.VehicleService;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Slf4j
/*@SpringJUnitConfig
@ContextConfiguration(locations = { "classpath:/spring/spring-app.xml", "classpath:/spring/db/spring-db.xml" })*/
/*@SpringJUnitConfig
@ExtendWith(SpringExtension.class)*/
@SpringBootTest
public class VehicleRepositoryTest {

    @Autowired
    VehicleRepository vehicleRepository;

    @Autowired
    VehicleModelRepository vehicleModelRepository;

    @Autowired
    EnterpriseRepository enterpriseRepository;

    @Autowired
    VehicleService service;

    @Test
    void getTest() {
        var vehicleModel = vehicleModelRepository.findById(100000).orElseThrow();
        var enterprise = enterpriseRepository.findById(100002).orElseThrow();

        var vehicle1 = vehicleRepository.save(new Vehicle(vehicleModel,
                "1Y1SL65848Z413490", BigDecimal.TEN, "red", 60000, 2008, LocalDateTime.now(), null, enterprise));

        var vehicle2 = vehicleRepository.save(new Vehicle(vehicleModel,
                "4Y1SL65848Z441401", BigDecimal.ONE, "blue", 60000, 2020, LocalDateTime.now(), null, enterprise));

        StopWatch watchNoCash = new StopWatch();
        watchNoCash.start();
        getAndPrint(vehicle1.getId());
        getAndPrint(vehicle2.getId());
        watchNoCash.stop();
        log.info("time without cash: {}", watchNoCash.getTotalTimeNanos());
        StopWatch watchCash = new StopWatch();
        watchCash.start();
        getAndPrint(vehicle1.getId());
        getAndPrint(vehicle2.getId());
        watchCash.stop();
        log.info("time with cash: {}", watchCash.getTotalTimeNanos());
    }

    @Test
    void getTest1() {
        var vehicleModel = vehicleModelRepository.findById(100000).orElseThrow();
        var enterprise = enterpriseRepository.findById(100002).orElseThrow();

        //service.getAll();

        var vehicle1 = vehicleRepository.save(new Vehicle(vehicleModel,
                "1R1SL65848Z418890", BigDecimal.TEN, "red", 60008, 2008, LocalDateTime.now(), null, enterprise));


    }

    private void getAndPrint(Integer id) {
        log.info("vehicle found: {}", vehicleRepository.findById(id).orElseThrow());
    }
}