package com.example.had_backend.service;

import com.example.had_backend.entity.Patient;
import com.example.had_backend.entity.UserInfo;
import com.example.had_backend.repository.PatientRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientService {
    @Autowired
    PatientRepository patientRepository;

    public Patient getPatient(Long patientId) {
        return patientRepository.findById(patientId)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found with id: " + patientId));
    }

    public Patient createPatient() {
        Patient patient = new Patient();
        return patientRepository.save(patient);
    }

    public Patient createPatient(UserInfo userInfo) {
        Patient patient = new Patient();
        patient.setUser_id(userInfo);
        return patientRepository.save(patient);
    }
}
