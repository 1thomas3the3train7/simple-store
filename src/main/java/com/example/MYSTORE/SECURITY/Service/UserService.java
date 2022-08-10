package com.example.MYSTORE.SECURITY.Service;

import com.example.MYSTORE.PRODUCTS.DTO.RegisterDTO;
import com.example.MYSTORE.PRODUCTS.DTO.ResetPasswordDTO;
import com.example.MYSTORE.PRODUCTS.POJO.RESULT;
import com.example.MYSTORE.SECURITY.JWT.JWTRefreshToken;
import com.example.MYSTORE.SECURITY.Model.ResetPasswordToken;
import com.example.MYSTORE.SECURITY.Model.Role;
import com.example.MYSTORE.SECURITY.Model.User;
import com.example.MYSTORE.SECURITY.Model.VerificationToken;
import com.example.MYSTORE.SECURITY.Repository.*;
import com.example.MYSTORE.SECURITY.RepositoryImpl.CustomJWTRTokenRepositoryImpl;
import com.example.MYSTORE.SECURITY.RepositoryImpl.CustomRTokenRepositoryImpl;
import com.example.MYSTORE.SECURITY.RepositoryImpl.CustomUserRepositoryImpl;
import com.example.MYSTORE.SECURITY.RepositoryImpl.CustomVTokenRepositoryImpl;
import com.google.gson.Gson;
import org.hibernate.StaleStateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {
    @Value("${frontend.url}")
    private String myurl;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private CustomUserRepositoryImpl customUserRepository;
    @Autowired
    private CustomVTokenRepositoryImpl customVTokenRepository;
    @Autowired
    private CustomRTokenRepositoryImpl customRTokenRepository;
    @Autowired
    private CustomJWTRTokenRepositoryImpl customJWTRTokenRepository;

    @Transactional(rollbackFor = Exception.class)
    public boolean SaveUser(User user){
        User user1 = customUserRepository.getUserByEmail(user.getEmail());
        if(user1 == null){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            customUserRepository.saveNewUser(user);
            return true;
        } else {
            if(user1 != null && !user1.getBanned() && !user1.getMyEnabled()){
                VerificationToken verificationToken = customVTokenRepository.getVTokenByUser(user1);
                if(verificationToken != null){customVTokenRepository.deleteVToken(verificationToken);}
                ResetPasswordToken resetPasswordToken = customRTokenRepository.getRTokenByUser(user1);
                if(resetPasswordToken != null){customRTokenRepository.deleteRToken(resetPasswordToken);}
                customUserRepository.deleteUser(user1);
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                customUserRepository.saveNewUser(user);
                return true;
            } else {
                return false;
            }
        }
    }

    public void SaveVerificationToken(VerificationToken verificationToken,User user){
        final VerificationToken verificationToken1 = customVTokenRepository.getVTokenByUser(user);
        if(verificationToken1 != null){customVTokenRepository.deleteVToken(verificationToken1);}
        customVTokenRepository.saveNewVToken(verificationToken);
        customVTokenRepository.updateVTokenAndUser(verificationToken,user);
    }
    public void SaveResetPasswordToken(ResetPasswordToken resetPasswordToken,User user){
        final ResetPasswordToken resetPasswordToken1 = customRTokenRepository.getRTokenByUser(user);
        if(resetPasswordToken1 != null){customRTokenRepository.deleteRToken(resetPasswordToken1);}
        customRTokenRepository.saveNewRToken(resetPasswordToken);
        customRTokenRepository.updateRTokenAndUser(resetPasswordToken,user);
    }
    @Transactional
    public void LogoutUser(String refreshToken){
        final JWTRefreshToken jwtRefreshToken = customJWTRTokenRepository.getJWTRTokenByRefreshToken(refreshToken);
        if(jwtRefreshToken != null){
            User user = customUserRepository.getUserByJWTRefreshToken(jwtRefreshToken);
            if(user != null){user.setJwtRefreshToken(null);}
            customJWTRTokenRepository.deleteJWTRToken(jwtRefreshToken);
        }
    }
    public ResponseEntity registerUser(String jsonUser){
        try{
            Gson gson = new Gson();
            RegisterDTO registerDTO = gson.fromJson(jsonUser,RegisterDTO.class);
            User user = new User(registerDTO.getEmail(), registerDTO.getPassword());
            user.setUsername(registerDTO.getUsername());
            if(registerDTO.getRole() != null && registerDTO.getRole().equals("admin")){user.setRoles(List.of(new Role("ROLE_ADMIN")));}
            final String token = UUID.randomUUID().toString();
            final VerificationToken verificationToken = new VerificationToken();
            verificationToken.setToken(token);
            final boolean bol = SaveUser(user);
            if(!bol){return ResponseEntity.ok(new RESULT("user already exist"));}
            SaveVerificationToken(verificationToken,user);
            final String recipientAddress = user.getEmail();
            final String subject = "Registration Confirmation";
            final String confirmationUrl
                    = myurl + "/registration?token=" + token + "&" +"email=" + recipientAddress;
            sendEmail(recipientAddress,subject,confirmationUrl);
            return ResponseEntity.ok(new RESULT("saved"));
        } catch (StaleStateException staleStateException){
            return ResponseEntity.ok(staleStateException.toString());
        }
    }
    @Transactional
    public ResponseEntity confirmUser(String token,String email){
        User user = customUserRepository.getUserByEmail(email);
        if(user == null){return ResponseEntity.ok("not found user");}
        final VerificationToken verificationToken = customVTokenRepository.getVTokenByUser(user);
        if(verificationToken == null){return ResponseEntity.ok("token not found");}
        if(verificationToken.getToken().equals(token) && token != null
                && verificationToken.getToken() != null){
            user.setMyenabled(true);
            user.setVerificationToken(null);
            customVTokenRepository.deleteVToken(verificationToken);
            customUserRepository.updateUser(user);
            return ResponseEntity.ok("Email confirmed");
        }
        return ResponseEntity.ok("not found token");
    }
    @Transactional
    public ResponseEntity resetPassword(String res){
        Gson gson = new Gson();
        RESULT result = gson.fromJson(res,RESULT.class);
        User user = customUserRepository.getUserByEmail(result.getResult());
        if(user == null){return ResponseEntity.ok("user not found");}
        final String token = UUID.randomUUID().toString();
        ResetPasswordToken resetPasswordToken = new ResetPasswordToken();
        resetPasswordToken.setTokenreset(token);
        SaveResetPasswordToken(resetPasswordToken,user);
        final String recipientAddress = user.getEmail();
        final String subject = "Reset Password";
        final String confirmationUrl
                = myurl + "/resetpassword?token=" + token + "&" +"email=" + recipientAddress;
        sendEmail(recipientAddress,subject,confirmationUrl);
        return ResponseEntity.ok("Email send");
    }
    @Transactional
    public ResponseEntity ConfirmResetPassword(String res){
        Gson gson = new Gson();
        ResetPasswordDTO resetPasswordDTO = gson.fromJson(res,ResetPasswordDTO.class);
        User user = customUserRepository.getUserByEmail(resetPasswordDTO.getEmail());
        if(user == null){return ResponseEntity.ok("email not found");}
        ResetPasswordToken resetPasswordToken = customRTokenRepository.getRTokenByUser(user);
        if(resetPasswordDTO.getToken().equals(resetPasswordToken.getTokenreset())
                && resetPasswordToken.getTokenreset() != null){
            customRTokenRepository.deleteRToken(resetPasswordToken);
            user.setResetPasswordToken(null);
            user.setPassword(passwordEncoder.encode(resetPasswordDTO.getPassword()));
            customUserRepository.updateUser(user);
            return ResponseEntity.ok("password has been changed");
        } else{
            return ResponseEntity.ok("token not found");
        }
    }
    public void sendEmail(String recipientAddress,String subject,String confirmationUrl){
        SimpleMailMessage email = new SimpleMailMessage();
        email.setTo(recipientAddress);
        email.setSubject(subject);
        email.setText( "\r\n" + confirmationUrl);
        mailSender.send(email);
    }

}
