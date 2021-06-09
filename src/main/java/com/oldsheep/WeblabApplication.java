package com.oldsheep;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.oldsheep.mapper")

public class WeblabApplication {

    public static void main(String[] args) {
        SpringApplication.run(WeblabApplication.class, args);
    }

}
