package com.example.controller;

import com.example.pojo.Product;
import com.example.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/list")
    public List<Product> selectProductList(){
        try {
            Thread.sleep(2000);
        }
        catch (Exception exp){
            exp.printStackTrace();
        }

        return productService.selectProductList();
    }

    @GetMapping("/pojo")
    public Product selectProductByPojo(@RequestBody Product product){
        return productService.selectProductByPojo(product);
    }
}
