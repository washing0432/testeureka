package com.example.service;

import com.example.pojo.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient("service-provider")
public interface ProductService {

    @GetMapping("/product/list")
    List<Product> selectProductList();
}
