package com.example.MYSTORE.SECURITY.JWT;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class JWTResponse{
    @JsonIgnore
    private String Bearer = "Bearer";
    private String AccessToken;



    public JWTResponse(String accessToken){
        this.AccessToken = accessToken;
    }
}
