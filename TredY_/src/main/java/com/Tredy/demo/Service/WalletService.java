package com.Tredy.demo.Service;

import com.Tredy.demo.Model.Order;
import com.Tredy.demo.Model.User;
import com.Tredy.demo.Model.Wallet;

public interface WalletService {

    Wallet getUserWallet(User user);
    Wallet addBalance(Wallet wallet,Long money);
    Wallet findWalletById(Long walletId) throws Exception;
    Wallet walletToWalletTransfer(User sender,Wallet receiverWallet, Long amount) throws Exception;
    Wallet payOrderPayment(Order order,User user) throws Exception;

}
