package com.example.MYSTORE.SECURITY.Repository;

import com.example.MYSTORE.SECURITY.Model.User;
import com.example.MYSTORE.SECURITY.Model.VerificationToken;

public interface CustomVerificationTokenRepository {
    void saveNewVToken(VerificationToken verificationToken);
    VerificationToken getVTokenByUser(User user);
    void deleteVToken(VerificationToken verificationToken);
    void updateVTokenAndUser(VerificationToken verificationToken,User user);
}
