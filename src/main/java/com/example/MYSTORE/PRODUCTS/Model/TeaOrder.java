package com.example.MYSTORE.PRODUCTS.Model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class TeaOrder extends Tea{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int amount;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinTable(name = "order_and_tea",joinColumns = @JoinColumn(name = "order_id"),inverseJoinColumns = @JoinColumn(name = "tea_id"))
    private Tea tea;

}
