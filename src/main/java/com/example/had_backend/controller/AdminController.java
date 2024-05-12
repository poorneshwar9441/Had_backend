package com.example.had_backend.controller;

import com.example.had_backend.entity.Doctor;
import com.example.had_backend.entity.UserInfo;
import com.example.had_backend.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {
    @Autowired
    private UserInfoService userInfoService;
    @PostMapping("/createAdmin")
    public ResponseEntity<?> createAdmin(@RequestBody UserInfo userInfo) {
        try {
            userInfo.setRoles("admin");
            UserInfo createdUserInfo = userInfoService.addUser(userInfo);
            return ResponseEntity.ok(createdUserInfo);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Username already exists!");
        }
    }
}
