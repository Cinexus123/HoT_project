package com.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@EnableScheduling
public class ApiApplication extends SpringBootServletInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        System.setProperty("spring.config.name", "backend");
        super.onStartup(servletContext);
    }

    public static void main(String[] args) {
        System.setProperty("spring.config.name", "backend");
        SpringApplication.run(ApiApplication.class, args);
    }

}
