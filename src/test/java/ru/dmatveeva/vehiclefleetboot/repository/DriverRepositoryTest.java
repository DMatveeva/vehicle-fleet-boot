package ru.dmatveeva.vehiclefleetboot.repository;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.dmatveeva.vehiclefleetboot.web.rest.RestDriverController;

@Slf4j
//@SpringJUnitConfig
//@ContextConfiguration(locations = { "classpath:/spring/spring-app.xml", "classpath:/spring/db/spring-db.xml" })
//@ExtendWith(SpringExtension.class)
@SpringBootTest
public class DriverRepositoryTest {

    @Autowired
    RestDriverController restDriverController;

    @Test
    void getTest() {
        restDriverController.getAll();
    }

}
