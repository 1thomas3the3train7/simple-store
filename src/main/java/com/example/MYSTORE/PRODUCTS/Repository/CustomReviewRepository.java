package com.example.MYSTORE.PRODUCTS.Repository;

import com.example.MYSTORE.PRODUCTS.Model.Reviews;

import java.util.Set;

public interface CustomReviewRepository {
    Set<Reviews> getReviewsByTeaId(Long id);
}
