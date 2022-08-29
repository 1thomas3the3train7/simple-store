package com.example.MYSTORE.PRODUCTS.Service;

import com.example.MYSTORE.PRODUCTS.DTO.ReviewsDTO;
import com.example.MYSTORE.PRODUCTS.Model.Category;
import com.example.MYSTORE.PRODUCTS.Model.Reviews;
import com.example.MYSTORE.PRODUCTS.Model.Tea;
import com.example.MYSTORE.PRODUCTS.POJO.RESULT;
import com.example.MYSTORE.PRODUCTS.POJO.ReviewsJson;
import com.example.MYSTORE.PRODUCTS.RepositoryImpl.CustomCategoryRepositoryImpl;
import com.example.MYSTORE.PRODUCTS.RepositoryImpl.CustomReviewRepositoryImpl;
import com.example.MYSTORE.PRODUCTS.RepositoryImpl.CustomTeaRepositoryImpl;
import com.example.MYSTORE.SECURITY.Model.User;
import com.example.MYSTORE.SECURITY.RepositoryImpl.CustomUserRepositoryImpl;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewService {
    private final CustomCategoryRepositoryImpl customCategoryRepository;
    private final CustomUserRepositoryImpl customUserRepository;
    private final CustomTeaRepositoryImpl customTeaRepository;
    private final CustomReviewRepositoryImpl customReviewRepository;
    private final ProductUtils productUtils;
    @Autowired
    public ReviewService(CustomCategoryRepositoryImpl customCategoryRepository, CustomUserRepositoryImpl customUserRepository,
                         CustomTeaRepositoryImpl customTeaRepository, CustomReviewRepositoryImpl customReviewRepository,
                         ProductUtils productUtils) {
        this.customCategoryRepository = customCategoryRepository;
        this.customUserRepository = customUserRepository;
        this.customTeaRepository = customTeaRepository;
        this.customReviewRepository = customReviewRepository;
        this.productUtils = productUtils;
    }

    private final Gson gson = new Gson();

    public ResponseEntity saveReview(String json, String email){
        ReviewsJson reviewsJson = gson.fromJson(json,ReviewsJson.class);
        User user = customUserRepository.getUserByEmail(email);
        Tea tea = customTeaRepository.getLazyTeaById(Long.parseLong(reviewsJson.getId()));
        if(user != null && tea != null){
            Reviews reviews = new Reviews(reviewsJson.getPluses(),reviewsJson.getMinuses(),
                    reviewsJson.getComment(), reviewsJson.getGrade());
            int grd = Integer.parseInt(customReviewRepository.countReviewsByTea(tea).toString());
            double grd1 = tea.getGrade();
            if(grd1 > 0){tea.setGrade(grd1 * (grd) / (grd + 1) + reviews.getGrade()/(grd + 1));}
            else {tea.setGrade(reviews.getGrade());};
            reviews.setUsername(user.getUsername());
            customTeaRepository.uploadTea(tea);
            customReviewRepository.saveNewReview(reviews);
            customReviewRepository.updateReviewAndTea(reviews,tea);
            customReviewRepository.updateReviewAndUser(reviews,user);
            return ResponseEntity.ok(new RESULT("saved"));
        }
        return ResponseEntity.ok(null);
    }
    public ResponseEntity getReview(String id){
        RESULT result = gson.fromJson(id,RESULT.class);
        List<Reviews> reviews = customReviewRepository.getReviewsByTeaId(Long.parseLong(result.getResult()));
        if(reviews == null){return ResponseEntity.ok(new RESULT("reviews not found"));}
        List<ReviewsDTO> reviewsDTOS = new ArrayList<>();
        reviews.forEach(r ->{
            reviewsDTOS.add(productUtils.Review_To_DTO(r));
        });
        return ResponseEntity.ok(reviewsDTOS);
    }
}
