package com.Tredy.demo.Model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Asset {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private double quantity;
    private double buyPrice;
    @ManyToOne
    private Bitcoin bitcoin;
    @ManyToOne
    private User user;

}
