package com.example.controller;

import com.example.pojo.Product;
import com.example.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    public Product selectProductById(@PathVariable("id") Integer id){
        return productService.selectProductById(id);
    }
}
