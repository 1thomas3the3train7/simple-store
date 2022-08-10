package com.example.MYSTORE.SECURITY.Repository;

import com.example.MYSTORE.SECURITY.JWT.JWTRefreshToken;
import com.example.MYSTORE.SECURITY.Model.User;

public interface CustomJWTRefreshTokenRepository {
    void saveNewJWTRToken(JWTRefreshToken jwtRefreshToken);
    void deleteJWTRToken(JWTRefreshToken jwtRefreshToken);
    void updateJWTRTokenAndUser(JWTRefreshToken jwtRefreshToken, User user);
    JWTRefreshToken getJWTRTokenByRefreshToken(String token);
}
