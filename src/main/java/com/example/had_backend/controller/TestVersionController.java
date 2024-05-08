package com.example.had_backend.controller;

import com.example.had_backend.entity.Doctor;
import com.example.had_backend.entity.Test;
import com.example.had_backend.entity.TestVersion;
import com.example.had_backend.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestVersionController {
    @Autowired
    private ConsultationService consultationService;

    @Autowired
    private TestService testService;

    @Autowired
    private TestVersionService testVersionService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private ImageService imageService;

    @GetMapping("/testVersion/getFile")
    public ResponseEntity<byte[]> getFile(@RequestParam Long testVersionId, @RequestHeader(name = "Authorization") String token) {
        token =  token.substring(7);
        String username = jwtService.extractUsername(token);
        Doctor doctor = doctorService.getDoctorByName(username);
        TestVersion testVersion = testVersionService.getTestVersion(testVersionId);
        Test test = testVersion.getTest();

        if(!test.getPermittedDoctors().contains(doctor)) {
            return ResponseEntity.badRequest().body(null);
        }

        try {
            if(testVersion.getData() == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(testVersion.getData());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}
