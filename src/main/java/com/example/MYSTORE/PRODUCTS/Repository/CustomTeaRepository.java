package com.example.MYSTORE.PRODUCTS.Repository;

import com.example.MYSTORE.PRODUCTS.Model.Tea;
import com.example.MYSTORE.SECURITY.Model.User;

import java.util.List;
import java.util.Set;

public interface CustomTeaRepository {
    Tea getLazyTeaById(Long id);

    Tea getEagerTeaCategoryReviewImage(Long id);
    void saveNewTea(Tea tea);
    Tea getTeaAndCategoryByTeaId(Long id);
    void TeaClearCategoryById(Long id);
    void TeaClearTeaImageById(Long id);
    void uploadTea(Tea tea);
    void updateTeaAndUser(Tea tea, User user);
    void updateTeaAndUser(Long teaId, Long userId);
    void deleteRelationTeaAndUser(Long teaId,Long userId);
    int findMaxPrice();
    int findMinPrice();
    List<Tea> findTeaByNameAndPriceAndCategoryName
            (String name,Set<String> categories,int minprice,int maxprice,int resPage);
    List<Tea> findTeaByNameAndPrice
            (String name,int minprice,int maxprice,int resPage);

}
