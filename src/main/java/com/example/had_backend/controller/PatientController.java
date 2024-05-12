package com.example.had_backend.controller;

import com.example.had_backend.dto.ConsentRequestDTO;
import com.example.had_backend.dto.ConsultationsDTO;
import com.example.had_backend.entity.*;
import com.example.had_backend.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    @Autowired
    private ConsultationService consultationService;
    @Autowired
    private TestService testService;
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private ConsentRequestService consentRequestService;

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
                    dto.setFinished(consultation.getFinished());

                    return dto;
                })
                .collect(Collectors.toSet());

        return ResponseEntity.ok(consultations);
    }

    @PreAuthorize("hasAuthority('patient')")
    @GetMapping("patient/getConsentRequests")
    public ResponseEntity<List<ConsentRequestDTO>> getConsentRequests(@RequestHeader (name="Authorization") String token) {
        token = token.substring(7);
        Patient patient = patientService.getPatientByName(jwtService.extractUsername(token));

        List<ConsentRequestDTO> consentRequests = patient.getConsentRequests().stream()
                .map(consentRequest -> {
                    ConsentRequestDTO dto = new ConsentRequestDTO();
                    dto.setId(consentRequest.getId());
                    dto.setConsultationName(consultationService.getConsultation(consentRequest.getConsultationId()).getName());
                    dto.setTestName(testService.getTest(consentRequest.getTestId()).getName());
                    dto.setDoctorName(doctorService.getDoctor(consentRequest.getDoctorId()).getUser().getName());

                    return dto;
                })
                .toList();

        return ResponseEntity.ok(consentRequests);
    }

    @PreAuthorize("hasAuthority('patient')")
    @PostMapping("patient/giveConsent")
    public ResponseEntity<Object> giveConsent(@RequestParam Long consentId, @RequestHeader (name="Authorization") String token) {
        token = token.substring(7);
        Patient patient = patientService.getPatientByName(jwtService.extractUsername(token));

        ConsentRequest consentRequest = consentRequestService.getConsentRequest(consentId);

        Doctor doctor = doctorService.getDoctor(consentRequest.getDoctorId());
        Test test = testService.getTest(consentRequest.getTestId());

        testService.addPermittedDoctors(doctor, test);
        doctorService.addVisibleTests(doctor, test);

        patientService.deleteConsentRequest(patient, consentRequest);
        consentRequestService.deleteConsentRequestById(consentId);

        return ResponseEntity.ok().body("Consent given!");
    }

    @PreAuthorize("hasAuthority('patient')")
    @PostMapping("patient/denyConsent")
    public ResponseEntity<Object> denyConsent(@RequestParam Long consentId, @RequestHeader (name="Authorization") String token) {
        token = token.substring(7);
        Patient patient = patientService.getPatientByName(jwtService.extractUsername(token));

        ConsentRequest consentRequest = consentRequestService.getConsentRequest(consentId);

        Doctor doctor = doctorService.getDoctor(consentRequest.getDoctorId());
        Test test = testService.getTest(consentRequest.getTestId());

        patientService.deleteConsentRequest(patient, consentRequest);
        consentRequestService.deleteConsentRequestById(consentId);

        return ResponseEntity.ok().body("Consent denied!");

    }

//    @GetMapping("/patient/getConsultations")
//    public ResponseEntity<Set<Consultation>> getConsultations(@RequestParam String token) {
//        Patient patient = patientService.getPatientByName(jwtService.extractUsername(token));
//        return ResponseEntity.ok(patient.getConsultations());
//    }
}
