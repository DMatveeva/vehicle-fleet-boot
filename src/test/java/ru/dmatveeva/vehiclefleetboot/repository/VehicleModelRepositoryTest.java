package ru.dmatveeva.vehiclefleetboot.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

@Slf4j
/*@SpringJUnitConfig
@ContextConfiguration(locations = { "classpath:/spring/spring-app.xml", "classpath:/spring/db/spring-db.xml" })
@ExtendWith(SpringExtension.class)*/
@SpringBootTest
public class VehicleModelRepositoryTest {

    @Autowired VehicleModelRepository vehicleModelRepository;

    @Test
    void getTest() {

        StopWatch watchNoCash = new StopWatch();
        watchNoCash.start();
        getAndPrint();
        watchNoCash.stop();
        log.info("without cash: {}", watchNoCash.getTotalTimeNanos());

        StopWatch watchCash = new StopWatch();
        watchCash.start();
        getAndPrint();
        watchCash.stop();
        log.info("with cash: {}", watchCash.getTotalTimeNanos());
    }

    private void getAndPrint() {
        log.info("vehicle model Astra found: {}", vehicleModelRepository.findByName("Astra").getName());
    }

}
