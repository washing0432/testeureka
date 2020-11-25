package com.example.controller;

import com.example.pojo.Product;
import com.example.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/pojo")
    public Product selectProductByPojo(Product product){
        System.out.println(Thread.currentThread().getName()+"---selectProductByPojo---");

        return productService.selectProductByProduct(product);
    }

    @GetMapping("/list")
    public List<Product> selectProductList(){
//        return productService.selectProductList();
        System.out.println(Thread.currentThread().getName()+"---selectProductList---");
        List<Product> products = productService.selectProductList();
        return products;
    }

    @GetMapping("/{id}")
    public Product selectProductById(@PathVariable Integer id){
        return productService.selectProductById(id);
    }
}
