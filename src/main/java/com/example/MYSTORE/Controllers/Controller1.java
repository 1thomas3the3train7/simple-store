package com.example.MYSTORE.Controllers;


import com.example.MYSTORE.PRODUCTS.Service.ProductService;
import com.example.MYSTORE.SECURITY.JWT.JWTAuthorization;
import com.example.MYSTORE.SECURITY.JWT.JWTRefreshRequest;
import com.example.MYSTORE.SECURITY.JWT.JWTRequest;
import com.example.MYSTORE.SECURITY.JWT.JWTResponse;
import com.example.MYSTORE.SECURITY.Model.User;
import com.example.MYSTORE.SECURITY.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.Arrays;
import java.util.stream.Collectors;

@RestController
public class Controller1 {
    @GetMapping(value = "/start")
    public String start() {
    return "HELLO WORLD";
    }
    @GetMapping("/q")
    public String q(Principal principal){


        return "successq";
    }
    @Autowired
    private JWTAuthorization authService;
    @Autowired
    private ServletContext servletContext;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductService productService;

    @PostMapping("/api/auth/login")
    public ResponseEntity<JWTResponse> login(@RequestBody JWTRequest authRequest, HttpServletResponse response) {
        final JWTResponse token = authService.login(authRequest, response);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/api/auth/token")
    public ResponseEntity<JWTResponse> getNewAccessToken(@RequestBody JWTRefreshRequest request,
                                                         HttpServletRequest request1) {
        final JWTResponse token = authService.getAccessToken(request1);
        return ResponseEntity.ok(token);
    }

    @PostMapping("/api/auth/refresh")
    public ResponseEntity<JWTResponse> getNewRefreshToken(@RequestBody JWTRefreshRequest request) {
        final JWTResponse token = authService.refresh(request.getRefreshToken());
        return ResponseEntity.ok(token);
    }
    @PostMapping("/test")
    public ResponseEntity<User> getuser(){
        final User user = userRepository.findByEmail("email");
        return ResponseEntity.ok(user);
    }
    @PostMapping("/test1")
    public JsonResponse test1(HttpServletRequest request){
        return new JsonResponse(Arrays.stream(request.getCookies()).map(cookie -> cookie.getValue()).collect(Collectors.joining()));
    }
    @PostMapping("/test3")
    public ResponseEntity<Path> test3() throws IOException{
        Path path = Paths.get("C:\\Users\\makssi\\IdeaProjects\\MYSTORE\\src\\main\\resources\\static\\images\\vladd.jpg");
        return ResponseEntity.ok(path);
    }
    @PostMapping(value = "/test4", produces = MediaType.IMAGE_JPEG_VALUE)
    public ResponseEntity image() throws IOException{
        final ByteArrayResource inputStream = new ByteArrayResource(Files.readAllBytes(Paths.get(
                "C:\\Users\\maksi\\IdeaProjects\\MYSTORE\\src\\main\\resources\\static\\images\\vladd.jpg"
        )));
        return ResponseEntity.status(HttpStatus.OK)
                .body(inputStream);
    }

}