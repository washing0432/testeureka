package com.example;

import feign.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableFeignClients
//@EnableCaching
//@EnableCircuitBreaker
@EnableHystrix
public class ServiceFeignConsumerApplication {

    @Bean
//    @LoadBalanced
    public RestTemplate restTemplate(){
        return new RestTemplate();
    }

//    @Bean
//    public RandomRule randomRule(){
//        return new RandomRule();
//    }

    @Bean
    public Logger.Level getLog(){
        return Logger.Level.FULL;
    }

    public static void main(String[] args){

        SpringApplication.run(ServiceFeignConsumerApplication.class,args);
    }
}
