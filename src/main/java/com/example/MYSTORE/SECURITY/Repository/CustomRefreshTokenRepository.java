package com.example.MYSTORE.SECURITY.Repository;

import com.example.MYSTORE.SECURITY.Model.ResetPasswordToken;
import com.example.MYSTORE.SECURITY.Model.User;

public interface CustomRefreshTokenRepository {
    void saveNewRToken(ResetPasswordToken resetPasswordToken);
    void deleteRToken(ResetPasswordToken resetPasswordToken);
    ResetPasswordToken getRTokenByUser(User user);
    void updateRTokenAndUser(ResetPasswordToken resetPasswordToken,User user);
}
