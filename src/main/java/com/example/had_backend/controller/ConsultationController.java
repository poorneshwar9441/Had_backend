package com.example.had_backend.controller;

import com.example.had_backend.entity.Consultation;
import com.example.had_backend.entity.Doctor;
import com.example.had_backend.entity.Patient;
import com.example.had_backend.service.ConsultationService;
import com.example.had_backend.service.DoctorService;
import com.example.had_backend.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class ConsultationController {
    @Autowired
    private ConsultationService consultationService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientService patientService;

    @PostMapping("/createConsultation")
    public ResponseEntity<Consultation> createConsultation(@RequestBody Map<String, Object> request) {
        System.out.println("012");

        Long doctorId = (Long) request.get("doctorId");
        Long patientId = (Long) request.get("patientId");
        String description = (String) request.get("description");

        System.out.println("abc");

        Doctor doctor = doctorService.getDoctor(doctorId);
        Patient patient = patientService.getPatient(patientId);

        System.out.println("xyz");

        Consultation createdConsultation = consultationService.createConsultation(doctor, patient, description);
        return ResponseEntity.ok(createdConsultation);
    }
}
