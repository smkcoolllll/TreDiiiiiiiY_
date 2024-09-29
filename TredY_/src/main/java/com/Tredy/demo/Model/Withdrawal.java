package com.Tredy.demo.Model;

import com.Tredy.demo.Domain.WithdrawalStatus;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Withdrawal {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private WithdrawalStatus withdrawalStatus;
    private  Long amount;
    @ManyToOne
    private User user;
    private LocalDateTime date=LocalDateTime.now();
}
