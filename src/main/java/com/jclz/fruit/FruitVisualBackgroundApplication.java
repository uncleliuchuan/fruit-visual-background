package com.jclz.fruit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
@EnableScheduling
@EnableCaching
public class FruitVisualBackgroundApplication {

    public static void main(String[] args) {
        SpringApplication.run(FruitVisualBackgroundApplication.class, args);
    }

}
