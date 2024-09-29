package com.Tredy.demo.Service;

import com.Tredy.demo.Model.PaymentDetails;
import com.Tredy.demo.Model.User;
import com.Tredy.demo.Repository.PaymentDetailRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PaymentDetailServImpl implements PaymentDetailService{

    @Autowired
    private PaymentDetailRepo paymentDetailRepo;
    @Override
    public PaymentDetails addPaymentDetailsService(String accountNumber, String accountHolderName, String ifsc, String bankName, User user) {
        PaymentDetails paymentDetails=new PaymentDetails();
        paymentDetails.setAccountNumber(accountNumber);
        paymentDetails.setAccountHolderName(accountHolderName);
        paymentDetails.setIfsc(ifsc);
        paymentDetails.setBankName(bankName);
        paymentDetails.setUser(user);
        return paymentDetailRepo.save(paymentDetails);
    }

    @Override
    public PaymentDetails getUsersPaymentDetails(User user) {
        return paymentDetailRepo.findByUserUserId(user.getUserId());
    }
}
