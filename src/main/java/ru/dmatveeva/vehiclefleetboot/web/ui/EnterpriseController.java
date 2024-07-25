package ru.dmatveeva.vehiclefleetboot.web.ui;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.dmatveeva.vehiclefleetboot.entity.Enterprise;
import ru.dmatveeva.vehiclefleetboot.entity.Manager;
import ru.dmatveeva.vehiclefleetboot.repository.EnterpriseRepository;
import ru.dmatveeva.vehiclefleetboot.repository.ManagerRepository;

import java.util.List;

@Controller
public class EnterpriseController {

    @Autowired
    EnterpriseRepository enterpriseRepository;
    @Autowired
    ManagerRepository managerRepository;

    @GetMapping("/enterprises")
    public String getEnterprises(Model model) {
        User user = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        Manager manager = managerRepository.findByLogin(user.getUsername());
        List<Enterprise> enterprises = manager.getEnterprises();

        model.addAttribute("enterprises", enterprises);
        return "enterprises.html";
    }
}
