package com.example.service.impl;

import com.example.pojo.Order;
import com.example.pojo.Product;
import com.example.service.OrderService;
import com.example.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    LoadBalancerClient loadBalancerClient; //ribbon负载均衡实现
/*
* 1. 源数据对象 spring的discoveryClient
* 2. Ribbon对象 netflix的discoveryClient
* 3. Ribbon 注解
*
*
*   1.DiscoveryClient：通过元数据获取服务信息
    2.LoadBalancerClient：Ribbon 的负载均衡器
    3.@LoadBalanced：通过注解开启 Ribbon 的负载均衡器
* */
    @Autowired
    DiscoveryClient discoveryClient;

    @Autowired
    ProductService productService;

    @Override
    public Order selectOrderById(Integer id) {

        return new Order(1,
                "R2000505000123",
                "东安路二店",
                120D,
                productService.selectProductList());
//                selectProductListByLoadBalancerClient());
//                selectProductListByLoadBalancerClient());
                //selectProductListByDiscoverClient());
    }

    private List<Product> selectProductWithLoadBalancered(){
        ResponseEntity<List<Product>> responseEntity =  restTemplate.exchange("http://service-provider/product/list",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Product>>() {
                });

        return responseEntity.getBody();
    }

    private List<Product> selectProductListByLoadBalancerClient(){
        ServiceInstance si = loadBalancerClient.choose("service-provider");
        if(si == null){
            return null;
        }

        String requestUrl = MessageFormat.format("http://{0}:{1}/product/list",si.getHost(),String.valueOf(si.getPort()));
        System.out.println(requestUrl);

        ResponseEntity<List<Product>> response = restTemplate.exchange(
                requestUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Product>>(){}
        );

        return response.getBody();
    }

    private List<Product> selectProductListByDiscoverClient(){
        StringBuffer sb = null;

        List<String> serviceIds = discoveryClient.getServices();
        if(CollectionUtils.isEmpty(serviceIds))
            return null;

        List<ServiceInstance> serviceInstances = discoveryClient.getInstances("service-provider");
        if(CollectionUtils.isEmpty(serviceInstances)){
            return null;
        }

        ServiceInstance si = serviceInstances.get(0);
        sb = new StringBuffer();
        sb.append("http://"+si.getHost()+":"+si.getPort()+"/product/list");

        ResponseEntity<List<Product>> response = restTemplate.exchange(
                sb.toString(),HttpMethod.GET, null, new ParameterizedTypeReference<List<Product>>(){}
        );

        return response.getBody();
    }
}
