package com.example.had_backend.service;

import com.example.had_backend.entity.ConsentRequest;
import com.example.had_backend.entity.Patient;
import com.example.had_backend.repository.ConsentRequestRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConsentRequestService {
    @Autowired
    ConsentRequestRepository consentRequestRepository;

    public ConsentRequest getConsentRequest(Long consentRequestId) {
        return consentRequestRepository.findById(consentRequestId)
                .orElseThrow(() -> new EntityNotFoundException("ConsentRequest not found with id: " + consentRequestId));
    }

    public void deleteConsentRequestById(Long consentRequestId) {
        consentRequestRepository.deleteById(consentRequestId);
    }

    public ConsentRequest createConsentRequest(Patient patient, Long consultationId, Long testId, Long doctorId) {
        ConsentRequest newConsentRequest = new ConsentRequest();
        newConsentRequest.setPatient(patient);
        newConsentRequest.setConsultationId(consultationId);
        newConsentRequest.setTestId(testId);
        newConsentRequest.setDoctorId(doctorId);

        return consentRequestRepository.save(newConsentRequest);
    }
}
