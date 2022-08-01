package com.example.MYSTORE.SECURITY.JWT;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JWTRequest {
    private String email;
    private String password;
}
