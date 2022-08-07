package com.example.MYSTORE.PRODUCTS.Repository;

import com.example.MYSTORE.PRODUCTS.Model.Tea;
import com.example.MYSTORE.PRODUCTS.Model.TeaImage;

public interface CustomTeaImageRepository {
    void saveNewTeaImage(TeaImage teaImage);
    void updateTeaImageAndTea(TeaImage teaImage, Tea tea);
}
