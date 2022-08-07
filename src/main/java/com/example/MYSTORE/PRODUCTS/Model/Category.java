package com.example.MYSTORE.PRODUCTS.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "tea_and_category",joinColumns = @JoinColumn(name = "category_id"),inverseJoinColumns = @JoinColumn(name = "tea_id"))
    private Set<Tea> teas = new HashSet<>();

    public void addTea(Tea tea){
        this.teas.add(tea);
    }
    public Category() {
    }
    public Category(String name) {
        this.name = name;
    }
}
