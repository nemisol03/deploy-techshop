package com.myshop.site;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@SpringBootApplication
@EntityScan("com.myshop.common")
public class MyShopFrontendApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyShopFrontendApplication.class, args);
    }

}
