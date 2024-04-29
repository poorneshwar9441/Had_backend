package com.example.had_backend.controller;

import com.example.had_backend.entity.Doctor;
import com.example.had_backend.entity.UserInfo;
import com.example.had_backend.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class RadiographerController {
    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private ConsultationService consultationService;

    @PostMapping("/createRadiographer")
    public ResponseEntity<?> createRadiographer(@RequestBody UserInfo userInfo) {
        try {
            userInfo.setRoles("radiographer");
            UserInfo createdUserInfo = userInfoService.addUser(userInfo);
            Doctor createdRadiographer = doctorService.createDoctor(createdUserInfo);
            return ResponseEntity.ok(createdRadiographer);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Username already exists!");
        }
    }
}
