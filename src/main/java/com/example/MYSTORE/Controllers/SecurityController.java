package com.example.MYSTORE.Controllers;

import com.example.MYSTORE.SECURITY.Repository.UserRepository;
import com.example.MYSTORE.SECURITY.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class SecurityController {
    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/api/auth/logout")
    public ResponseEntity Logout(@RequestBody String email, HttpServletRequest request,
                                 HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (c.getName().equals("refreshToken")) {
                    userService.LogoutUser(c.getValue());
                }
                c.setValue("");
                c.setPath("/");
                c.setMaxAge(0);
                response.addCookie(c);
            }
        }
        SecurityContext context = SecurityContextHolder.getContext();
        context.setAuthentication(null);
        SecurityContextHolder.clearContext();
        return ResponseEntity.ok(null);
    }

    @PostMapping("/api/auth/register")
    public ResponseEntity Register(@RequestBody String reg) {
        return userService.registerUser(reg);
    }

    @GetMapping("/api/auth/registration")
    public ResponseEntity Registration(@RequestParam(name = "token", required = false) String token,
                                       @RequestParam(name = "email", required = false) String email) {
        System.out.println(token);
        System.out.println(email);
        return userService.confirmUser(token, email);
    }

    @PostMapping("/api/auth/reset")
    public ResponseEntity Reset(@RequestBody String res){
        return userService.resetPassword(res);
    }

    @PostMapping("/api/auth/confirmreset")
    public ResponseEntity ConfirmReset(@RequestBody String res){
        return userService.ConfirmResetPassword(res);
    }
}
