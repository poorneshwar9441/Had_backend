package com.example.had_backend.controller;

import com.example.had_backend.entity.Consultation;
import com.example.had_backend.entity.Patient;
import com.example.had_backend.entity.UserInfo;
import com.example.had_backend.service.JwtService;
import com.example.had_backend.service.PatientService;
import com.example.had_backend.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class PatientController {
    @Autowired
    private PatientService patientService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private JwtService jwtService;

    @PostMapping("/createPatient")
    public ResponseEntity<Patient> createPatient(@RequestBody UserInfo userInfo) {
        UserInfo createdUserInfo = userInfoService.addUser(userInfo);
        Patient createdPatient = patientService.createPatient(createdUserInfo);
        return ResponseEntity.ok(createdPatient);
    }

    @GetMapping("/patient/getConsultations")
    public ResponseEntity<Set<Consultation>> getConsultations(@RequestParam String token) {
        Patient patient = patientService.getPatientByName(jwtService.extractUsername(token));
        return ResponseEntity.ok(patient.getConsultations());
    }
}
