package ru.dmatveeva.vehiclefleetboot.service.reactive;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.dmatveeva.vehiclefleetboot.repository.VehicleRepository;

@SpringBootTest
class ReactiveTrackGeneratorServiceTest {

    @Autowired ReactiveTrackGeneratorService reactiveTrackGeneratorService;
    @Autowired
    VehicleRepository vehicleRepository;

    @Test
    @SneakyThrows
    void generateTrackReactive() {

        var vehicle = vehicleRepository.findById(100005).orElseThrow();
        var start = new double [] {-118.397900,33.939051};
        var end = new double [] {-118.278982, 33.941356};

        reactiveTrackGeneratorService.generateTrackRealTime(vehicle, start, end, 80, 10);
    }
}