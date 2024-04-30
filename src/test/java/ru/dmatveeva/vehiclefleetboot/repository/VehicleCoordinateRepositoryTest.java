package ru.dmatveeva.vehiclefleetboot.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;
import ru.dmatveeva.vehiclefleetboot.entity.Track;
import ru.dmatveeva.vehiclefleetboot.web.rest.RestCoordinateController;

@Slf4j
//@SpringJUnitConfig
//@ContextConfiguration(locations = { "classpath:/spring/spring-app.xml", "classpath:/spring/db/spring-db.xml" })
//@ExtendWith(SpringExtension.class)
@SpringBootTest
public class VehicleCoordinateRepositoryTest {

    @Autowired VehicleCoordinateRepository vehicleCoordinateRepository;
    @Autowired TrackRepository trackRepository;

    @Autowired
    RestCoordinateController controller;

    @Test
    void getTest() {
        var track = trackRepository.findById(100040).orElseThrow();
        var coordinates = vehicleCoordinateRepository.findAllByTrackOrderByVisited(track);

        StopWatch watchNoCash = new StopWatch();
        watchNoCash.start();
        getAndPrint(track);
        watchNoCash.stop();
        log.info("without cash: {}", watchNoCash.getTotalTimeNanos());

        StopWatch watchCash = new StopWatch();
        watchCash.start();
        getAndPrint(track);
        watchCash.stop();
        log.info("with cash: {}", watchCash.getTotalTimeNanos());

    }

    @Test
    void getTest1() {
        var track = trackRepository.findById(100040).orElseThrow();
        var coordinates = vehicleCoordinateRepository.findAllByTrackOrderByVisited(track);

        StopWatch watchNoCash = new StopWatch();
        watchNoCash.start();
        getAndPrint(track);
        watchNoCash.stop();
        log.info("without cash: {}", watchNoCash.getTotalTimeNanos());

        StopWatch watchCash = new StopWatch();
        watchCash.start();
        getAndPrint(track);
        watchCash.stop();
        log.info("with cash: {}", watchCash.getTotalTimeNanos());

    }



    private void getAndPrint(Track track) {
        vehicleCoordinateRepository.findAllByTrackOrderByVisited(track);
        log.info("coordinates of track {}", track.getId());
    }
}
