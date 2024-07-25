package ru.dmatveeva.vehiclefleetboot.controller.ui;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.env.Environment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.BrowserWebDriverContainer;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.List;

import static com.codeborne.selenide.Selenide.open;
import static org.assertj.core.api.Assertions.assertThat;

@Testcontainers
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_CLASS)
public class GenerateRouteControllerTest {

    @Autowired
    MockMvc mockMvc;
    @LocalServerPort
    private Integer port;

    private static final DockerImageName image = DockerImageName.parse("postgis/postgis:16-master")
            .asCompatibleSubstituteFor("postgres");
    @Container
    private static PostgreSQLContainer postgis = new PostgreSQLContainer<>(image).withPassword("1");

    private static KafkaContainer kafka = new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:6.2.1"));


    public static BrowserWebDriverContainer<?> webDriverContainer =
            new BrowserWebDriverContainer<>()
                    .withCapabilities(new ChromeOptions()
                            .addArguments("--no-sandbox")
                            .addArguments("--disable-dev-shm-usage"));

    @BeforeAll
    static void beforeAll(@Autowired Environment environment) {
        org.testcontainers.Testcontainers.exposeHostPorts(environment.getProperty("local.server.port", Integer.class));
        postgis.start();
        webDriverContainer.start();
        kafka.start();
    }

    @Test
    void checkRoute() {

        Configuration.timeout = 2000;
        Configuration.baseUrl = String.format("http://host.testcontainers.internal:%d", port);

     //   mockMvc.perform()

        RemoteWebDriver remoteWebDriver = webDriverContainer.getWebDriver();
        WebDriverRunner.setWebDriver(remoteWebDriver);
        JavascriptExecutor js = (JavascriptExecutor) remoteWebDriver;

        //get time
        //send generate request
        // wait
        // check last rout for this c=vehicle in db
        // check created is bigger than time
        // check that js is present for that route


        open("/routes/track/101084");

        List val = (List) js.executeScript("return points");
        assertThat(val).isNotEmpty();
    }
}
