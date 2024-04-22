package com.example.had_backend.controller;

import com.example.had_backend.entity.Consultation;
import com.example.had_backend.entity.Doctor;
import com.example.had_backend.entity.Patient;
import com.example.had_backend.entity.UserInfo;
import com.example.had_backend.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Set;

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
    public ResponseEntity<Doctor> createDoctor(@RequestBody UserInfo userInfo) {
        UserInfo createdUserInfo = userInfoService.addUser(userInfo);
        Doctor createdDoctor = doctorService.createDoctor(createdUserInfo);
        return ResponseEntity.ok(createdDoctor);
    }

    @PostMapping("/createConsultation")
    public ResponseEntity<Consultation> createConsultation(@RequestBody Map<String, Object> request) {
//        Long doctorId = ((Integer) request.get("doctorId")).longValue();
        String token = (String) request.get("token");
        String patientName =  (String) request.get("patientName");
        String name = (String) request.get("name");
        String description = (String) request.get("description");

//        Doctor doctor = doctorService.getDoctor(doctorId);
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

    @GetMapping("/doctor/getPrimaryConsultations")
    public ResponseEntity<Set<Consultation>> getPrimaryConsultations(@RequestParam String token) {
//        Doctor doctor = doctorService.getDoctor(doctorId);

        Doctor doctor = doctorService.getDoctorByName(jwtService.extractUsername(token));
        return  ResponseEntity.ok(doctor.getPrimaryConsultations());
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