package com.example.MYSTORE.SECURITY.Model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String role_name;

    public Role(){}

    public Role(String role_name) {
        this.role_name = role_name;
    }
}
