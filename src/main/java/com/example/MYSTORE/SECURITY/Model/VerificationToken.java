package com.example.MYSTORE.SECURITY.Model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class VerificationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;
    @OneToOne
    @JoinTable(name="user_and_vtoken",joinColumns = @JoinColumn(name = "token_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
    private User user;

    private Date date;

    public VerificationToken(String token, User user) {
        this.token = token;
        this.user = user;
        this.date = new Date();
    }

    public VerificationToken(){}
}
