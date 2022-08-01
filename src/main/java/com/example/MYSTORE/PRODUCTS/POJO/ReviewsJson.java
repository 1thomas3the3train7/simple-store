package com.example.MYSTORE.PRODUCTS.POJO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReviewsJson {
    private String id;
    private Integer grade;
    private String pluses;
    private String minuses;
    private String comment;

    public ReviewsJson(Integer grade, String pluses, String minuses, String comment) {
        this.grade = grade;
        this.pluses = pluses;
        this.minuses = minuses;
        this.comment = comment;
    }
}
