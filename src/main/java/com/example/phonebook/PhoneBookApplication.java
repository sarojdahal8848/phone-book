package com.example.phonebook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class PhoneBookApplication {

    public static void main(String[] args) {
        SpringApplication.run(PhoneBookApplication.class, args);
    }

}
