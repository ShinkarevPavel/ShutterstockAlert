package com.nobody.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@ComponentScan("com.nobody")
@EnableScheduling
public class TestRunner extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(TestRunner.class, args);
    }
}
