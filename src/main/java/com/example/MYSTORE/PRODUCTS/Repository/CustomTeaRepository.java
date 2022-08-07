package com.example.MYSTORE.PRODUCTS.Repository;

import com.example.MYSTORE.PRODUCTS.Model.Tea;

public interface CustomTeaRepository {
    Tea getLazyTeaById(Long id);

    Tea getEagerTeaById(Long id);
    void saveNewTea(Tea tea);
    Tea getTeaAndCategoryByTeaId(Long id);
    void TeaClearCategoryById(Long id);
    void TeaClearTeaImageById(Long id);
    void uploadTea(Tea tea);

}
