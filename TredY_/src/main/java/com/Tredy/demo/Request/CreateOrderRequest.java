package com.Tredy.demo.Request;

import com.Tredy.demo.Domain.OrderType;
import lombok.Data;

@Data
public class CreateOrderRequest {
    private  String bitCoinId;
    private double quantity;
    private OrderType orderType;
}
