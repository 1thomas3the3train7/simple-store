package com.example.MYSTORE.PRODUCTS.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDTO {
    private String name;

    public CategoryDTO() {
    }

    public CategoryDTO(String name) {
        this.name = name;
    }
}
