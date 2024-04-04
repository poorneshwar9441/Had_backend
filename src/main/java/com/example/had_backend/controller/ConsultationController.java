package com.example.had_backend.controller;

import com.example.had_backend.service.ConsultationService;
import com.example.had_backend.service.DoctorService;
import com.example.had_backend.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@CrossOrigin(origins = "*")
public class ConsultationController {
    @Autowired
    private ConsultationService consultationService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientService patientService;

    @GetMapping("/createConsultation")
    public ResponseEntity<String> Jon(){//@RequestBody Long doctorId, @RequestBody Long patientId, @RequestBody String description) {
        System.out.println("hello wrold");
//        Doctor doctor = doctorService.getDoctor(doctorId);
//        Patient patient = patientService.getPatient(patientId);
//        consultationService.createConsultation(doctor, patient, description);

        return ResponseEntity.status(HttpStatus.OK).body("bom");

    }
}
