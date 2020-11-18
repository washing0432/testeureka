package com.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

//@EnableEurekaClient
@EnableCaching
@SpringBootApplication
public class ServiceProviderApplication {
        public static void main(String[] args){
        SpringApplication.run(ServiceProviderApplication.class,args);
    }
}
