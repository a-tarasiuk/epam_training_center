package com.epam.esm.controller;

import com.epam.esm.controller.util.PackagePath;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = PackagePath.BASE)
@EnableJpaRepositories(PackagePath.REPOSITORY)
@EntityScan(PackagePath.MODEL)
public class EsmApplicationRunner extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(EsmApplicationRunner.class, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(EsmApplicationRunner.class);
    }
}