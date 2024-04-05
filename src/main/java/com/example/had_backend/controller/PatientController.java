package com.example.had_backend.controller;

import com.example.had_backend.entity.Patient;
import com.example.had_backend.entity.UserInfo;
import com.example.had_backend.service.PatientService;
import com.example.had_backend.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class PatientController {
    @Autowired
    private PatientService patientService;

    @Autowired
    private UserInfoService userInfoService;

    @PostMapping("createPatient")
    public ResponseEntity<Patient> createPatient(@RequestBody UserInfo userInfo) {
        userInfoService.addUser(userInfo);
        Patient createdPatient = patientService.createPatient(userInfo);
        return ResponseEntity.ok(createdPatient);
    }
}
