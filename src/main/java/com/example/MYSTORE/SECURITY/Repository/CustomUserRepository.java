package com.example.MYSTORE.SECURITY.Repository;

import com.example.MYSTORE.SECURITY.JWT.JWTRefreshToken;
import com.example.MYSTORE.SECURITY.Model.User;

public interface CustomUserRepository {
    User getUserByEmail(String email);
    void saveNewUser(User user);
    void deleteUser(User user);
    User getUserAndVTokenByEmail(String email);
    User getUserByJWTRefreshToken(JWTRefreshToken jwtRefreshToken);
    void updateUser(User user);
    User getUserByTeaIdAndEmail(Long id,String email);
    User getUserAndRoleByEmail(String email);
}
