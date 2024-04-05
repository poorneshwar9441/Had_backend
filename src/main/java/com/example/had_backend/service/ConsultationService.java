package com.example.had_backend.service;

import com.example.had_backend.entity.Consultation;
import com.example.had_backend.entity.Doctor;
import com.example.had_backend.entity.Patient;
import com.example.had_backend.entity.Report;
import com.example.had_backend.repository.ConsultationRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class ConsultationService {
    @Autowired
    ConsultationRepository consultationRepository;

    public Consultation getConsultation(Long consultationId) {
        return consultationRepository.findById(consultationId)
                .orElseThrow(() -> new EntityNotFoundException("Consultation not found with id: " + consultationId));
    }

    public Consultation createConsultation(Doctor doctor, Patient patient, String description) {
        Consultation consultation = new Consultation();
        consultation.setPatient(patient);
        consultation.setMainDoctor(doctor);
        consultation.setDescription(description);

        return consultationRepository.save(consultation);
    }

    public void checkMainDoctor(Consultation consultation, Doctor doctor) {
        if(consultation.getMainDoctor() == null || !Objects.equals(consultation.getMainDoctor().getId(), doctor.getId())) {
            throw new IllegalArgumentException("The provided doctor is not the main doctor of the consultation.");
        }
    }

    public void finishConsultation(Consultation consultation, Doctor doctor) {
        checkMainDoctor(consultation, doctor);
        consultation.setFinished(true);
    }

    public void startConsultation(Consultation consultation, Doctor doctor) {
        checkMainDoctor(consultation, doctor);
        consultation.setFinished(false);
    }

    public void addSecondaryDoctor(Consultation consultation, Doctor doctor) {
        consultation.getSecondaryDoctors().add(doctor);
    }

    public void addReport(Consultation consultation, Report report) {
        consultation.getReports().add(report);
    }
}
