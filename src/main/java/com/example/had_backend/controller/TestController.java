package com.example.had_backend.controller;

import com.example.had_backend.dto.NotesDTO;
import com.example.had_backend.dto.TestVersionsDTO;
import com.example.had_backend.entity.*;
import com.example.had_backend.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
public class TestController {
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

    @Autowired
    private NoteService noteService;

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

    @PreAuthorize("hasAnyAuthority('doctor', 'radiologist', 'radiographer')")
    @PostMapping("/test/createVersion")
    public ResponseEntity<Object> createVersion(@RequestParam Long testId, @RequestParam("file") MultipartFile file, @RequestHeader(name = "Authorization") String token) {
        token =  token.substring(7);
        String username = jwtService.extractUsername(token);
        Doctor doctor = doctorService.getDoctorByName(username);
        Test test = testService.getTest(testId);

        if(!test.getPermittedDoctors().contains(doctor)) {
            return ResponseEntity.badRequest().body(null);
        }

        byte[] imageData;
        try {
            imageData = file.getBytes();
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image: " + e.getMessage());
        }

        TestVersion createdTestVersion = testVersionService.createTestVersion(test, doctor, imageData);
        testService.addTestVersion(test, createdTestVersion);

//        createdTestVersion.setDoctor(doctor);
//        createdTestVersion.setTest(test);

//        try {
//            byte[] imageData = file.getBytes();
//            createdTestVersion.setData(imageData);
//        } catch (IOException e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload image: " + e.getMessage());
//        }

        return ResponseEntity.ok().body(null);
    }

    @GetMapping("/test/getVersions")
    public ResponseEntity<Object> getVersions(@RequestParam Long testId, @RequestHeader(name = "Authorization") String token) {
        token =  token.substring(7);
        String username = jwtService.extractUsername(token);
        Doctor doctor = doctorService.getDoctorByName(username);
        Test test = testService.getTest(testId);

        if(!test.getPermittedDoctors().contains(doctor)) {
            return ResponseEntity.badRequest().body(null);
        }

        List<TestVersionsDTO> testVersions = test.getVersions().stream()
                .map(testVersion -> {
                    TestVersionsDTO dto = new TestVersionsDTO();
                    dto.setId(testVersion.getId());
                    dto.setName(testVersion.getDoctor().getUser().getName());

                    return dto;
                })
                .toList();

        return ResponseEntity.ok().body(testVersions);
    }

    @PostMapping("/test/addNote")
    public ResponseEntity<Object> addNote(@RequestParam Long testId, @RequestBody Map<String, Object> request, @RequestHeader(name = "Authorization") String token) {
        token = token.substring(7);
        String username = jwtService.extractUsername(token);
        Doctor doctor = doctorService.getDoctorByName(username);
//        TestVersion testVersion = testVersionService.getTestVersion(testVersionId);
//        Test test = testVersion.getTest();
        Test test = testService.getTest(testId);

        if(!test.getPermittedDoctors().contains(doctor)) {
            return ResponseEntity.badRequest().body(null);
        }

//        String sender = (String) request.get("sender");
        String message = (String) request.get("message");

//        Note createdNote = noteService.createNote(testVersion, username, message);
//        testVersionService.addNote(testVersion, createdNote);

        Note createdNote = noteService.createNote(test, username, message);
        testService.addNote(test, createdNote);

        return ResponseEntity.ok().body("Note added!");
    }

    @GetMapping("/test/getNotes")
    public ResponseEntity<List<NotesDTO>> getNotes(@RequestParam Long testId, @RequestHeader(name = "Authorization") String token) {
        token = token.substring(7);
        String username = jwtService.extractUsername(token);
        Doctor doctor = doctorService.getDoctorByName(username);
//        TestVersion testVersion = testVersionService.getTestVersion(testVersionId);
//        Test test = testVersion.getTest();
        Test test = testService.getTest(testId);

        if(!test.getPermittedDoctors().contains(doctor)) {
            return ResponseEntity.badRequest().body(null);
        }

        List<NotesDTO> notes = test.getNotes().stream()
                .map(note -> {
                    NotesDTO dto = new NotesDTO();
                    dto.setId(note.getId());
                    dto.setSender(note.getSender());
                    dto.setMessage(note.getMessage());

                    return dto;
                })
                .toList();

        return ResponseEntity.ok().body(notes);
    }


//    @GetMapping("/test/getFile")
//    public ResponseEntity<byte[]> getFile(@RequestParam Long testId, @RequestParam Long testVersionId, @RequestHeader(name = "Authorization") String token) {
//        token =  token.substring(7);
//        String username = jwtService.extractUsername(token);
//        Doctor doctor = doctorService.getDoctorByName(username);
//        Test test = testService.getTest(testId);
//        TestVersion testVersion = testVersionService.getTestVersion(testVersionId);
//
//        if(!test.getPermittedDoctors().contains(doctor)) {
//            return ResponseEntity.badRequest().body(null);
//        }
//
//        try {
//            if(testVersion.getData() == null) {
//                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
//            }
//            return ResponseEntity.ok()
//                    .contentType(MediaType.IMAGE_JPEG)
//                    .body(testVersion.getData());
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//        }
//    }
}
