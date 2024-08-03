package ru.dmatveeva.vehiclefleetboot.web.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dmatveeva.vehiclefleetboot.repository.DriverRepository;
import ru.dmatveeva.vehiclefleetboot.to.DriverTo;
import ru.dmatveeva.vehiclefleetboot.util.DriverUtils;

import java.util.List;

@RestController
@RequestMapping(value = RestDriverController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
@Slf4j
public class RestDriverController {

    static final String REST_URL = "/rest/driver";
    private final DriverRepository driverRepository;

    public RestDriverController(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    @GetMapping()
    public List<DriverTo> getAll() {

        log.info("Retrieve all drivers");
        var drivers = driverRepository.findAll();
        var driverTo = DriverUtils.getDriverTos(driverRepository.findAll());
        log.info("Drivers : {}", drivers);
        return driverTo;
    }

}
