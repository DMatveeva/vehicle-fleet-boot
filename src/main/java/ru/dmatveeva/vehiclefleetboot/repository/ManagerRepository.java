package ru.dmatveeva.vehiclefleetboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.dmatveeva.vehiclefleetboot.entity.Manager;

public interface ManagerRepository extends JpaRepository<Manager, Integer> {
    Manager findByLogin(String login);
}
