package ru.dmatveeva.vehiclefleetboot.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

import static io.hypersistence.utils.jdbc.validator.SQLStatementCountValidator.assertSelectCount;
import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
@SpringBootTest
public class EnterpriseRepositoryTest {

    @Autowired EnterpriseRepository enterpriseRepository;

    @Test
    @Transactional
    void findTest() {
        var enterprise = enterpriseRepository.findAll();
        var drivers = enterprise.stream()
                .map(e -> e.getDrivers())
                .collect(Collectors.toList());
        log.info("drivers {}", drivers);

        assertThat(drivers).isNotEmpty();
        assertSelectCount(1);
    }


}
