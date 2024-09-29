package com.Tredy.demo.Service;

import com.Tredy.demo.Domain.OrderType;
import com.Tredy.demo.Model.Bitcoin;
import com.Tredy.demo.Model.Order;
import com.Tredy.demo.Model.OrderItem;
import com.Tredy.demo.Model.User;

import java.util.List;

public interface OrderService {
    Order createOrder(User user, OrderItem orderItem, OrderType orderType);
    Order getOrderById(Long orderId) throws Exception;
    List<Order> getAllOrdersOfUser(Long userId,OrderType orderType,String assetSymbol);
    Order processOrder(Bitcoin bitcoin,double quantity,OrderType orderType,User user) throws Exception;

}
