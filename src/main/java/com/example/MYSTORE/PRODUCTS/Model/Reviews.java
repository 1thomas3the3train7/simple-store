package com.example.MYSTORE.PRODUCTS.Model;

import com.example.MYSTORE.SECURITY.Model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.File;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
public class Reviews {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String pluses;
    private String minuses;
    private String comment;
    private double grade;
    private String username;
    @JsonIgnore
    @ManyToOne(cascade = CascadeType.PERSIST,fetch = FetchType.LAZY)
    @JoinTable(name="user_and_review",joinColumns = @JoinColumn(name = "review_id"),inverseJoinColumns = @JoinColumn(name="user_id"))
    private User user;
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinTable(name="tea_and_review",joinColumns = @JoinColumn(name = "review_id"),inverseJoinColumns = @JoinColumn(name="tea_id"))
    private Tea tea;
    public Reviews() {}

    public Reviews(String pluses, String minuses, String comment, double grade) {
        this.pluses = pluses;
        this.minuses = minuses;
        this.comment = comment;
        this.grade = grade;
    }
}
