package com.fastfood;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {"com.fastfood"})
@EnableJpaRepositories
public class FastFoodPagamentoApplication {
    public static void main(String[] args) {
        SpringApplication.run(FastFoodPagamentoApplication.class, args);
    }
}

//testeworkflow