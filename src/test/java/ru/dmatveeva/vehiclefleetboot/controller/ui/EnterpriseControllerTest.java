package ru.dmatveeva.vehiclefleetboot.controller.ui;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.env.Environment;
import org.testcontainers.containers.BrowserWebDriverContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EnterpriseControllerTest {

    @LocalServerPort
    private Integer port;

    public static BrowserWebDriverContainer<?> webDriverContainer =
            new BrowserWebDriverContainer<>()
                    .withCapabilities(new ChromeOptions()
                            .addArguments("--no-sandbox")
                            .addArguments("--disable-dev-shm-usage"));

    @BeforeAll
    static void beforeAll(@Autowired Environment environment) {
        org.testcontainers.Testcontainers.exposeHostPorts(environment.getProperty("local.server.port", Integer.class));
        webDriverContainer.start();
    }


    @Test
    void shouldDisplayBooks() {

        Configuration.timeout = 2000;
        Configuration.baseUrl = String.format("http://host.testcontainers.internal:%d", port);

        RemoteWebDriver remoteWebDriver = webDriverContainer.getWebDriver();
        WebDriverRunner.setWebDriver(remoteWebDriver);

        open("/enterprises");
        $(By.id("100002")).click();
        $(By.id("all-enterprises")).shouldBe(Condition.visible);
    }
}
