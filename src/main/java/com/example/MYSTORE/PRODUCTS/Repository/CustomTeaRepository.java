package com.example.MYSTORE.PRODUCTS.Repository;

import com.example.MYSTORE.PRODUCTS.Model.Tea;
import com.example.MYSTORE.SECURITY.Model.User;

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

}
