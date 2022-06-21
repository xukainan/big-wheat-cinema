package com.wheat.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.wheat.admin", "com.wheat.cinema"})
public class CinemaAdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(CinemaAdminApplication.class, args);
    }

}
