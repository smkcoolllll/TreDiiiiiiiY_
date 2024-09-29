package com.Tredy.demo.Controller;

import com.Tredy.demo.Model.PaymentDetails;
import com.Tredy.demo.Model.User;
import com.Tredy.demo.Service.PaymentDetailService;
import com.Tredy.demo.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/paymentDetail")
public class PaymentDetailController {

    @Autowired
    private PaymentDetailService paymentDetailService;

    @Autowired
    private UserService userService;

    @PostMapping("/add/payment")
    public ResponseEntity<PaymentDetails> addPaymentDetail(@RequestHeader("Authorization") String jwt, @RequestBody PaymentDetails paymentDetails) throws Exception {
        User user=userService.findUserByJWT(jwt);
        PaymentDetails paymentDetails1=paymentDetailService.addPaymentDetailsService(
                paymentDetails.getAccountNumber(),
                paymentDetails.getAccountHolderName(),
                paymentDetails.getIfsc(),
                paymentDetails.getBankName(),
                user
        );
        return  new ResponseEntity<>(paymentDetails1, HttpStatus.OK);
    }


    @GetMapping("/get/payment")
    public  ResponseEntity<PaymentDetails> getUsersPaymentDetails(@RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findUserByJWT(jwt);
        PaymentDetails paymentDetails=paymentDetailService.getUsersPaymentDetails(user);
        return  new ResponseEntity<>(paymentDetails,HttpStatus.CREATED);
    }
}
