package com.example.MYSTORE.PRODUCTS.Repository;

import com.example.MYSTORE.PRODUCTS.Model.SlaiderImages;
import com.example.MYSTORE.PRODUCTS.Model.Tea;
import com.example.MYSTORE.PRODUCTS.Model.TeaImage;

public interface CustomTeaImageRepository {
    void saveNewTeaImage(TeaImage teaImage);
    void updateTeaImageAndTea(TeaImage teaImage, Tea tea);
    TeaImage getTeaImageByName(String name);
    void deleteTeaImageBySlaider(SlaiderImages slaiderImages);
}
