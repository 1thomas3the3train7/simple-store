package com.example.MYSTORE.PRODUCTS.Repository;

import com.example.MYSTORE.PRODUCTS.Model.Reviews;
import com.example.MYSTORE.PRODUCTS.Model.Tea;
import com.example.MYSTORE.SECURITY.Model.User;

import java.util.List;

public interface CustomReviewRepository {
    List<Reviews> getReviewsByTeaId(Long id);
    void saveNewReview(Reviews reviews);
    void updateReviewAndTea(Reviews reviews, Tea tea);
    Reviews getReviewById(Long id);
    Long countReviewsByTea(Tea tea);
    void updateReviewAndUser(Reviews reviews, User user);
    void updateReviewAndUser(Long rid, Long uid);
    void uploadReview(Reviews reviews);
}
