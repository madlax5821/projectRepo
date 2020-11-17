package com.ascending.dao;

import com.ascending.model.Config;
import com.ascending.model.Order;

import java.util.List;

public interface OrderDao {
    Order save(Order order, Config config);
    boolean delete(Order order);
    boolean update(Order order, Config config);
    List<Order> getOrders();
    boolean deleteByOrderName(String name);
    Order getOrderById(long id);
    Order getOrderByName(String orderNumber);
    boolean deleteByOrderId(long id);
}
