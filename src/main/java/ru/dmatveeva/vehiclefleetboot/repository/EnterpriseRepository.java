package ru.dmatveeva.vehiclefleetboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.dmatveeva.vehiclefleetboot.entity.Enterprise;

import java.util.List;

@Repository
public interface EnterpriseRepository extends JpaRepository<Enterprise, Integer> {

    //@EntityGraph(attributePaths = "drivers")
    @Query("select e from Enterprise e join fetch e.drivers d")
    List<Enterprise> findAll();

    Enterprise findById(Long id);
}
