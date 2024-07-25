package ru.dmatveeva.vehiclefleetboot.web.ui;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.dmatveeva.vehiclefleetboot.repository.VehicleModelRepository;

@Controller
public class VehicleModelController {

    private VehicleModelRepository vehicleModelRepository;

    public VehicleModelController(VehicleModelRepository vehicleModelRepository) {
        this.vehicleModelRepository = vehicleModelRepository;
    }

    @GetMapping("/models")
    private String getAll(Model model) {
        model.addAttribute("models", vehicleModelRepository.findAll());
        return "vehicleModels";
    }
}
