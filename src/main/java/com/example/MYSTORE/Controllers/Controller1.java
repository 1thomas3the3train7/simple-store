package com.example.MYSTORE.Controllers;



import org.springframework.web.bind.annotation.*;



@RestController
public class Controller1 {
    @GetMapping(value = "/start")
    public String start() {
    return "HELLO WORLD";
    }
}