package com.Tredy.demo.Service;

import com.Tredy.demo.Domain.OrderType;
import com.Tredy.demo.Model.Order;
import com.Tredy.demo.Model.User;
import com.Tredy.demo.Model.Wallet;
import com.Tredy.demo.Repository.WalletRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class WalletServiceIMPl implements WalletService {

    @Autowired
    private WalletRepo walletRepo;
    @Override
    public Wallet getUserWallet(User user) {
        Wallet wallet=walletRepo.findByUserUserId(user.getUserId());
        if(wallet==null){
            wallet=new Wallet();
            wallet.setUser(user);
            walletRepo.save(wallet);
        }
        return wallet;
    }

    @Override
    public Wallet addBalance(Wallet wallet, Long money) {
        BigDecimal balance=wallet.getBalance();
        BigDecimal newBalance=balance.add(BigDecimal.valueOf(money));
        wallet.setBalance(newBalance);
        return walletRepo.save(wallet);
    }

    @Override
    public Wallet findWalletById(Long walletId) throws Exception {
        Optional<Wallet> wallet=walletRepo.findById(walletId);
        if(wallet.isEmpty()){
            throw  new Exception("wallet not found");
        }
        return wallet.get();
    }

    @Override
    public Wallet walletToWalletTransfer(User sender, Wallet receiverWallet, Long amount) throws Exception {
        Wallet senderWwallet=getUserWallet(sender);
        if(senderWwallet.getBalance().compareTo(BigDecimal.valueOf(amount))<0){
            throw  new Exception("Insufficient balance");
        }
        BigDecimal senderBalance=senderWwallet.getBalance().subtract(BigDecimal.valueOf(amount));
        senderWwallet.setBalance(senderBalance);
        walletRepo.save(senderWwallet);
        BigDecimal receiverBalance=receiverWallet.getBalance().add(BigDecimal.valueOf(amount));
        receiverWallet.setBalance(receiverBalance);
        walletRepo.save(receiverWallet);
        return senderWwallet;
    }

    @Override
    public Wallet payOrderPayment(Order order, User user) throws Exception {
        Wallet wallet=getUserWallet(user);
        if(order.getOrderType().equals(OrderType.BUY)){
            BigDecimal newBalance=wallet.getBalance().subtract(order.getPrice());
            if(newBalance.compareTo(order.getPrice())<0){
                throw  new Exception("Insufficient funds for this transactions");
            }
            wallet.setBalance(newBalance);
        }else{
            BigDecimal newBalance=wallet.getBalance().add(order.getPrice());
            wallet.setBalance(newBalance);
        }
        walletRepo.save(wallet);
        return wallet;
    }
}
