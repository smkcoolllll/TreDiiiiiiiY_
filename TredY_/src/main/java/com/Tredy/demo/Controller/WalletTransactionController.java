package com.Tredy.demo.Controller;

import com.Tredy.demo.Model.WalletTransaction;
import com.Tredy.demo.Service.WalletTransactionService;
import com.Tredy.demo.Domain.WalletTransactionType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/walletTransactions")
public class WalletTransactionController {

    @Autowired
    private WalletTransactionService walletTransactionService;

    @PostMapping
    public ResponseEntity<WalletTransaction> createTransaction(
            @RequestParam Long senderWalletId,
            @RequestParam Long receiverWalletId,
            @RequestParam WalletTransactionType type,
            @RequestParam String purpose,
            @RequestParam Long amount) throws Exception {

        WalletTransaction transaction = walletTransactionService.createTransaction(senderWalletId, receiverWalletId, type, purpose, amount);
        return ResponseEntity.ok(transaction);
    }

    @GetMapping("/{walletId}")
    public ResponseEntity<List<WalletTransaction>> getTransactionsByWallet(@PathVariable Long walletId) {
        List<WalletTransaction> transactions = walletTransactionService.getTransactionsByWallet(walletId);
        return ResponseEntity.ok(transactions);
    }

    @GetMapping("/transaction/{transactionId}")
    public ResponseEntity<WalletTransaction> getTransactionById(@PathVariable Long transactionId) {
        WalletTransaction transaction = walletTransactionService.getTransactionById(transactionId);
        return ResponseEntity.ok(transaction);
    }

    @DeleteMapping("/transaction/{transactionId}")
    public ResponseEntity<Void> deleteTransaction(@PathVariable Long transactionId) {
        walletTransactionService.deleteTransaction(transactionId);
        return ResponseEntity.noContent().build();
    }
}
