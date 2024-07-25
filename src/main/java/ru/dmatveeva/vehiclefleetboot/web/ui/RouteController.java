package ru.dmatveeva.vehiclefleetboot.web.ui;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.dmatveeva.vehiclefleetboot.entity.Track;
import ru.dmatveeva.vehiclefleetboot.entity.vehicle.VehicleCoordinate;
import ru.dmatveeva.vehiclefleetboot.repository.TrackRepository;
import ru.dmatveeva.vehiclefleetboot.repository.VehicleCoordinateRepository;
import ru.dmatveeva.vehiclefleetboot.util.CoordinateUtils;

import java.util.List;

@Controller
@RequestMapping("/routes")
public class RouteController {
    public final VehicleCoordinateRepository coordinateRepository;
    public final TrackRepository trackRepository;

    public RouteController(VehicleCoordinateRepository coordinateRepository, TrackRepository trackRepository) {
        this.coordinateRepository = coordinateRepository;
        this.trackRepository = trackRepository;
    }

    @GetMapping("/track/{id}")
    public String getRoute(Model model, @PathVariable int id) {
        Track track = trackRepository.findById(id).orElseThrow();
        List<VehicleCoordinate>  coordinates = coordinateRepository.findAllByTrackOrderByVisited(track);
        double[][] points = CoordinateUtils.getPoints(coordinates);
        double[] center = CoordinateUtils.getCenter(coordinates);

        model.addAttribute("points", points);
        model.addAttribute("center",center);
        return "route.html";
    }

}