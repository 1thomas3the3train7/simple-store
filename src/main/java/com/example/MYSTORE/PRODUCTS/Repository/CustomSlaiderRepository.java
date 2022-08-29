package com.example.MYSTORE.PRODUCTS.Repository;

import com.example.MYSTORE.PRODUCTS.Model.SlaiderImages;
import com.example.MYSTORE.PRODUCTS.Model.Tea;
import com.example.MYSTORE.PRODUCTS.Model.TeaImage;

public interface CustomSlaiderRepository {
    void saveNewSlaider(SlaiderImages slaiderImages);
    void deleteSlaider(SlaiderImages slaiderImages);
    SlaiderImages getSlaiderAndTeaImageByName(String name);
    void updateSlaiderAndTeaImage(SlaiderImages slaiderImages, TeaImage teaImage);
}
