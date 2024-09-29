package com.Tredy.demo.Service;

import com.Tredy.demo.Model.Wallet;
import com.Tredy.demo.Model.WalletTransaction;
import com.Tredy.demo.Domain.WalletTransactionType;
import com.Tredy.demo.Repository.WalletRepo;
import com.Tredy.demo.Repository.WalletTransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class WalletTransactionServiceImpl implements WalletTransactionService {

    @Autowired
    private WalletTransactionRepository walletTransactionRepository;

    @Autowired
    private WalletRepo walletRepository;

    @Override
    public WalletTransaction createTransaction(Long senderWalletId, Long receiverWalletId, WalletTransactionType type, String purpose, Long amount) throws Exception {
        Optional<Wallet> senderWalletOptional = walletRepository.findById(senderWalletId);
        Optional<Wallet> receiverWalletOptional = walletRepository.findById(receiverWalletId);

        if (senderWalletOptional.isEmpty()) {
            throw new Exception("Sender wallet not found");
        }

        if (receiverWalletOptional.isEmpty()) {
            throw new Exception("Receiver wallet not found");
        }

        Wallet senderWallet = senderWalletOptional.get();
        Wallet receiverWallet = receiverWalletOptional.get();

        BigDecimal transactionAmount = BigDecimal.valueOf(amount);

        if (type == WalletTransactionType.WALLET_TRANSFER) {
            // Check sender wallet balance
            if (senderWallet.getBalance().compareTo(transactionAmount) < 0) {
                throw new Exception("Insufficient funds");
            }

            // Update sender and receiver balances
            senderWallet.setBalance(senderWallet.getBalance().subtract(transactionAmount));
            receiverWallet.setBalance(receiverWallet.getBalance().add(transactionAmount));

            walletRepository.save(senderWallet);
            walletRepository.save(receiverWallet);
        }

        // Create transaction record
        WalletTransaction transaction = new WalletTransaction();
        transaction.setWallet(senderWallet);
        transaction.setType(type);
        transaction.setPurpose(purpose);
        transaction.setAmount(amount);
        transaction.setLocalDate(LocalDate.now());

        return walletTransactionRepository.save(transaction);
    }

    @Override
    public List<WalletTransaction> getTransactionsByWallet(Long walletId) {
        return walletTransactionRepository.findByWalletId(walletId);
    }

    @Override
    public WalletTransaction getTransactionById(Long transactionId) {
        return walletTransactionRepository.findById(transactionId)
                .orElseThrow(() -> new RuntimeException("Transaction not found"));
    }

    @Override
    public void deleteTransaction(Long transactionId) {
        if (!walletTransactionRepository.existsById(transactionId)) {
            throw new RuntimeException("Transaction not found");
        }
        walletTransactionRepository.deleteById(transactionId);
    }
}
