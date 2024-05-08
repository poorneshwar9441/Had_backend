package com.example.had_backend.controller;

import com.example.had_backend.dto.NotesDTO;
import com.example.had_backend.entity.Doctor;
import com.example.had_backend.entity.Note;
import com.example.had_backend.entity.Test;
import com.example.had_backend.entity.TestVersion;
import com.example.had_backend.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @Autowired
    private NoteService noteService;

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

    @PostMapping("/testVersion/addNote")
    public ResponseEntity<Object> addNote(@RequestParam Long testVersionId, @RequestBody Map<String, Object> request, @RequestHeader(name = "Authorization") String token) {
        token = token.substring(7);
        String username = jwtService.extractUsername(token);
        Doctor doctor = doctorService.getDoctorByName(username);
        TestVersion testVersion = testVersionService.getTestVersion(testVersionId);
        Test test = testVersion.getTest();

        if(!test.getPermittedDoctors().contains(doctor)) {
            return ResponseEntity.badRequest().body(null);
        }

//        String sender = (String) request.get("sender");
        String message = (String) request.get("message");

        Note createdNote = noteService.createNote(testVersion, username, message);
        testVersionService.addNote(testVersion, createdNote);

        return ResponseEntity.ok().body("Note added!");
    }

    @GetMapping("/testVersion/getNotes")
    public ResponseEntity<List<NotesDTO>> getNotes(@RequestParam Long testVersionId, @RequestHeader(name = "Authorization") String token) {
        token =  token.substring(7);
        String username = jwtService.extractUsername(token);
        Doctor doctor = doctorService.getDoctorByName(username);
        TestVersion testVersion = testVersionService.getTestVersion(testVersionId);
        Test test = testVersion.getTest();

        if(!test.getPermittedDoctors().contains(doctor)) {
            return ResponseEntity.badRequest().body(null);
        }

        List<NotesDTO> notes = testVersion.getNotes().stream()
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
}
