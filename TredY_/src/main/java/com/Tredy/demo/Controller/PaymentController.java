package com.Tredy.demo.Controller;

import com.Tredy.demo.Domain.PaymentMethod;
import com.Tredy.demo.Model.PaymentOrder;
import com.Tredy.demo.Model.User;
import com.Tredy.demo.Response.PaymentResponse;
import com.Tredy.demo.Service.PaymentService;
import com.Tredy.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {

    @Autowired
    private UserService userService;
    @Autowired
    private PaymentService paymentService;

    @PostMapping("/paymentHandler/{paymentMethod}/amount/{amount}")
    public ResponseEntity<PaymentResponse> paymentHandler(@PathVariable PaymentMethod paymentMethod, @PathVariable Long amount, @RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findUserByJWT(jwt);
        PaymentResponse paymentResponse;
        PaymentOrder paymentOrder=paymentService.createOrder(user,amount,paymentMethod);
        if(paymentMethod.equals(PaymentMethod.RAZORPAY)){
            paymentResponse=paymentService.createRazorpayPaymentLing(user,amount,paymentOrder.getId());
        }else {
            paymentResponse=paymentService.createStripePaymentLing(user,amount,paymentOrder.getId());
        }
        return new ResponseEntity<>(paymentResponse, HttpStatus.CREATED);
    }
}
