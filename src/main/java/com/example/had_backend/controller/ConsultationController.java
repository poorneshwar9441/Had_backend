package com.example.had_backend.controller;

import com.example.had_backend.entity.Consultation;
import com.example.had_backend.entity.Doctor;
import com.example.had_backend.entity.Patient;
import com.example.had_backend.service.ConsultationService;
import com.example.had_backend.service.DoctorService;
import com.example.had_backend.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.lang.Long;

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
    @PreAuthorize("hasAuthority('Doctor')")
    public ResponseEntity<Consultation> createConsultation(@RequestBody Map<String, Object> request) {
        Long doctorId = ((Integer) request.get("doctorId")).longValue();
        Long patientId =  ((Integer) request.get("patientId")).longValue();
        String description = (String) request.get("description");

        Doctor doctor = doctorService.getDoctor(doctorId);
        Patient patient = patientService.getPatient(patientId);

        Consultation createdConsultation = consultationService.createConsultation(doctor, patient, description);
        return ResponseEntity.ok(createdConsultation);
    }
}
