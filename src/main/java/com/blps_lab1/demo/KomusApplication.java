package com.blps_lab1.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class KomusApplication {

    public static void main(String[] args) {
        SpringApplication.run(KomusApplication.class, args);
    }

}
