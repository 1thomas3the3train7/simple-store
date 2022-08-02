package com.example.MYSTORE.SECURITY.Repository;

import com.example.MYSTORE.SECURITY.Model.ResetPasswordToken;
import com.example.MYSTORE.SECURITY.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ResetPasswordTokenRepository extends JpaRepository<ResetPasswordToken,Long> {
    ResetPasswordToken findByUser(User user);
}
