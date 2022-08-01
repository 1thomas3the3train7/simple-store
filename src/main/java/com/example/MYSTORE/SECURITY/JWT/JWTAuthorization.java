package com.example.MYSTORE.SECURITY.JWT;

import com.example.MYSTORE.SECURITY.Model.User;
import com.example.MYSTORE.SECURITY.Repository.JWTRefreshTokenRepository;
import com.example.MYSTORE.SECURITY.Repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JWTAuthorization {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private JWTRefreshTokenRepository jwtRefreshTokenRepository;
    @Autowired
    private JWTProvider jwtProvider;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Transactional
    public JWTResponse login(@NonNull JWTRequest authRequest, HttpServletResponse response) {
        System.out.println(authRequest.getPassword());
        System.out.println(authRequest.getEmail());
        final User user = userRepository.findByEmail(authRequest.getEmail());
        if (user != null){
            if (passwordEncoder.matches(authRequest.getPassword(), user.getPassword())) {
                JWTRefreshToken jwtRefreshToken1 = user.getJwtRefreshToken();
                if(jwtRefreshToken1 != null){jwtRefreshTokenRepository.deleteByUser(user);}
                final String accessToken = jwtProvider.generateAccessToken(user);
                final String refreshToken = jwtProvider.generateRefreshToken(user);
                final JWTRefreshToken jwtRefreshToken = new JWTRefreshToken(jwtProvider.generateRefreshToken(user));
                user.setJwtRefreshToken(jwtRefreshToken);
                jwtRefreshTokenRepository.save(jwtRefreshToken);
                userRepository.save(user);
                final Cookie cookie = new Cookie("refreshToken",refreshToken);
                cookie.setHttpOnly(true);
                cookie.setPath("/");
                cookie.setSecure(false);
                response.addCookie(cookie);
                return new JWTResponse(accessToken);
            }
        }
        return new JWTResponse(null);
    }

    public JWTResponse getAccessToken(HttpServletRequest request) {
        try {
            final String refreshToken = Arrays.stream
                    (request.getCookies()).map(cookie -> cookie.getValue()).collect(Collectors.joining());
            System.out.println(refreshToken);
            if(refreshToken != null){
                if (jwtProvider.validateRefreshToken(refreshToken)) {
                    final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
                    final String login = claims.getSubject();
                    final User user = userRepository.findByEmail(login);
                    if(user != null) {
                        final JWTRefreshToken jwtRefreshToken = jwtRefreshTokenRepository.findByUser(user);
                        if (jwtRefreshToken != null && jwtRefreshToken.getRefreshToken().equals(refreshToken)) {
                            final String accessToken = jwtProvider.generateAccessToken(user);
                            return new JWTResponse(accessToken);
                        }
                    }
                }
            }
        } catch (NullPointerException n){
            System.out.println(n);
            return new JWTResponse(null);
        }
        return new JWTResponse(null);
    }

    public JWTResponse refresh(String refreshToken) {
        if(refreshToken != null){
            if (jwtProvider.validateRefreshToken(refreshToken)) {
                final Claims claims = jwtProvider.getRefreshClaims(refreshToken);
                final String login = claims.getSubject();
                final User user = userRepository.findByEmail(login);
                final JWTRefreshToken jwtRefreshToken = jwtRefreshTokenRepository.findByUser(user);
                final String jwtRefresh = jwtRefreshToken.getRefreshToken();
                if (jwtRefresh != null && jwtRefresh.equals(refreshToken)){
                    final String accessToken = jwtProvider.generateAccessToken(user);
                    final JWTRefreshToken jwtRefreshToken1 = new JWTRefreshToken(jwtProvider.generateRefreshToken(user));
                    jwtRefreshTokenRepository.delete(jwtRefreshToken);
                    user.setJwtRefreshToken(jwtRefreshToken1);
                    jwtRefreshTokenRepository.save(jwtRefreshToken1);
                    return new JWTResponse(accessToken);
                }
            }
        }
        return new JWTResponse(null);
    }

    public JWTAuthentication getAuthInfo() {
        return (JWTAuthentication) SecurityContextHolder.getContext().getAuthentication();
    }
}
