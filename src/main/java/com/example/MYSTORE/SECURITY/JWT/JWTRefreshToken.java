package com.example.MYSTORE.SECURITY.JWT;

import com.example.MYSTORE.SECURITY.Model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class JWTRefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore
    private Long id;
    private String refreshToken;
    @OneToOne( fetch = FetchType.EAGER)
    @JoinTable(name="user_and_jwt",joinColumns = @JoinColumn(name = "jwt_id"),inverseJoinColumns = @JoinColumn(name = "user_id"))
    @JsonIgnore
    private User user;

    public JWTRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
    public JWTRefreshToken(){}
}
