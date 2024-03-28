package com.liid.greenforum;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

@EnableMethodSecurity
@SpringBootApplication
public class GreenForumApplication {

    public static void main(String[] args) {
        SpringApplication.run(GreenForumApplication.class, args);
    }

}
