package com.example.MYSTORE.SECURITY.Model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class ResetPasswordToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tokenreset;
    private Date date;
    @OneToOne
    @JoinTable(name = "user_and_rtoken",joinColumns = @JoinColumn(name = "token_id"),inverseJoinColumns = @JoinColumn(name = "user_id"))
    private User user;

    public ResetPasswordToken(String tokenreset,User user) {
        this.tokenreset = tokenreset;
        this.user = user;
        this.date = new Date();
    }

    public ResetPasswordToken() {}
}
