package ru.dmatveeva.vehiclefleetboot.configuration;

import org.springframework.context.annotation.Bean;
import org.thymeleaf.dialect.IDialect;
import org.thymeleaf.extras.java8time.dialect.Java8TimeDialect;

public class TemplatesConfiguration {
    @Bean
    public IDialect conditionalCommentDialect() {
        return new Java8TimeDialect();
    }
}
