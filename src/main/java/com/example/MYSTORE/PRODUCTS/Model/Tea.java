package com.example.MYSTORE.PRODUCTS.Model;


import com.example.MYSTORE.SECURITY.Model.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


import javax.persistence.*;
import java.util.*;


@Entity
@Getter
@Setter
public class Tea {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;
    private String subname;
    private String name;
    private String madeCountry;
    private String about;
    private String fermentation;
    private Integer price;
    private Integer oldPrice;
    private String methodCook;
    private String mainLinkImage;
    private Integer presence;
    private double grade;
    public Tea(){}
    public Tea(String madeCountry, String about, String fermentation, Integer price,
               String methodCook) {
        this.madeCountry = madeCountry;
        this.about = about;
        this.fermentation = fermentation;
        this.price = price;
        this.methodCook = methodCook;
    }

    public Tea(String madeCountry, String about, String fermentation,
               Integer price, String methodCook,String mainLinkImage ,
               Integer presence,Integer grade,
               String name) {
        this.madeCountry = madeCountry;
        this.about = about;
        this.fermentation = fermentation;
        this.price = price;
        this.methodCook = methodCook;
        this.presence = presence;
        this.grade = grade;
        this.name = name;
        this.mainLinkImage = mainLinkImage;
    }
    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    @JoinTable(name = "tea_and_list",joinColumns = @JoinColumn(name = "tea_id"),inverseJoinColumns = @JoinColumn(name = "list_id"))
    private Collection<TeaLists> teaLists = new ArrayList<>();
    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "tea_and_review",joinColumns = @JoinColumn(name = "tea_id"),
            inverseJoinColumns = @JoinColumn(name = "review_id"))
    private Set<Reviews> reviews = new HashSet<>();
    @JoinColumn
    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "tea_and_image",joinColumns = @JoinColumn(name = "tea_id"),inverseJoinColumns = @JoinColumn(name = "image_id"))
    private Set<TeaImage> teaImages = new HashSet<>();
    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "tea_and_category", joinColumns = @JoinColumn(name = "tea_id"),inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories = new HashSet<>();
    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "user_and_tea",joinColumns = @JoinColumn(name = "tea_id"),inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users = new ArrayList<>();
    public void addUser(User user){
        this.users.add(user);
    }
    public void addList(TeaLists teaLists){
        this.teaLists.add(teaLists);
    }
    public void delList(TeaLists teaLists){
        this.teaLists.remove(teaLists);
    }
    public void addReview(Reviews reviews){
        this.reviews.add(reviews);
    }
    public void addCategory(Category category){
        this.categories.add(category);
        category.getTeas().add(this);
    }
    public void addTeaImage(TeaImage teaImage){
        this.teaImages.add(teaImage);
        teaImage.setTea(this);
    }

}
