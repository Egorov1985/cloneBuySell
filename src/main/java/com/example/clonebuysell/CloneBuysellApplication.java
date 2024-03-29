package com.example.clonebuysell;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@AllArgsConstructor
@Slf4j
public class CloneBuysellApplication {


    public static void main(String[] args) {
        SpringApplication.run(CloneBuysellApplication.class, args);
    }

}
