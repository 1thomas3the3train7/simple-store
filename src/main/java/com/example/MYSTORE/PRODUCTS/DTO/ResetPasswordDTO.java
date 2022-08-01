package com.example.MYSTORE.PRODUCTS.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordDTO {
    private String email;
    private String token;
    private String password;
}
