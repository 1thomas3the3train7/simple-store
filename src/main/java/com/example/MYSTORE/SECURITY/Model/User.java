package com.example.MYSTORE.SECURITY.Model;

import com.example.MYSTORE.PRODUCTS.Model.Reviews;
import com.example.MYSTORE.PRODUCTS.Model.Tea;
import com.example.MYSTORE.PRODUCTS.Model.TeaLists;
import com.example.MYSTORE.SECURITY.JWT.JWTRefreshToken;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

@Entity
@Data
@Table(name = "users")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String username;
    @JsonIgnore
    private String password;
    @JsonIgnore
    private boolean myenabled;
    @JsonIgnore
    private boolean banned;
    public boolean getMyEnabled(){
        return this.myenabled;
    }
    public boolean getBanned(){return this.banned;}
    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name="role_and_user", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Collection<Role> roles;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name="user_and_jwt", joinColumns = @JoinColumn(name="user_id"), inverseJoinColumns = @JoinColumn(name = "jwt_id"))
    @JsonIgnore
    private JWTRefreshToken jwtRefreshToken;
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name="user_and_review", joinColumns = @JoinColumn(name = "user_id"),inverseJoinColumns = @JoinColumn(name = "review_id"))
    private Set<Reviews> reviews = new HashSet<>();
    @JsonIgnore
    @OneToOne
    @JoinTable(name="user_and_vtoken",joinColumns = @JoinColumn(name = "user_id"),inverseJoinColumns = @JoinColumn(name = "token_id"))
    private VerificationToken verificationToken;
    @JsonIgnore
    @OneToOne(cascade = CascadeType.ALL)
    @JoinTable(name = "user_and_rtoken",joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "token_id"))
    private ResetPasswordToken resetPasswordToken;
    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_and_tea",joinColumns = @JoinColumn(name = "user_id"),inverseJoinColumns = @JoinColumn(name = "tea_id"))
    private List<Tea> teas = new ArrayList<>();
    public void addTea(Tea tea){
        this.teas.add(tea);
    }
    public void delTea(Tea tea){this.teas.remove(tea);}
    public void addReview(Reviews reviews){
        this.reviews.add(reviews);
    }
    public User(String email, String password) {
        this.email = email;
        this.password = password;
        this.roles = List.of(new Role("ROLE_USER"));
        this.banned = false;
    }
    public User(){}
}
