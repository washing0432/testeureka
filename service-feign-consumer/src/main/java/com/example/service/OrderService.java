package com.example.service;

import com.example.pojo.Order;

public interface OrderService {
    Order selectOrderById(Integer id);
}
