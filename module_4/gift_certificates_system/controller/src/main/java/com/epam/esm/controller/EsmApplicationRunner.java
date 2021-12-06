package com.epam.esm.controller;

import com.epam.esm.controller.util.PackagePath;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = PackagePath.BASE)
@EnableJpaRepositories(PackagePath.BASE)
@EntityScan(PackagePath.BASE)
public class EsmApplicationRunner {
    public static void main(String[] args) {
        SpringApplication.run(EsmApplicationRunner.class, args);
    }
}