package ru.dmatveeva.vehiclefleetboot.web.rest;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = RestEnterpriseController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)

public class RestEnterpriseController {
    static final String REST_URL = "/rest/enterprises";

   /* @GetMapping()
    public List<Enterprise> getAll() {
        Manager manager = SecurityUtil.getAuthManager();
        return manager.getEnterprise();
    }*/
}
