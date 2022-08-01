package com.example.MYSTORE.SECURITY.Repository;

import com.example.MYSTORE.SECURITY.JWT.JWTRefreshToken;
import com.example.MYSTORE.SECURITY.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JWTRefreshTokenRepository extends JpaRepository<JWTRefreshToken,Long> {
    JWTRefreshToken findByUser(User user);
    void deleteByUser(User user);
    JWTRefreshToken findByRefreshToken(String refreshToken);
}
