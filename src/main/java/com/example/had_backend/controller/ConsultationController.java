package com.example.had_backend.controller;

import com.example.had_backend.service.ConsultationService;
import com.example.had_backend.service.DoctorService;
import com.example.had_backend.service.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class ConsultationController {
    @Autowired
    private ConsultationService consultationService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientService patientService;

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
