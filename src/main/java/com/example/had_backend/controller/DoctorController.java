package com.example.had_backend.controller;

import com.example.had_backend.entity.Doctor;
import com.example.had_backend.entity.UserInfo;
import com.example.had_backend.service.DoctorService;
import com.example.had_backend.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class DoctorController {
    @Autowired
    private DoctorService doctorService;

    @Autowired
    private UserInfoService userInfoService;

    @PostMapping("/createDoctor")
    public ResponseEntity<Doctor> createDoctor(@RequestBody UserInfo userInfo) {
        userInfoService.addUser(userInfo);
        Doctor createdDoctor = doctorService.createDoctor(userInfo);
        return ResponseEntity.ok(createdDoctor);
    }
}