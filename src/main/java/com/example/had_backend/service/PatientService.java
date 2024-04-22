package com.example.had_backend.service;

import com.example.had_backend.entity.Consultation;
import com.example.had_backend.entity.Patient;
import com.example.had_backend.entity.UserInfo;
import com.example.had_backend.repository.PatientRepository;
import com.example.had_backend.repository.UserInfoRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PatientService {
    @Autowired
    PatientRepository patientRepository;

    @Autowired
    UserInfoRepository userInfoRepository;

    public Patient getPatient(Long patientId) {
        return patientRepository.findById(patientId)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found with id: " + patientId));
    }

    public Patient getPatientByName(String patientName) {
//        Optional<UserInfo> user = userInfoRepository.findByName(patientName);
//        if(user.isEmpty()) {
//            throw new IllegalArgumentException("No such patient exists.");
//        }
//
//        Long userId = user.get().getId();
//        return patientRepository.findByUserId(userId)
//                .orElseThrow(() -> new EntityNotFoundException("Patient not found with id: " + userId));

        return patientRepository.findByPatientName(patientName)
                .orElseThrow(() -> new EntityNotFoundException("Patient not found with name: " + patientName));
    }

    public Patient createPatient() {
        Patient patient = new Patient();
        return patientRepository.save(patient);
    }

    public Patient createPatient(UserInfo userInfo) {
        Patient patient = new Patient();
        patient.setUser(userInfo);
        return patientRepository.save(patient);
    }

    @Transactional
    public void addConsultation(Patient patient, Consultation consultation) {
        patient.getConsultations().add(consultation);
    }
}
