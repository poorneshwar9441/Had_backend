package com.example.had_backend.controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    // Endpoint that returns "Hello, world!"
    @GetMapping("/hello")
    public String helloWorld() {
        return "Hello, world!";
    }
}
