package com.example.had_backend.service;

import com.example.had_backend.entity.Doctor;
import com.example.had_backend.entity.UserInfo;
import com.example.had_backend.repository.DoctorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DoctorService {
    @Autowired
    DoctorRepository doctorRepository;

    public Doctor getDoctor(Long doctorId) {
        return doctorRepository.findById(doctorId)
                .orElseThrow(() -> new EntityNotFoundException("Doctor not found with id: " + doctorId));
    }

    public Doctor createDoctor() {
        Doctor doctor = new Doctor();
        return doctorRepository.save(doctor);
    }

    public Doctor createDoctor(UserInfo userInfo) {
        Doctor doctor = new Doctor();
        doctor.setUser_id(userInfo);
        return doctorRepository.save(doctor);
    }
}
