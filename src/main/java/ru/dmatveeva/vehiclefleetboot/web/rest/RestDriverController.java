package ru.dmatveeva.vehiclefleetboot.web.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dmatveeva.vehiclefleetboot.entity.Driver;
import ru.dmatveeva.vehiclefleetboot.repository.DriverRepository;

import java.util.List;

@RestController
@RequestMapping(value = RestDriverController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RestDriverController {

    static final String REST_URL = "/rest/drivers";

    public static Logger LOG = LoggerFactory.getLogger(RestCoordinateController.class);

    private final DriverRepository driverRepository;

    public RestDriverController(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

  /*  @GetMapping()
    public List<DriverTo> getAll() {

        LOG.info("Retrieve all drivers");
        var drivers = driverRepository.findAll();
       // var drivers = DriverUtils.getDriverTos(driverRepository.findAll());
        LOG.info("Drivers : {}", drivers);
        return drivers;
    }*/

    public List<Driver> getAll() {

        LOG.info("Retrieve all drivers");
        var drivers = driverRepository.findAll();
        // var drivers = DriverUtils.getDriverTos(driverRepository.findAll());
        LOG.info("Drivers : {}", drivers);
        return drivers;
    }

}
