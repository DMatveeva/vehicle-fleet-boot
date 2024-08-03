package ru.dmatveeva.vehiclefleetboot.web.rest.generator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dmatveeva.vehiclefleetboot.service.generate.VehicleGeneratorService;
import ru.dmatveeva.vehiclefleetboot.to.VehicleGeneratorParamsTo;

@RestController
@RequestMapping(value = "/rest/generator/vehicle", produces = MediaType.APPLICATION_JSON_VALUE)
public class RestVehicleGeneratorController {

    @Autowired
    private VehicleGeneratorService vehicleGeneratorService;

    @PostMapping(value = "/generate", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void generate(@RequestBody VehicleGeneratorParamsTo vehicleGeneratorParams) {
        vehicleGeneratorService.generateVehiclesForEnterprises(vehicleGeneratorParams.getEntepriseIds(),
                vehicleGeneratorParams.getNumVehicles());
    }
}
