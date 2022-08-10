package com.example.MYSTORE.PRODUCTS.Repository;

import com.example.MYSTORE.PRODUCTS.Model.Reviews;
import com.example.MYSTORE.PRODUCTS.Model.Tea;

import java.util.List;

public interface CustomReviewRepository {
    List<Reviews> getReviewsByTeaId(Long id);
    void saveNewReview(Reviews reviews);
    void updateReviewAndTea(Reviews reviews, Tea tea);
}
