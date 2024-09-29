package com.Tredy.demo.Model;

import com.Tredy.demo.Domain.PaymentMethod;
import com.Tredy.demo.Domain.PaymentOrderStatus;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class PaymentOrder {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long amount;
    private PaymentOrderStatus paymentOrderStatus;
    private PaymentMethod paymentMethod;
    @ManyToOne
    private User user;
}
