package com.Tredy.demo.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;


    private double quantity;

    @ManyToOne
    private Bitcoin bitcoin;

    private double buyPrice;
    private  double sellPrice;

    @JsonIgnore
    @OneToOne
    private  Order order;
}
