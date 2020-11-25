package com.example.service.impl;

import com.example.pojo.Order;
import com.example.pojo.Product;
import com.example.service.OrderService;
import com.example.service.ProductService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCollapser;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.hystrix.contrib.javanica.conf.HystrixPropertiesManager;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

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

    @HystrixCommand(groupKey = "order-productService-singlePool",// 服务名称，相同名称使用同一个线程池
            commandKey = "selectOrderById",// 接口名称，默认为方法名
            threadPoolKey = "order-productService-singlePool",// 线程池名称，相同名称使用同一个线程池
            commandProperties = {
                    // 超时时间，默认 1000ms
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",
                            value = "5000")
            },
            threadPoolProperties = {
                    // 线程池大小
                    @HystrixProperty(name = "coreSize", value = "3"),
                    // 队列等待阈值(最大队列长度，默认 -1)
                    @HystrixProperty(name = "maxQueueSize", value = "100"),
                    // 线程存活时间，默认 1min
                    @HystrixProperty(name = "keepAliveTimeMinutes", value = "2"),
                    // 超出队列等待阈值执行拒绝策略
                    @HystrixProperty(name = "queueSizeRejectionThreshold", value = "100")
            })
    @Override
    public Order selectOrderById(Integer id) {

        System.out.println(Thread.currentThread().getName() + "---selectOrderById---");

        return new Order(1,
                "R2000505000123",
                "东安路二店",
                120D,
                productService.selectProductList());
//                selectProductListByLoadBalancerClient());
//                selectProductListByLoadBalancerClient());
        //selectProductListByDiscoverClient());
    }

//    @HystrixCommand(groupKey = "order-service-list-pool",
//            threadPoolKey = "order-service-list-pool",
//            commandKey = "selectOrderList",
//            commandProperties = {
//                    //超时时间，默认1000ms
//                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",
//                            value = "5000")
//            },
//            threadPoolProperties = {
//                    //
//                    @HystrixProperty(name = "coreSize", value = "6"),
//                    @HystrixProperty(name = "maxQueueSize", value = "100"),
//                    @HystrixProperty(name = "keepAliveTimeMinutes", value = "2"),
//                    @HystrixProperty(name = "queueSizeRejectionThreshold", value = "100")
//            },
//            fallbackMethod = "selectOrderListFallback")
//    @Override
//    public List<Order> selectOrderList(List<Integer> ids) {
//
//        System.out.println(Thread.currentThread().getName() + "---selectOrderList---");
//        System.out.println(MessageFormat.format("--call selectOrderList: 合并的请求参数:{0}--", ids));
//
//        List<Order> orders = new ArrayList();
//        for (Integer i : ids) {
//
//            orders.add(new Order(i,
//                    "R00000" + i.toString(),
//                    "东安路" + i.toString() + "店",
//                    120D,
//                    Arrays.asList(productService.selectProductById(i))));
//        }
//
//        return orders;
//    }

    @HystrixCommand(
            commandProperties = {
                    //超时时间，默认1000ms
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "5000"),
                    @HystrixProperty(name = HystrixPropertiesManager.EXECUTION_ISOLATION_STRATEGY,value="SEMAPHORE"),
                    @HystrixProperty(name = HystrixPropertiesManager.EXECUTION_ISOLATION_SEMAPHORE_MAX_CONCURRENT_REQUESTS,value="6")
            },
            fallbackMethod = "selectOrderListFallback")
    @Override
    public List<Order> selectOrderList(List<Integer> ids) {

        System.out.println(Thread.currentThread().getName() + "---selectOrderList---");
        System.out.println(MessageFormat.format("--call selectOrderList: 合并的请求参数:{0}--", ids));

        List<Order> orders = new ArrayList();
        for (Integer i : ids) {

            orders.add(new Order(i,
                    "R00000" + i.toString(),
                    "东安路" + i.toString() + "店",
                    120D,
                    Arrays.asList(productService.selectProductById(i))));
        }

        return orders;
    }

    public List<Order> selectOrderListFallback(List<Integer> ids) {

        System.out.println(MessageFormat.format("selectOrderListFallback args:{0}",ids));

        List<Order> orders = new ArrayList();
        orders.add(new Order(1,
                "fallback_R00000" + 1,
                "fallback_东安路" + 1 + "店",
                120D,
                Arrays.asList(productService.selectProductById(1))));
        orders.add(new Order(2,
                "fallback_R00000" + 2,
                "fallback_东安路" + 2 + "店",
                130D,
                Arrays.asList(productService.selectProductById(2))));

        return orders;
    }

    @HystrixCollapser(batchMethod = "selectOrderList", // 合并请求方法
            scope = com.netflix.hystrix.HystrixCollapser.Scope.GLOBAL, // 请求方式
            collapserProperties = {
                    // 间隔多久的请求会进行合并，默认 10ms
                    @HystrixProperty(name = "timerDelayInMilliseconds", value = "200"),
                    // 批处理之前，批处理中允许的最大请求数
                    @HystrixProperty(name = "maxRequestsInBatch", value = "200")
            })
    @Override
    public Future<Order> queryOrderById(Integer id) {
        System.out.println("-----orderService-----selectProductById-----");
        return null;
    }

//    @HystrixCollapser(batchMethod = "selectOrderList",
//            scope = com.netflix.hystrix.HystrixCollapser.Scope.GLOBAL,
//            collapserProperties = {
//                    @HystrixProperty(name = "timerDelayInMilliseconds", value = "50"),
//                    @HystrixProperty(name = "maxRequestsInBatch", value = "200")
//            })
//    public Future<Order> queryOrderById(Integer id) {
//        System.out.println(MessageFormat.format("--future selectOrderById:{0}--",id));
//        //不会进这个方法体
//        return null;
//    }

    @Override
    public List<Order> QueryOrderList(List<Integer> ids) {
        Future<Order> p1 = queryOrderById(1);
        Future<Order> p2 = queryOrderById(2);
        Future<Order> p3 = queryOrderById(3);
        Future<Order> p4 = queryOrderById(4);
        Future<Order> p5 = queryOrderById(5);

        try {
            System.out.println(p1.get());
            System.out.println(p2.get());
            System.out.println(p3.get());
            System.out.println(p4.get());
            System.out.println(p5.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Arrays.asList(new Order(1,
                "R2111111",
                "东安路了店",
                130D,
                null));
    }

    private List<Product> selectProductWithLoadBalancered() {

        ResponseEntity<List<Product>> responseEntity = restTemplate.exchange("http://service-provider/product/list",
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Product>>() {
                });

        return responseEntity.getBody();
    }

    private List<Product> selectProductListByLoadBalancerClient() {
        ServiceInstance si = loadBalancerClient.choose("service-provider");
        if (si == null) {
            return null;
        }

        String requestUrl = MessageFormat.format("http://{0}:{1}/product/list", si.getHost(), String.valueOf(si.getPort()));
        System.out.println(requestUrl);

        ResponseEntity<List<Product>> response = restTemplate.exchange(
                requestUrl,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<Product>>() {
                }
        );

        return response.getBody();
    }

    private List<Product> selectProductListByDiscoverClient() {
        StringBuffer sb = null;

        List<String> serviceIds = discoveryClient.getServices();
        if (CollectionUtils.isEmpty(serviceIds))
            return null;

        List<ServiceInstance> serviceInstances = discoveryClient.getInstances("service-provider");
        if (CollectionUtils.isEmpty(serviceInstances)) {
            return null;
        }

        ServiceInstance si = serviceInstances.get(0);
        sb = new StringBuffer();
        sb.append("http://" + si.getHost() + ":" + si.getPort() + "/product/list");

        ResponseEntity<List<Product>> response = restTemplate.exchange(
                sb.toString(), HttpMethod.GET, null, new ParameterizedTypeReference<List<Product>>() {
                }
        );

        return response.getBody();
    }
}
