package com.example.had_backend.controller;

import com.example.had_backend.entity.Consultation;
import com.example.had_backend.entity.Doctor;
import com.example.had_backend.entity.Test;
import com.example.had_backend.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Autowired
    private ConsultationService consultationService;

    @Autowired
    private TestService testService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserInfoService userInfoService;

    // Endpoint that returns "Hello, world!"
//    @GetMapping("/hello")
//    public String helloWorld() {
//        return "Hello, world!";
//    }

    @PreAuthorize("hasAuthority('doctor')")
    @PostMapping("/test/permitDoctor")
    public ResponseEntity<Object> permitDoctor(@RequestParam Long consultationId, @RequestParam Long testId, @RequestParam String permittedDoctorName, @RequestHeader(name = "Authorization") String token) {
        token =  token.substring(7);
        String username = jwtService.extractUsername(token);
        Consultation consultation = consultationService.getConsultation(consultationId);
        Doctor doctor = doctorService.getDoctorByName(username);
        Doctor permittedDoctor = doctorService.getDoctorByName(permittedDoctorName);
        Test test = testService.getTest(testId);

        if(!consultation.getMainDoctor().getUser().getName().equals(username)
        || !consultation.getTests().contains(test)
        || !test.getPermittedDoctors().contains(doctor)
        || test.getPermittedDoctors().contains(permittedDoctor)
        || !consultation.getSecondaryDoctors().contains(permittedDoctor)) {

            return ResponseEntity.badRequest().body(null);
        }

        testService.addPermittedDoctors(permittedDoctor, test);
        doctorService.addVisibleTests(permittedDoctor, test);

        return ResponseEntity.ok().body(null);
    }
}
