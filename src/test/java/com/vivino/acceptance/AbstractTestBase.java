package com.vivino.acceptance;

import io.cucumber.java.Scenario;
import io.cucumber.spring.CucumberContextConfiguration;
import lombok.Data;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;

@ContextConfiguration({"classpath:spring/spring-configuration.xml"})
@CucumberContextConfiguration
@Data
public class AbstractTestBase {
    public static ApplicationContext applicationContext;
    public static Scenario scenario;
}
