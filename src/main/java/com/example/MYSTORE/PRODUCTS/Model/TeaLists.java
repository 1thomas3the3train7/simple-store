package com.example.MYSTORE.PRODUCTS.Model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Getter
@Setter
public class TeaLists {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "tea_and_list",joinColumns = @JoinColumn(name="list_id"),inverseJoinColumns = @JoinColumn(name="tea_id"))
    private Collection<Tea> teas1 = new ArrayList<>();
    public TeaLists(){}

}
