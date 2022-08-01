package com.example.MYSTORE.PRODUCTS.POJO;

import com.example.MYSTORE.Controllers.JsonResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RESULT {
    private String result;

    public RESULT(String result) {
        this.result = result;
    }
}
