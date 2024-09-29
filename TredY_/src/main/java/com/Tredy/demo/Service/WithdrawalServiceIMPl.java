package com.Tredy.demo.Service;

import com.Tredy.demo.Domain.WithdrawalStatus;
import com.Tredy.demo.Model.User;
import com.Tredy.demo.Model.Withdrawal;
import com.Tredy.demo.Repository.WithdrawalRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class WithdrawalServiceIMPl implements WithdrawalService {

    @Autowired
    private WithdrawalRepo withdrawalRepo;
    @Override
    public Withdrawal requestWithdrawal(Long amount, User user) {
        Withdrawal withdrawal=new Withdrawal();
        withdrawal.setAmount(amount);
        withdrawal.setUser(user);
        withdrawal.setWithdrawalStatus(WithdrawalStatus.PENDING);
        return withdrawalRepo.save(withdrawal);

    }

    @Override
    public Withdrawal proceedWithWithdrawal(Long withdrawalId, boolean accept) throws Exception {
        Optional<Withdrawal> withdrawal=withdrawalRepo.findById(withdrawalId);
        if(withdrawal.isEmpty()){
            throw  new Exception("Withdrawal not found");
        }
        Withdrawal withdrawal1=withdrawal.get();
        withdrawal1.setDate(LocalDateTime.now());
        if(accept){
            withdrawal1.setWithdrawalStatus(WithdrawalStatus.SUCCESS);
        }else {
            withdrawal1.setWithdrawalStatus(WithdrawalStatus.PENDING);
        }
        return withdrawalRepo.save(withdrawal1);
    }

    @Override
    public List<Withdrawal> getUsersWithdrawalHistory(User user) {

        return withdrawalRepo.findByUserUserId(user.getUserId());
    }

    @Override
    public List<Withdrawal> getAllWithdrawalRequest() {
        return withdrawalRepo.findAll();
    }
}
