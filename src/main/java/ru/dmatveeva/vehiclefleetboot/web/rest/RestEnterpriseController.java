package ru.dmatveeva.vehiclefleetboot.web.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.dmatveeva.vehiclefleetboot.entity.Enterprise;
import ru.dmatveeva.vehiclefleetboot.entity.Manager;
import ru.dmatveeva.vehiclefleetboot.repository.ManagerRepository;

import java.util.List;

@RestController
@RequestMapping(value = "/rest/enterprise", produces = MediaType.APPLICATION_JSON_VALUE)

public class RestEnterpriseController {
    @Autowired
    ManagerRepository managerRepository;

    @GetMapping()
    public List<Enterprise> getAll() {
        User user = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        Manager manager = managerRepository.findByLogin(user.getUsername());

        return manager.getEnterprises();
    }
}
