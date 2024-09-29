package com.Tredy.demo.Controller;

import com.Tredy.demo.Domain.WalletTransactionType;
import com.Tredy.demo.Model.User;
import com.Tredy.demo.Model.Wallet;
import com.Tredy.demo.Model.WalletTransaction;
import com.Tredy.demo.Model.Withdrawal;
import com.Tredy.demo.Service.UserService;
import com.Tredy.demo.Service.WalletService;
import com.Tredy.demo.Service.WalletTransactionService;
import com.Tredy.demo.Service.WithdrawalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/withdrawal")
public class WithdrawalController {
    @Autowired
    private WithdrawalService withdrawalService;
    @Autowired
    private WalletService walletService;
    @Autowired
    private UserService userService;

    @Autowired
    private WalletTransactionService walletTransactionService;

    @PostMapping("/{amount}")
    public ResponseEntity<?> withdrawalRequest(@RequestHeader("Authorization") String jwt, @PathVariable Long amount) throws Exception {

        User user=userService.findUserByJWT(jwt);
        Wallet wallet=walletService.getUserWallet(user);
        Withdrawal withdrawal=withdrawalService.requestWithdrawal(amount,user);
        walletService.addBalance(wallet,-withdrawal.getAmount());
       WalletTransaction walletTransaction=walletTransactionService.createTransaction(null, wallet.getId(), WalletTransactionType.WITHDRAWAL, "bank account withdrawal", withdrawal.getAmount());
        return new ResponseEntity<>(withdrawal, HttpStatus.OK);
    }

//    @PatchMapping("/admin/(id}/proceed/{accept}")
//    public ResponseEntity<?> proceedWithdrawal(@PathVariable Long id,@PathVariable boolean accept,@RequestHeader("Authorization") String jwt) throws Exception {
//        User user=userService.findUserByJWT(jwt);
//        Withdrawal withdrawal=withdrawalService.proceedWithWithdrawal(id,accept);
//        Wallet wallet=walletService.getUserWallet(user);
//        if(!accept){
//            walletService.addBalance(wallet,withdrawal.getAmount());
//        }
//        return  new ResponseEntity<>(withdrawal,HttpStatus.OK);
//    }

    @PatchMapping("/admin/{id}/proceed/{accept}")
    public ResponseEntity<?> proceedWithdrawal(@PathVariable Long id, @PathVariable boolean accept, @RequestHeader("Authorization") String jwt) throws Exception {
        User user = userService.findUserByJWT(jwt);
        Withdrawal withdrawal = withdrawalService.proceedWithWithdrawal(id, accept);
        Wallet wallet = walletService.getUserWallet(user);
        if (!accept) {
            walletService.addBalance(wallet, withdrawal.getAmount());
        }
        return new ResponseEntity<>(withdrawal, HttpStatus.OK);
    }


    @GetMapping("/get")
    public ResponseEntity<List<Withdrawal>> getWithdrawalHistory(@RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findUserByJWT(jwt);
        List<Withdrawal> withdrawals=withdrawalService.getUsersWithdrawalHistory(user);
        return new ResponseEntity<>(withdrawals,HttpStatus.OK);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<Withdrawal>> getAllWithdrawalRequest(@RequestHeader("Authorization") String jwt) throws Exception {
        User user=userService.findUserByJWT(jwt);
        List<Withdrawal> withdrawals=withdrawalService.getAllWithdrawalRequest();
        return new ResponseEntity<>(withdrawals,HttpStatus.OK);
    }
}
