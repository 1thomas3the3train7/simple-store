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
}