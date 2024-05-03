package com.example.had_backend.controller;

import com.example.had_backend.dto.UserDTO;
import com.example.had_backend.entity.AuthRequest;
import com.example.had_backend.entity.UserInfo;
import com.example.had_backend.service.JwtService;
import com.example.had_backend.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    private UserInfoService service;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private AuthenticationManager authenticationManager;

//    @GetMapping("/welcome")
//    public String welcome() {
//        return "Welcome this endpoint is not secure";
//    }

//    @PostMapping("/addNewUser")
//    public String addNewUser(@RequestBody UserInfo userInfo) {
//        System.out.println("added");
//
//        String return_type = service.addUser(userInfo);
////        if(userInfo.getRoles() == "Role_Doctor") {
////            // Add doctor to the doctor table
////
////
////        }
////        else if(userInfo.getRoles() == "Role_Patient"){
////
////        }
//
//        return return_type;
//    }

    @GetMapping("/RadioGraph/RadioProfile")
    @PreAuthorize("hasAuthority('Radiographer')")
    public String RProfile() {
        return "Welcome to Radiographer Page";
    }

    @GetMapping("/ting")
    public String temp() {
        return "Welcome to ting Page";
    }

    @GetMapping("/Doctor/DoctorProf")
    @PreAuthorize("hasAuthority('Doctor')")
    public String LProfile() {
        return "Welcome to Doctor Page";
    }

    @GetMapping("/admin/adminProfile")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public String adminProfile() {
        return "Welcome to Admin Profile";
    }

    @PostMapping("/generateToken")
    public ResponseEntity<String> authenticateAndGetToken(@RequestParam String role, @RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        if (authentication.isAuthenticated() && userInfoService.getUserByUsername(authRequest.getUsername()).getRoles().equals(role.toLowerCase())) {
            return ResponseEntity.ok().body(jwtService.generateToken(authRequest.getUsername()));
        } else {
            return ResponseEntity.badRequest().body("No such user found!");
//            throw new UsernameNotFoundException("invalid user request !");
        }
    }

    @GetMapping("/getDoctorsBySubstring")
    public  ResponseEntity<List<UserInfo>> getDoctorsBySubstring(@RequestParam String name) {
        return ResponseEntity.ok(userInfoService.getDoctorsBySubstring(name));
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<List<UserDTO>> getAllUsers(@RequestParam String role) {
        System.out.println("asdasd");

        List<UserInfo> users = switch (role) {
            case "doctor" -> userInfoService.getDoctors();
            case "radiographer" -> userInfoService.getRadiographers();
            case "radiologist" -> userInfoService.getRadiologists();
            case "patient" -> userInfoService.getPatients();
            default -> Collections.emptyList();
        };

        List<UserDTO> filteredUsers = users.stream()
                .map(user -> {
                    UserDTO dto = new UserDTO();
                    dto.setId(user.getId());
                    dto.setName(user.getName());
                    dto.setRoles(user.getRoles());

                    return dto;
                })
                .toList();

        return ResponseEntity.ok(filteredUsers);
    }
}