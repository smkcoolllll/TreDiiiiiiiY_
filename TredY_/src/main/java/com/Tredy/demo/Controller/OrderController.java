package com.Tredy.demo.Controller;

import com.Tredy.demo.Domain.OrderType;
import com.Tredy.demo.Model.Bitcoin;
import com.Tredy.demo.Model.Order;
import com.Tredy.demo.Model.User;
import com.Tredy.demo.Request.CreateOrderRequest;
import com.Tredy.demo.Service.BitcoinService;
import com.Tredy.demo.Service.OrderService;
import com.Tredy.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;
    @Autowired
    private BitcoinService bitcoinService;

    @PostMapping("/pay")
    public ResponseEntity<Order> payOrderPayment(@RequestHeader("Authorization") String jwt, @RequestBody CreateOrderRequest request) throws Exception {
        User user=userService.findUserByJWT(jwt);
        Bitcoin bitcoin=bitcoinService.findById(request.getBitCoinId());
        Order order=orderService.processOrder(bitcoin,request.getQuantity(),request.getOrderType(),user);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/getOrder/{orderId}")
    public  ResponseEntity<Order> getOrderById(@RequestHeader("Authorization") String jwt,@PathVariable Long orderId) throws Exception {
        User user=userService.findUserByJWT(jwt);
        Order order=orderService.getOrderById(orderId);
        if(order.getUser().getUserId().equals(user.getUserId())){
            return ResponseEntity.ok(order);
        }else {
            throw new Exception("You don't have access");
        }
    }

    @GetMapping("/orders")
    public ResponseEntity<List<Order>> getAllOrdersForUser(@RequestHeader("Authorization") String jwt, @RequestParam(required = false)OrderType orderType,@RequestParam(required = false) String asset_symbol) throws Exception {
        Long userId=userService.findUserByJWT(jwt).getUserId();
        List<Order> userOrders=orderService.getAllOrdersOfUser(userId,orderType,asset_symbol);
        return ResponseEntity.ok(userOrders);
    }

}


