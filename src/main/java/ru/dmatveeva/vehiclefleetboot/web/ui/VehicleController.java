package ru.dmatveeva.vehiclefleetboot.web.ui;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.dmatveeva.vehiclefleetboot.entity.Enterprise;
import ru.dmatveeva.vehiclefleetboot.entity.Manager;
import ru.dmatveeva.vehiclefleetboot.entity.VehicleReport;
import ru.dmatveeva.vehiclefleetboot.entity.vehicle.Vehicle;
import ru.dmatveeva.vehiclefleetboot.entity.vehicle.VehicleModel;
import ru.dmatveeva.vehiclefleetboot.repository.EnterpriseRepository;
import ru.dmatveeva.vehiclefleetboot.repository.ManagerRepository;
import ru.dmatveeva.vehiclefleetboot.repository.TrackRepository;
import ru.dmatveeva.vehiclefleetboot.repository.VehicleModelRepository;
import ru.dmatveeva.vehiclefleetboot.repository.VehicleRepository;
import ru.dmatveeva.vehiclefleetboot.util.DateUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;

@Slf4j
@Controller
@RequestMapping("/vehicles")
public class VehicleController {
    @Autowired EnterpriseRepository enterpriseRepository;
    @Autowired TrackRepository trackRepository;
    @Autowired VehicleRepository vehicleRepository;
    @Autowired VehicleModelRepository vehicleModelRepository;
    @Autowired ManagerRepository managerRepository;

    public VehicleController(EnterpriseRepository enterpriseRepository,
                             TrackRepository trackRepository,
                             VehicleRepository vehicleRepository,
                             VehicleModelRepository vehicleModelRepository) {
        this.enterpriseRepository = enterpriseRepository;
        this.trackRepository = trackRepository;

        this.vehicleRepository = vehicleRepository;
        this.vehicleModelRepository = vehicleModelRepository;
    }

    @GetMapping("/report/{id}")
    public String report(@PathVariable int id, Model model){
        model.addAttribute("report", new VehicleReport());
        Vehicle vehicle =  vehicleRepository.findById(id).orElseThrow();
        model.addAttribute("vehicleId", vehicle.getId());
        return "reportForm.html";
    }

    @GetMapping("/create")
    public String create(Model model){
        model.addAttribute("vehicle", new Vehicle());
        List<VehicleModel> models = vehicleModelRepository.findAll();
        model.addAttribute("models", models);

        User user = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        Manager manager = managerRepository.findByLogin(user.getUsername());
        List<Enterprise> enterprises = manager.getEnterprises();
        model.addAttribute("enterprises", enterprises);
        return "vehicleForm.html";
    }

    @GetMapping("/{id}")
    public String get(@PathVariable int id, Model model) {
        Vehicle vehicle =  vehicleRepository.findById(id).orElseThrow();
        model.addAttribute("vehicle", vehicle);
        model.addAttribute("tracks", trackRepository.getAllByVehicle(vehicle));
        return "vehicle.html";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id, HttpServletRequest request) {
        Vehicle vehicle = vehicleRepository.findById(id).orElseThrow();
        int enterpriseId = vehicle.getEnterprise().getId();
        vehicleRepository.deleteById(id);
        return "redirect:/vehicles?enterpriseId=" + enterpriseId;
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable int id,  HttpServletRequest request, Model model){
        Vehicle vehicle = vehicleRepository.findById(id).orElseThrow();

        User user = ((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        Manager manager = managerRepository.findByLogin(user.getUsername());
        List<Enterprise> enterprises = manager.getEnterprises();
        model.addAttribute("enterprises", enterprises);
        model.addAttribute("vehicle", vehicle);
        model.addAttribute("models", getVehicleModels(vehicle));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        String dateFormatted = vehicle.getPurchaseDate().format(formatter);
        model.addAttribute("purchaseDateVal", dateFormatted);

        return "vehicleForm.html";
    }

    private List<VehicleModel> getVehicleModels(Vehicle vehicle) {
        List<VehicleModel> models = vehicleModelRepository.findAll();
        VehicleModel vehicleModel = vehicle.getVehicleModel();
        models.remove(vehicleModel);
        models.add(0, vehicleModel);
        return models;
    }


    @GetMapping("/all")
    public String getAll(Model model) {
        model.addAttribute("vehicles", vehicleRepository.findAll());
        return "vehicles.html";
    }

    @PostMapping("/update_or_create")
    public String updateOrCreate(@RequestParam("id") String id,
                                 @RequestParam("vin") String vin,
                                 @RequestParam("vehicleModel") Integer vehicleModelId,
                                 @RequestParam("color") String color,
                                 @RequestParam("costUsd") BigDecimal costUsd,
                                 @RequestParam("mileage") Integer mileage,
                                 @RequestParam("productionYear") Integer productionYear,
                                 @RequestParam("purchaseDateVal") String purchaseDateStr,
                                 @RequestParam("enterprise") Integer enterpriseId
                                 ) {
        VehicleModel vehicleModel = vehicleModelRepository.findById(vehicleModelId).orElseThrow();
        Enterprise enterprise = enterpriseRepository.findById(enterpriseId).orElseThrow();
        LocalDateTime purchaseDate = DateUtils.getLdtFromString(purchaseDateStr);
        Vehicle vehicle;
        if (id.isEmpty()) {
            vehicle = new Vehicle(vehicleModel,
                    vin, costUsd, color, mileage, productionYear,
                    purchaseDate, null, enterprise);
        } else {
            vehicle = new Vehicle(Integer.parseInt(id), vehicleModel,
                    vin, costUsd, color, mileage, productionYear,
                    purchaseDate,null, enterprise);
        }
        var saved = vehicleRepository.save(vehicle);
        log.info("vehicle {} created", saved.getId());

        return "redirect:/vehicles?enterpriseId=" + enterprise.getId();
    }

    @GetMapping
    public String getVehiclesByEnterprise(HttpServletRequest request, Model model){
        String paramId = Objects.requireNonNull(request.getParameter("enterpriseId"));
        Enterprise enterprise = enterpriseRepository.findById(Integer.parseInt(paramId)).orElseThrow();

        PageRequest p = PageRequest.of(0, 100);
        List<Vehicle> vehicles = vehicleRepository.getAllByEnterprise(enterprise, p).stream().toList();

        vehicles.forEach(v -> setZonedPurchaseDateToVehicle(v, enterprise));

        model.addAttribute("vehicles", vehicles);
        return "vehicles.html";
    }

    void setZonedPurchaseDateToVehicle(Vehicle vehicle, Enterprise enterprise) {
        var zoned = vehicle.getPurchaseDate() == null ? null :
                DateUtils.getLtdInTimeZone(vehicle.getPurchaseDate(),
                        "UTC", enterprise.getLocalTimeZone());
        vehicle.setPurchaseDate(zoned);
    }
}
