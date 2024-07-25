package ru.dmatveeva.vehiclefleetboot.web.ui;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RootController {
    @GetMapping("/login")
    public String login(Model model){
        return "login.html";
    }

}
