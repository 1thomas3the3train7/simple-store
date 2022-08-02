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
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JWTRefreshTokenRepository jwtRefreshTokenRepository;
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private VerificationTokenRepository verificationTokenRepository;
    @Autowired
    private ResetPasswordTokenRepository resetPasswordTokenRepository;

    @Transactional
    public boolean SaveUser(User user){
        User user1 = userRepository.findByEmail(user.getEmail());
        if(user1 == null){
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return true;
        } else {
            if(user1 != null && !user1.getBanned() && !user1.getMyEnabled()){
                VerificationToken verificationToken = verificationTokenRepository.findByUser(user1);
                if(verificationToken != null){verificationTokenRepository.delete(verificationToken);}
                ResetPasswordToken resetPasswordToken = resetPasswordTokenRepository.findByUser(user1);
                if(resetPasswordToken != null){resetPasswordTokenRepository.delete(resetPasswordToken);}
                userRepository.delete(user1);
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                userRepository.save(user);
                return true;
            } else {
                return false;
            }
        }
    }

    public void SaveVerificationToken(VerificationToken verificationToken,User user){
        final VerificationToken verificationToken1 = verificationTokenRepository.findByUser(user);
        if(verificationToken1 != null){verificationTokenRepository.delete(verificationToken1);}
        user.setVerificationToken(verificationToken);
        verificationTokenRepository.save(verificationToken);
    }
    public void SaveResetPasswordToken(ResetPasswordToken resetPasswordToken,User user){
        final ResetPasswordToken resetPasswordToken1 = resetPasswordTokenRepository.findByUser(user);
        if(resetPasswordToken1 != null){resetPasswordTokenRepository.delete(resetPasswordToken1);}
        user.setResetPasswordToken(resetPasswordToken);
        resetPasswordTokenRepository.save(resetPasswordToken);
    }
    @Transactional
    public void LogoutUser(String refreshToken){
        final JWTRefreshToken jwtRefreshToken = jwtRefreshTokenRepository.findByRefreshToken(refreshToken);
        if(jwtRefreshToken != null){
            User user = userRepository.findByJwtRefreshToken(jwtRefreshToken);
            if(user != null){user.setJwtRefreshToken(null);}
            jwtRefreshTokenRepository.delete(jwtRefreshToken);
            userRepository.save(user);
        }
    }
    public ResponseEntity registerUser(String jsonUser){
        try{
            Gson gson = new Gson();
            RegisterDTO registerDTO = gson.fromJson(jsonUser,RegisterDTO.class);
            User user = new User(registerDTO.getEmail(), registerDTO.getPassword());
            user.setUsername(registerDTO.getUsername());
            if(registerDTO.getRole() == "admin"){user.setRoles(List.of(new Role("ROLE_ADMIN")));}
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
            SimpleMailMessage email = new SimpleMailMessage();
            email.setTo(recipientAddress);
            email.setSubject(subject);
            email.setText( "\r\n" + confirmationUrl);
            mailSender.send(email);
            return ResponseEntity.ok(new RESULT("saved"));
        } catch (StaleStateException staleStateException){
            return ResponseEntity.ok(staleStateException.toString());
        }
    }
    @Transactional
    public ResponseEntity confirmUser(String token,String email){
        User user = userRepository.findByEmail(email);
        if(user == null){return ResponseEntity.ok("not found user");}
        final VerificationToken verificationToken = verificationTokenRepository.findByUser(user);
        if(verificationToken == null){return ResponseEntity.ok("token not found");}
        if(verificationToken.getToken().equals(token) && token != null
                && verificationToken.getToken() != null){
            user.setMyenabled(true);
            user.setVerificationToken(null);
            verificationTokenRepository.delete(verificationToken);
            userRepository.save(user);
            return ResponseEntity.ok("Email confirmed");
        }
        return ResponseEntity.ok("not found token");
    }
    @Transactional
    public ResponseEntity resetPassword(String res){
        Gson gson = new Gson();
        RESULT result = gson.fromJson(res,RESULT.class);
        User user = userRepository.findByEmail(result.getResult());
        if(user == null){return ResponseEntity.ok("user not found");}
        final String token = UUID.randomUUID().toString();
        ResetPasswordToken resetPasswordToken = new ResetPasswordToken();
        resetPasswordToken.setTokenreset(token);
        SaveResetPasswordToken(resetPasswordToken,user);
        final String recipientAddress = user.getEmail();
        final String subject = "Reset Password";
        final String confirmationUrl
                = myurl + "/resetpassword?token=" + token + "&" +"email=" + recipientAddress;
        SimpleMailMessage Email = new SimpleMailMessage();
        Email.setTo(recipientAddress);
        Email.setSubject(subject);
        Email.setText( "\r\n" + confirmationUrl);
        mailSender.send(Email);
        return ResponseEntity.ok("Email send");
    }
    @Transactional
    public ResponseEntity ConfirmResetPassword(String res){
        Gson gson = new Gson();
        ResetPasswordDTO resetPasswordDTO = gson.fromJson(res,ResetPasswordDTO.class);
        User user = userRepository.findByEmail(resetPasswordDTO.getEmail());
        if(user == null){return ResponseEntity.ok("email not found");}
        ResetPasswordToken resetPasswordToken = resetPasswordTokenRepository.findByUser(user);
        if(resetPasswordDTO.getToken().equals(resetPasswordToken.getTokenreset())
                && resetPasswordToken.getTokenreset() != null){
            resetPasswordTokenRepository.delete(resetPasswordToken);
            user.setResetPasswordToken(null);
            user.setPassword(passwordEncoder.encode(resetPasswordDTO.getPassword()));
            userRepository.save(user);
            return ResponseEntity.ok("password has been changed");
        } else{
            return ResponseEntity.ok("token not found");
        }
    }

}
