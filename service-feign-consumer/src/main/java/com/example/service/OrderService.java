package com.example.service;

import com.example.pojo.Order;

import java.util.List;
import java.util.concurrent.Future;

public interface OrderService {
    Order selectOrderById(Integer id);
    List<Order> QueryOrderList(List<Integer> ids);
    Future<Order> queryOrderById(Integer id);
    List<Order> selectOrderList(List<Integer> ids);
}
