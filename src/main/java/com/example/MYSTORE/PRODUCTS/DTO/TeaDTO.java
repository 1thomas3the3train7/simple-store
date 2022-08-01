package com.example.MYSTORE.PRODUCTS.DTO;

import com.example.MYSTORE.PRODUCTS.Model.Category;
import com.example.MYSTORE.PRODUCTS.Model.TeaImage;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TeaDTO {
    private Long id;
    private String name;
    private String madeCountry;
    private String about;
    private String fermentation;
    private Integer price;
    private Integer oldPrice;
    private String methodCook;
    private String mainLinkImage;
    private Integer presence;
    private String subname;
    private int count;
    private double grade;
    private List<ReviewsDTO> reviewsDTOList;
    private List<TeaImage> teaImages;
    private List<CategoryDTO> categoryDTOList;

    public TeaDTO() {
    }

    public TeaDTO(Long id,String name, String madeCountry, String about,
                  String fermentation, Integer price, String methodCook,
                  String mainLinkImage, Integer presence, double grade,Integer oldPrice) {
        this.id = id;
        this.name = name;
        this.madeCountry = madeCountry;
        this.about = about;
        this.fermentation = fermentation;
        this.price = price;
        this.methodCook = methodCook;
        this.mainLinkImage = mainLinkImage;
        this.presence = presence;
        this.grade = grade;
        this.oldPrice = oldPrice;
    }
}
