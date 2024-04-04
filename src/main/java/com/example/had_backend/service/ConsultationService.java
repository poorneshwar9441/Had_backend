package com.example.had_backend.service;

import com.example.had_backend.entity.Consultation;
import com.example.had_backend.entity.Doctor;
import com.example.had_backend.entity.Patient;
import com.example.had_backend.repository.ConsultationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConsultationService {
    @Autowired
    ConsultationRepository consultationRepository;

    public Consultation createConsultation(Doctor doctor, Patient patient, String description) {
        Consultation consultation = new Consultation();
        consultation.setPatient(patient);
        consultation.setMainDoctor(doctor);
        consultation.setDescription(description);

        return consultationRepository.save(consultation);
    }
}
