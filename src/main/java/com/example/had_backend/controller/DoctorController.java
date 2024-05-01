package com.example.had_backend.controller;

import com.example.had_backend.dto.ConsultationsDTO;
import com.example.had_backend.entity.Consultation;
import com.example.had_backend.entity.Doctor;
import com.example.had_backend.entity.Patient;
import com.example.had_backend.entity.UserInfo;
import com.example.had_backend.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class DoctorController {
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

    @PostMapping("/createDoctor")
    public ResponseEntity<?> createDoctor(@RequestBody UserInfo userInfo) {
        try {
            userInfo.setRoles("doctor");
            UserInfo createdUserInfo = userInfoService.addUser(userInfo);
            Doctor createdDoctor = doctorService.createDoctor(createdUserInfo);
            return ResponseEntity.ok(createdDoctor);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body("Username already exists!");
        }
    }

    @PreAuthorize("hasAuthority('doctor')")
    @PostMapping("/createConsultation")
    public ResponseEntity<Consultation> createConsultation(@RequestBody Map<String, Object> request, @RequestHeader(name="Authorization") String token) {
        token = token.substring(7);
        String patientName =  (String) request.get("patientName");
        String name = (String) request.get("name");
        String description = (String) request.get("description");

        Doctor doctor = doctorService.getDoctorByName(jwtService.extractUsername(token));
        Patient patient = patientService.getPatientByName(patientName);

        if(doctor == null || patient == null){
            return ResponseEntity.badRequest().body(null);
        }

        Consultation createdConsultation = consultationService.createConsultation(doctor, patient, name, description);

        doctorService.addPrimaryConsultation(doctor, createdConsultation);
        patientService.addConsultation(patient, createdConsultation);

        return ResponseEntity.ok(createdConsultation);
    }

    @PreAuthorize("hasAuthority('doctor')")
    @GetMapping("/doctor/getPrimaryConsultations")
    public ResponseEntity<Set<ConsultationsDTO>> getPrimaryConsultations(@RequestHeader (name="Authorization") String token) {
        token = token.substring(7);
        Doctor doctor = doctorService.getDoctorByName(jwtService.extractUsername(token));

        Set<ConsultationsDTO> primaryConsultations = doctor.getPrimaryConsultations().stream()
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

        return ResponseEntity.ok(primaryConsultations);
    }

    @PreAuthorize("hasAuthority('doctor')")
    @GetMapping("/doctor/getSecondaryConsultations")
    public ResponseEntity<Set<ConsultationsDTO>> getSecondaryConsultations(@RequestHeader (name="Authorization") String token) {
        token = token.substring(7);
        Doctor doctor = doctorService.getDoctorByName(jwtService.extractUsername(token));

        Set<ConsultationsDTO> secondaryConsultations = doctor.getSecondaryConsultations().stream()
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

        return ResponseEntity.ok(secondaryConsultations);
    }

//    @GetMapping("/doctor/getPrimaryConsultations")
//    public ResponseEntity<Set<Consultation>> getPrimaryConsultations(@RequestBody Map<String, Object> request) {
//        Long doctorId = ((Integer) request.get("doctorId")).longValue();
//
//        Doctor doctor = doctorService.getDoctor(doctorId);
//
//        return  ResponseEntity.ok(doctor.getPrimaryConsultations());
//    }

//    @GetMapping("/getNewSize")
//    public String getNewSize(@RequestBody Map<String, Object> request) {
//        Long doctorId = ((Integer) request.get("doctorId")).longValue();
//        Doctor doctor = doctorService.getDoctor(doctorId);
//
//        System.out.println(doctor.getUser().getName());
//        System.out.println("size of new set after transaction: " + doctor.getPrimaryConsultations().size());
//
//        return "Hello, world!";
//    }
}