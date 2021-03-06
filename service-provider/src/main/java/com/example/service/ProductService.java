package com.example.service;

import com.example.pojo.Product;

import java.util.List;

public interface ProductService {

    List<Product> selectProductList();

    Product selectProductByPojo(Product product);

    Product selectProductById(Integer id);
}
