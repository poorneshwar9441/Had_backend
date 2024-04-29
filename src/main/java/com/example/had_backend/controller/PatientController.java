package com.example.had_backend.controller;

import com.example.had_backend.dto.ConsultationsDTO;
import com.example.had_backend.entity.Patient;
import com.example.had_backend.entity.UserInfo;
import com.example.had_backend.service.JwtService;
import com.example.had_backend.service.PatientService;
import com.example.had_backend.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Set;
import java.util.stream.Collectors;

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
    public ResponseEntity<?> createPatient(@RequestBody UserInfo userInfo) {
        try {
            userInfo.setRoles("patient");
            UserInfo createdUserInfo = userInfoService.addUser(userInfo);
            Patient createdPatient = patientService.createPatient(createdUserInfo);
            return ResponseEntity.ok(createdPatient);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Username already exists!");
        }
    }

    @PreAuthorize("hasAuthority('patient')")
    @GetMapping("/patient/getConsultations")
    public ResponseEntity<Set<ConsultationsDTO>> getConsultations(@RequestHeader (name="Authorization") String token) {
        token = token.substring(7);
        Patient patient = patientService.getPatientByName(jwtService.extractUsername(token));

        Set<ConsultationsDTO> consultations = patient.getConsultations().stream()
                .map(consultation -> {
                    ConsultationsDTO dto = new ConsultationsDTO();
                    dto.setId(consultation.getId());
                    dto.setName(consultation.getName());
                    dto.setDescription(consultation.getDescription());
                    dto.setPatientName(consultation.getPatient().getUser().getName());
                    dto.setDoctorName(consultation.getMainDoctor().getUser().getName());
                    dto.setDate(consultation.getDate());

                    return dto;
                })
                .collect(Collectors.toSet());

        return ResponseEntity.ok(consultations);
    }

//    @GetMapping("/patient/getConsultations")
//    public ResponseEntity<Set<Consultation>> getConsultations(@RequestParam String token) {
//        Patient patient = patientService.getPatientByName(jwtService.extractUsername(token));
//        return ResponseEntity.ok(patient.getConsultations());
//    }
}
