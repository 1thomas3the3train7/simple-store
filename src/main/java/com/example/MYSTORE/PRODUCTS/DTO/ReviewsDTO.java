package com.example.MYSTORE.PRODUCTS.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewsDTO {
    private String pluses;
    private String minuses;
    private String comment;
    private double grade;

    private String username;

    public ReviewsDTO() {
    }

    public ReviewsDTO(String pluses, String minuses, String comment, double grade,String username) {
        this.pluses = pluses;
        this.minuses = minuses;
        this.comment = comment;
        this.grade = grade;
        this.username = username;
    }

}
