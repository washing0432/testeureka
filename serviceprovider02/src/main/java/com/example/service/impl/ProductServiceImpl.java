package com.example.service.impl;

import com.example.pojo.Product;
import com.example.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {
    @Override
    public List<Product> selectProductList() {

        Product p = new Product();
        return Arrays.asList(new Product(1,"华为平板",20,Double.valueOf(110)),
                new Product(2,"小米平板",30,Double.valueOf(136)),
                new Product(3,"魅族平板",30,Double.valueOf(136)),
                new Product(4,"oppo平板",30,Double.valueOf(136)));
    }
}
