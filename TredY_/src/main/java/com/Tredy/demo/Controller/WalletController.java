package com.Tredy.demo.Controller;

import com.Tredy.demo.Domain.WalletTransactionType;
import com.Tredy.demo.Model.*;
import com.Tredy.demo.Response.PaymentResponse;
import com.Tredy.demo.Service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api/wallet")
public class WalletController {

    @Autowired
    private WalletService walletService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private WalletTransactionService walletTransactionService;



    @GetMapping("/userWallet")
    public ResponseEntity<Wallet> getUserWallet(@RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findUserByJWT(jwt);
        Wallet wallet=walletService.getUserWallet(user);
        return  new ResponseEntity<>(wallet, HttpStatus.ACCEPTED);
    }

    @PutMapping("/transfer/{walletId}")
    public ResponseEntity<Wallet> walletToWalletTransfer(@RequestHeader("Authorization") String jwt, @PathVariable Long walletId, @RequestBody WalletTransaction request) throws Exception {
        User senderUser=userService.findUserByJWT(jwt);
        Wallet receiverWallet= walletService.findWalletById(walletId);
        Wallet wallet=walletService.walletToWalletTransfer(senderUser,receiverWallet,request.getAmount());

        // Create a wallet transaction record
        walletTransactionService.createTransaction(
                wallet.getId(),
                receiverWallet.getId(),
                WalletTransactionType.WALLET_TRANSFER,
                request.getPurpose(),
                request.getAmount()
        );

        return new ResponseEntity<>(wallet,HttpStatus.ACCEPTED);
    }

    @PutMapping("/order/{orderId}/pay")
    public ResponseEntity<Wallet> payOrderPayment(@RequestHeader("Authorization") String jwt, @PathVariable Long orderId) throws Exception {
        User user=userService.findUserByJWT(jwt);
        Order order=orderService.getOrderById(orderId);
        Wallet wallet=walletService.payOrderPayment(order,user);

        return new ResponseEntity<>(wallet,HttpStatus.ACCEPTED);
    }


    @PutMapping("/wallet/deposit")
    public ResponseEntity<Wallet> addBalanceToWallet(@RequestHeader("Authorization") String jwt,@RequestParam(name = "order_id") Long orderId ,@RequestHeader(name = "payment_id") String paymentId) throws Exception {
        User user=userService.findUserByJWT(jwt);
        Wallet wallet=walletService.getUserWallet(user);
        PaymentOrder paymentOrder=paymentService.getPaymentOrderById(orderId);
        Boolean status=paymentService.proceedPaymentOrder(paymentOrder,paymentId);

        if (wallet.getBalance()==null){
            wallet.setBalance(BigDecimal.valueOf(0));
        }
//        PaymentResponse paymentResponse=new PaymentResponse();
        if(status){
            wallet=walletService.addBalance(wallet,paymentOrder.getAmount());
        }

        return new ResponseEntity<>(wallet,HttpStatus.ACCEPTED);
    }


















}
