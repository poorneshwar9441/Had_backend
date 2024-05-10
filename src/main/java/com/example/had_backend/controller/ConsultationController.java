package com.example.had_backend.controller;

import com.example.had_backend.dto.TestsDTO;
import com.example.had_backend.dto.UserDTO;
import com.example.had_backend.entity.Consultation;
import com.example.had_backend.entity.Doctor;
import com.example.had_backend.entity.Test;
import com.example.had_backend.entity.UserInfo;
import com.example.had_backend.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class ConsultationController {
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

    @PreAuthorize("hasAuthority('doctor')")
    @PostMapping("/consultation/addSecondaryDoctor")
    public ResponseEntity<Object> addSecondaryDoctor(@RequestParam String doctor, @RequestParam Long consultationId, @RequestHeader(name="Authorization") String token) {
        token = token.substring(7);

        Consultation consultation = consultationService.getConsultation(consultationId);
        Doctor doctor_curr = doctorService.getDoctorByName(jwtService.extractUsername(token));
        Doctor doctor_new = doctorService.getDoctorByName(doctor);

        if(doctor_curr == null || doctor_new == null || consultation == null) {
            return ResponseEntity.badRequest().body(null);
        }

        if(!consultation.getMainDoctor().equals(doctor_curr)) {
            return ResponseEntity.badRequest().body(null);
        }

        if(doctor_curr.equals(doctor_new)) {
            return ResponseEntity.badRequest().body(null);
        }

        consultationService.addSecondaryDoctor(doctor_new, consultation);
        doctorService.addSecondaryConsultation(doctor_new, consultation);

        return ResponseEntity.ok().body(null);
    }

    @GetMapping("/consultation/getSecondaryDoctors")
    public ResponseEntity<List<UserDTO>> getSecondaryDoctors(@RequestParam Long consultationId, @RequestHeader (name="Authorization") String token) {
        token =  token.substring(7);

        String username = jwtService.extractUsername(token);
        Consultation consultation = consultationService.getConsultation(consultationId);

        if(
                !consultation.getSecondaryDoctors().stream().anyMatch(d -> d.getUser().getName().equals(username))
                        && !consultation.getMainDoctor().getUser().getName().equals(username)
                        && !consultation.getPatient().getUser().getName().equals(username))
        {
            return ResponseEntity.badRequest().body(null);
        }

        List<UserDTO> secondaryDoctors = consultation.getSecondaryDoctors().stream()
                .map(doctor -> {
                    UserDTO dto = new UserDTO();
                    dto.setId(doctor.getUser().getId());
                    dto.setName(doctor.getUser().getName());
                    dto.setRoles(doctor.getUser().getRoles());

                    return dto;
                })
                .toList();

        return ResponseEntity.ok(secondaryDoctors);
    }

    @PreAuthorize("hasAuthority('doctor')")
    @PostMapping("/consultation/createTest")
    public ResponseEntity<Test> createTest(@RequestBody Map<String, Object> request, @RequestHeader(name="Authorization") String token) {
        token = token.substring(7);
        Long consultationId = ((Integer) request.get("consultationId")).longValue();
        String name = (String) request.get("name");
        String description = (String) request.get("description");
        Doctor doctor = doctorService.getDoctorByName(jwtService.extractUsername(token));

        Consultation consultation = consultationService.getConsultation(consultationId);

//        if(!doctor.getPrimaryConsultations().contains(consultation) && !doctor.getSecondaryConsultations().contains(consultation)) {
//            return ResponseEntity.badRequest().body(null);
//        }
        if(!doctor.getPrimaryConsultations().contains(consultation)) {
            return ResponseEntity.badRequest().body(null);
        }

        Test createdTest = testService.createTest(consultation, name, description);

        consultationService.addTest(consultation, createdTest);
        testService.addPermittedDoctors(doctor, createdTest);
        doctorService.addVisibleTests(doctor, createdTest);

        return ResponseEntity.ok(createdTest);
    }

    @GetMapping("/consultation/getTests")
    public ResponseEntity<Set<TestsDTO>> getTests(@RequestParam Long consultationId, @RequestHeader (name="Authorization") String token) {
        token =  token.substring(7);
//        Long consultationId = ((Integer) request.get("consultationId")).longValue();

        String username = jwtService.extractUsername(token);
        UserInfo user = userInfoService.getUserByUsername(username);
        Consultation consultation = consultationService.getConsultation(consultationId);

        if(
                !consultation.getSecondaryDoctors().stream().anyMatch(d -> d.getUser().getName().equals(username))
                && !consultation.getMainDoctor().getUser().getName().equals(username)
                && !consultation.getPatient().getUser().getName().equals(username))
        {
            return ResponseEntity.badRequest().body(null);
        }


        Set<Test> visibleTests;
        if(user.getRoles().contains("patient")) {
            visibleTests = consultation.getTests();
        } else {
            visibleTests = doctorService.getDoctorByName(username).getVisibleTests();
        }

//        Set<TestsDTO> tests = consultation.getTests().stream()
        Set<TestsDTO> tests = visibleTests.stream()
                .map(test -> {
                    TestsDTO dto = new TestsDTO();
                    dto.setId(test.getId());
                    dto.setName(test.getName());
                    dto.setDescription(test.getDescription());

                    return dto;
                })
                .collect(Collectors.toSet());

        return ResponseEntity.ok(tests);
    }

    @PreAuthorize("hasAuthority('doctor')")
    @PostMapping("/consultation/finishConsultation")
    public ResponseEntity<Object> finishConsultation(@RequestParam Long consultationId, @RequestHeader(name="Authorization") String token) {
        token = token.substring(7);

        Consultation consultation = consultationService.getConsultation(consultationId);
        Doctor doctor_curr = doctorService.getDoctorByName(jwtService.extractUsername(token));

        if(doctor_curr == null || consultation == null) {
            return ResponseEntity.badRequest().body(null);
        }

        if(!consultation.getMainDoctor().equals(doctor_curr)) {
            return ResponseEntity.badRequest().body(null);
        }

        consultationService.finishConsultation(consultation, doctor_curr);
        return ResponseEntity.ok().body(null);
    }

//    @PostMapping("/createConsultation")
//    public ResponseEntity<Consultation> createConsultation(@RequestBody Map<String, Object> request) {
//        Long doctorId = ((Integer) request.get("doctorId")).longValue();
//        Long patientId =  ((Integer) request.get("patientId")).longValue();
//        String description = (String) request.get("description");
//
//        Doctor doctor = doctorService.getDoctor(doctorId);
//        Patient patient = patientService.getPatient(patientId);
//
//        Consultation createdConsultation = consultationService.createConsultation(doctor, patient, description);
//        return ResponseEntity.ok(createdConsultation);
//    }
//    @GetMapping("/Testing")
//    public ResponseEntity<Object> getReq(){
//        // Create a list to hold multiple JSON objects
//        List<Map<String, Object>> responseList = new ArrayList<>();
//
//        // Create JSON objects with keys "testName" and "description" and add them to the list
//        Map<String, Object> obj1 = new HashMap<>();
//        obj1.put("testName", "Test 1");
//        obj1.put("description", "Description for Test 1");
//        responseList.add(obj1);
//
//        Map<String, Object> obj2 = new HashMap<>();
//        obj2.put("testName", "Test 2");
//        obj2.put("description", "Description for Test 2");
//        responseList.add(obj2);
//
//        // Return the ResponseEntity with the list of JSON objects and OK status
//        return ResponseEntity.ok(responseList);
//    }
}
