package com.example.had_backend.service;

import com.example.had_backend.entity.Consultation;
import com.example.had_backend.entity.Doctor;
import com.example.had_backend.entity.UserInfo;
import com.example.had_backend.repository.DoctorRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
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

    public Doctor getDoctorByName(String doctorName) {
        return doctorRepository.findByDoctorName(doctorName)
                .orElseThrow(() -> new EntityNotFoundException("Doctor not found with name: " + doctorName));
    }

    public Doctor createDoctor() {
        Doctor doctor = new Doctor();
        return doctorRepository.save(doctor);
    }

    public Doctor createDoctor(UserInfo userInfo) {
        Doctor doctor = new Doctor();
        doctor.setUser(userInfo);
        return doctorRepository.save(doctor);
    }

    @Transactional
    public void addPrimaryConsultation(Doctor doctor, Consultation consultation) {
        doctor.getPrimaryConsultations().add(consultation);
    }

    @Transactional
    public void addSecondaryConsultation(Doctor doctor, Consultation consultation) {
        doctor.getSecondaryConsultations().add(consultation);
    }
}
