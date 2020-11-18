package com.example.service;

import com.example.pojo.Product;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient("service-provider")
public interface ProductService {

    @Cacheable(cacheNames = "productService:product:list")
    @GetMapping("/product/list")
    List<Product> selectProductList();

    @GetMapping("/product/pojo")
    Product selectProductByProduct(Product product);

    @GetMapping("/product/{id}")
    @Cacheable(cacheNames = "productService:product:single",key = "#id")
    Product selectProductById(@PathVariable Integer id);

}
