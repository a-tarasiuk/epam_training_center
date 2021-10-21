package com.epam.esm.config;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EntityScan(basePackages = "com.epam.esm.entity")
public class EsmApplicationConfiguration {
    public static void main(String[] args) {
        // Method 1
//        SpringApplication.run(EsmApplicationConfiguration.class, args);

        // Method 3
        SpringApplication application = new SpringApplication(EsmApplicationConfiguration.class);
        application.setBannerMode(Banner.Mode.OFF);
        application.run(args);

        // Method 2
//        new SpringApplicationBuilder(EsmApplicationConfiguration.class)
//                .logStartupInfo(false)
//                .run(args);
    }
}
