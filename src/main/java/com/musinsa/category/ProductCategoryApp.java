package com.musinsa.category;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = {"com.musinsa.category.*"})
public class ProductCategoryApp {

    public static void main(String[] args) {
        SpringApplication.run(ProductCategoryApp.class, args);
    }

}
