package com.Tredy.demo.Service;

import com.Tredy.demo.Domain.PaymentMethod;
import com.Tredy.demo.Model.PaymentOrder;
import com.Tredy.demo.Model.User;
import com.Tredy.demo.Response.PaymentResponse;
import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;

public interface PaymentService {

    PaymentOrder createOrder(User user, Long amount, PaymentMethod paymentMethod);
    PaymentOrder getPaymentOrderById(Long id) throws Exception;
    Boolean proceedPaymentOrder(PaymentOrder paymentOrder,String paymentId) throws RazorpayException;
    PaymentResponse createRazorpayPaymentLing(User user,Long amount,Long orderId) throws RazorpayException;
    PaymentResponse createStripePaymentLing(User user,Long amount,Long orderId) throws StripeException;

}
