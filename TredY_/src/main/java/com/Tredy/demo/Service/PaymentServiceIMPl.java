package com.Tredy.demo.Service;

import com.Tredy.demo.Domain.PaymentMethod;
import com.Tredy.demo.Domain.PaymentOrderStatus;
import com.Tredy.demo.Model.PaymentOrder;
import com.Tredy.demo.Model.User;
import com.Tredy.demo.Repository.PaymentOrderRepo;
import com.Tredy.demo.Response.PaymentResponse;
import com.razorpay.Payment;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import netscape.javascript.JSObject;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceIMPl implements PaymentService{

    @Autowired
    private PaymentOrderRepo paymentOrderRepo;
    @Value("${stripe.api.key}")
    private String stripeSecretKey;
    @Value("${razorpay.api.key}")
    private String apiKey;
    @Value("${razorpay.api.secret")
    private String apiSecretKey;
    @Override
    public PaymentOrder createOrder(User user, Long amount, PaymentMethod paymentMethod) {
        PaymentOrder paymentOrder=new PaymentOrder();
        paymentOrder.setUser(user);
        paymentOrder.setAmount(amount);
        paymentOrder.setPaymentMethod(paymentMethod);
        paymentOrder.setPaymentOrderStatus(PaymentOrderStatus.PENDING);
        return paymentOrderRepo.save(paymentOrder);
    }

    @Override
    public PaymentOrder getPaymentOrderById(Long id) throws Exception {
        return paymentOrderRepo.findById(id).orElseThrow(() -> new Exception("payment order not found"));
    }

    @Override
    public Boolean proceedPaymentOrder(PaymentOrder paymentOrder, String paymentId) throws RazorpayException {

        if(paymentOrder.getPaymentOrderStatus()==null){
            paymentOrder.setPaymentOrderStatus(PaymentOrderStatus.PENDING);
        }

        if(paymentOrder.getPaymentOrderStatus().equals(PaymentOrderStatus.PENDING)){
            if(paymentOrder.getPaymentMethod().equals(PaymentMethod.RAZORPAY)){
                RazorpayClient razorpayClient=new RazorpayClient(apiKey,apiSecretKey);
                Payment payment=razorpayClient.payments.fetch(paymentId);
                Integer amount=payment.get("amount");
                String status=payment.get("status");
                if(status.equals("captured")){
                    paymentOrder.setPaymentOrderStatus(PaymentOrderStatus.SUCCESS);
                    return  true;
                }
                paymentOrder.setPaymentOrderStatus(PaymentOrderStatus.FAILED);
                paymentOrderRepo.save(paymentOrder);
                return  false;
            }
            paymentOrder.setPaymentOrderStatus(PaymentOrderStatus.SUCCESS);
            paymentOrderRepo.save(paymentOrder);
            return  true;
        }
        return false;
    }

    @Override
    public PaymentResponse createRazorpayPaymentLing(User user, Long amount,Long orderId) throws RazorpayException {
        Long Amount=amount*100;
        try{
            RazorpayClient razorpayClient=new RazorpayClient(apiKey,apiSecretKey);
            JSONObject paymentLinkRequest=new JSONObject();
            paymentLinkRequest.put("amount",amount);
            paymentLinkRequest.put("currency","INR");

            JSONObject customer =new JSONObject();
            customer.put("name",user.getFullName());
            customer.put("email",user.getEmail());
            paymentLinkRequest.put("customer",customer);
            JSONObject notify=new JSONObject();
            notify.put("email",true);
            paymentLinkRequest.put("notify",notify);
            paymentLinkRequest.put("reminder_enable",true);
            paymentLinkRequest.put("callback_url","http://localhost:5055/api/wallet?order_id="+orderId);
            paymentLinkRequest.put("callback_method","get");

            PaymentLink paymentLink=razorpayClient.paymentLink.create(paymentLinkRequest);
            String paymentLinkId=paymentLink.get("id");
            String paymentLinkUrl=paymentLink.get("short_url");
            PaymentResponse paymentResponse=new PaymentResponse();
            paymentResponse.setPayment_url(paymentLinkUrl);
            return paymentResponse;
        }catch (RazorpayException e){
            System.out.println("Error creating payment link : "+e.getMessage());
            throw  new RazorpayException(e.getMessage());
        }
    }

    @Override
    public PaymentResponse createStripePaymentLing(User user, Long amount, Long orderId) throws StripeException {
        Stripe.apiKey=stripeSecretKey;
        SessionCreateParams params=SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl("http://localhost:5055/api/wallet?order_id="+orderId)
                .setCancelUrl("http://localhost:5055/payment/cancel")
                .addLineItem(SessionCreateParams.LineItem.builder()
                        .setQuantity(1L)
                        .setPriceData(
                                SessionCreateParams.LineItem.PriceData.builder()
                                        .setCurrency("usd")
                                        .setUnitAmount(amount*100)
                                        .setProductData(SessionCreateParams
                                                .LineItem
                                                .PriceData
                                                .ProductData
                                                .builder()
                                                .setName("Top up wallet")
                                                .build()
                                        ).build()

                        ).build()
                ).build();
        Session session=Session.create(params);
        System.out.println("session ________"+session);
        PaymentResponse paymentResponse=new PaymentResponse();
        paymentResponse.setPayment_url(session.getUrl());
        return paymentResponse;
    }
}
