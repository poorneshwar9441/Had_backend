package com.example.had_backend.service;

import com.example.had_backend.entity.UserInfo;
import com.example.had_backend.repository.UserInfoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserInfoService implements UserDetailsService {

    @Autowired
    private UserInfoRepository repository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<UserInfo> userDetail = repository.findByName(username);

        // Converting userDetail to UserDetails
        return userDetail.map(UserInfoDetails::new)
                .orElseThrow(() -> new UsernameNotFoundException("User not found " + username));
    }

    @Transactional
    public UserInfo addUser(UserInfo userInfo) {
//        if(userInfo.getPassword() == null){
//            return "failed";
//        }
        try{
            userInfo.setRoles(userInfo.getRoles().toLowerCase());
            userInfo.setPassword(encoder.encode(userInfo.getPassword()));
            return repository.save(userInfo);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("Username already exists!");
        }
    }

    public UserInfo getUserByUsername(String username) {
        Optional<UserInfo> userInfoOptional = repository.findByName(username);
        return userInfoOptional.orElse(null); // Handle the Optional
    }

    public List<UserInfo> getDoctorsBySubstring(String username) {
        return repository.findByUsernameContainingAndRoleNot(username, "patient");
    }

    public List<UserInfo> getDoctors() {
//        return repository.findByRoleNot("patient");
        System.out.println(repository.findByRole("doctor"));
        return repository.findByRole("doctor");
    }

    public List<UserInfo> getRadiographers() {
        return repository.findByRole("radiographer");
    }

    public List<UserInfo> getRadiologists() {
        return repository.findByRole("radiologist");
    }

    public List<UserInfo> getPatients() {
        return repository.findByRole("patient");
    }
}