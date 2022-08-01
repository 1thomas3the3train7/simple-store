package com.example.MYSTORE.PRODUCTS.Model;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Data
public class TeaLists {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
    @JoinTable(name = "tea_and_list",joinColumns = @JoinColumn(name="list_id"),inverseJoinColumns = @JoinColumn(name="tea_id"))
    private Collection<Tea> teas1 = new ArrayList<>();
    public TeaLists(){}

    public TeaLists(String name) {
        this.name = name;
    }

    public void addTea(Tea tea){
        this.teas1.add(tea);
        tea.addList(this);
    }
}
