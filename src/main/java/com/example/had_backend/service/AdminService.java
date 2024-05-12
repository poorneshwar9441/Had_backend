package com.example.had_backend.service;

import com.example.had_backend.entity.Admin;
import com.example.had_backend.entity.Doctor;
import com.example.had_backend.entity.UserInfo;
import com.example.had_backend.repository.AdminRepository;
import com.example.had_backend.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class AdminService{
//    public Doctor createDoctor(UserInfo userInfo) {
//        Doctor doctor = new Doctor();
//        doctor.setUser(userInfo);
//        return doctorRepository.save(doctor);
//    }
    @Autowired
    AdminRepository adminRepository;
    public Admin createAdmin(UserInfo Info){
        Admin a = new Admin();
        a.setUser(Info);

        return adminRepository.save(a);
    }


}
