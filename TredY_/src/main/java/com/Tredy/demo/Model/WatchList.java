package com.Tredy.demo.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class WatchList {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne
    private  User user;
    @ManyToMany
    private List<Bitcoin> bitcoinList=new ArrayList<>();
}
