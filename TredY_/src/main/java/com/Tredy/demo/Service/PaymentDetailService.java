package com.Tredy.demo.Service;

import com.Tredy.demo.Model.PaymentDetails;
import com.Tredy.demo.Model.User;

public interface PaymentDetailService {
    public PaymentDetails addPaymentDetailsService(String accountNumber, String accountHolderName, String ifsc, String bankName, User user);

    public PaymentDetails getUsersPaymentDetails(User user);
}
