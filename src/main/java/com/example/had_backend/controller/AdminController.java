package com.example.had_backend.controller;

import com.example.had_backend.entity.Admin;
import com.example.had_backend.entity.Doctor;
import com.example.had_backend.entity.UserInfo;
import com.example.had_backend.service.AdminService;
import com.example.had_backend.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public class AdminController {
    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private AdminService adminService;


    @PostMapping("/createAdmin")
    public ResponseEntity<?> createAdmin(@RequestBody UserInfo userInfo) {
        System.out.println("logs");
        try {

            userInfo.setRoles("Admin");
            UserInfo createdUserInfo = userInfoService.addUser(userInfo);
            Admin a = adminService.createAdmin(createdUserInfo);
            return ResponseEntity.ok(a);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Username already exists!");
        }
    }
}
