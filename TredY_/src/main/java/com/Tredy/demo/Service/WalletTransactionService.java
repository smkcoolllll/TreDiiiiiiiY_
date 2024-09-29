package com.Tredy.demo.Service;

import com.Tredy.demo.Domain.WalletTransactionType;
import com.Tredy.demo.Model.WalletTransaction;

import java.util.List;

public interface WalletTransactionService {
    WalletTransaction createTransaction(Long senderWalletId, Long receiverWalletId, WalletTransactionType type, String purpose, Long amount) throws Exception;

    List<WalletTransaction> getTransactionsByWallet(Long walletId);

    WalletTransaction getTransactionById(Long transactionId);

    void deleteTransaction(Long transactionId);
}
