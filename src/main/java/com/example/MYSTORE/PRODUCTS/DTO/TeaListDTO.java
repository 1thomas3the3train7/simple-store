package com.example.MYSTORE.PRODUCTS.DTO;

import com.example.MYSTORE.PRODUCTS.Model.Tea;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
public class TeaListDTO {
    private List<Tea> teas;

    public TeaListDTO() {
    }

}
