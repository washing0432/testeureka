package com.example.service.impl;

import com.example.pojo.Product;
import com.example.service.ProductService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {


    @Override
    public List<Product> selectProductList() {

        Product p = new Product();
//        return Arrays.asList(new Product(1,"华为手机",20,Double.valueOf(110)),
//                new Product(2,"小米手机",30,Double.valueOf(136)),
//                new Product(3,"魅族手机",30,Double.valueOf(136)),
//                new Product(4,"oppo手机",30,Double.valueOf(136)));
        return Arrays.asList(
                new Product(1,"压缩华为手机",20,Double.valueOf(110)),
                new Product(2,"压缩小米手机",30,Double.valueOf(136)),
                new Product(3,"魅族手机",30,Double.valueOf(136)),
                new Product(1,"华为手机",20,Double.valueOf(110)),
                new Product(2,"小米手机",30,Double.valueOf(136)),
                new Product(3,"魅族手机",30,Double.valueOf(136)),
                new Product(1,"华为手机",20,Double.valueOf(110)),
                new Product(2,"小米手机",30,Double.valueOf(136)),
                new Product(3,"魅族手机",30,Double.valueOf(136)),
                new Product(1,"华为手机",20,Double.valueOf(110)),
                new Product(2,"小米手机",30,Double.valueOf(136)),
                new Product(3,"魅族手机",30,Double.valueOf(136)),
                new Product(1,"华为手机",20,Double.valueOf(110)),
                new Product(2,"小米手机",30,Double.valueOf(136)),
                new Product(3,"魅族手机",30,Double.valueOf(136)),
                new Product(1,"华为手机",20,Double.valueOf(110)),
                new Product(2,"小米手机",30,Double.valueOf(136)),
                new Product(3,"魅族手机",30,Double.valueOf(136)),
                new Product(1,"华为手机",20,Double.valueOf(110)),
                new Product(2,"小米手机",30,Double.valueOf(136)),
                new Product(3,"魅族手机",30,Double.valueOf(136)),
                new Product(1,"华为手机",20,Double.valueOf(110)),
                new Product(2,"小米手机",30,Double.valueOf(136)),
                new Product(3,"魅族手机",30,Double.valueOf(136)),
                new Product(1,"华为手机",20,Double.valueOf(110)),
                new Product(2,"小米手机",30,Double.valueOf(136)),
                new Product(3,"魅族手机",30,Double.valueOf(136)),
                new Product(1,"华为手机",20,Double.valueOf(110)),
                new Product(2,"小米手机",30,Double.valueOf(136)),
                new Product(3,"魅族手机",30,Double.valueOf(136)),
                new Product(1,"华为手机",20,Double.valueOf(110)),
                new Product(2,"小米手机",30,Double.valueOf(136)),
                new Product(3,"魅族手机",30,Double.valueOf(136)),
                new Product(1,"华为手机",20,Double.valueOf(110)),
                new Product(2,"小米手机",30,Double.valueOf(136)),
                new Product(3,"魅族手机",30,Double.valueOf(136)),
                new Product(1,"华为手机",20,Double.valueOf(110)),
                new Product(2,"小米手机",30,Double.valueOf(136)),
                new Product(3,"魅族手机",30,Double.valueOf(136)),
                new Product(1,"华为手机",20,Double.valueOf(110)),
                new Product(2,"小米手机",30,Double.valueOf(136)),
                new Product(3,"魅族手机",30,Double.valueOf(136)),
                new Product(1,"华为手机",20,Double.valueOf(110)),
                new Product(2,"小米手机",30,Double.valueOf(136)),
                new Product(3,"魅族手机",30,Double.valueOf(136)),
                new Product(1,"华为手机",20,Double.valueOf(110)),
                new Product(2,"小米手机",30,Double.valueOf(136)),
                new Product(3,"魅族手机",30,Double.valueOf(136)),
                new Product(1,"华为手机",20,Double.valueOf(110)),
                new Product(2,"小米手机",30,Double.valueOf(136)),
                new Product(3,"魅族手机",30,Double.valueOf(136)),
                new Product(1,"华为手机",20,Double.valueOf(110)),
                new Product(2,"小米手机",30,Double.valueOf(136)),
                new Product(3,"魅族手机",30,Double.valueOf(136)),
                new Product(1,"华为手机",20,Double.valueOf(110)),
                new Product(2,"小米手机",30,Double.valueOf(136)),
                new Product(3,"魅族手机",30,Double.valueOf(136)),
                new Product(1,"华为手机",20,Double.valueOf(110)),
                new Product(2,"小米手机",30,Double.valueOf(136)),
                new Product(3,"魅族手机",30,Double.valueOf(136)),
                new Product(1,"华为手机",20,Double.valueOf(110)),
                new Product(2,"小米手机",30,Double.valueOf(136)),
                new Product(3,"魅族手机",30,Double.valueOf(136)),
                new Product(4,"oppo手机",30,Double.valueOf(136)));
    }

    @Override
    public Product selectProductByPojo(Product product) {
        System.out.println(product);
        product.setProductName("回传:" + product.getProductName());
        return product;
    }

    @Override
    public Product selectProductById(Integer id) {
        Product p =  new Product(4,"oppo手机",30,Double.valueOf(136));
        p.setProductName("手工传入id-"+id);
        return p;
    }
}
