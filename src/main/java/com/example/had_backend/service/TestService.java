package com.example.had_backend.service;

import com.example.had_backend.entity.Consultation;
import com.example.had_backend.entity.Doctor;
import com.example.had_backend.entity.Test;
import com.example.had_backend.entity.TestVersion;
import com.example.had_backend.repository.TestRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService {
    @Autowired
    TestRepository testRepository;

    public Test getTest(Long testId) {
        return testRepository.findById(testId)
                .orElseThrow(() -> new EntityNotFoundException("Test not found with id: " + testId));
    }

    public Test createTest(Consultation consultation, String name, String description) {
        Test test = new Test();
        test.setConsultation(consultation);
        test.setName(name);
        test.setDescription(description);

        return testRepository.save(test);
    }

    @Transactional
    public void addPermittedDoctors(Doctor doctor, Test test) {
        test.getPermittedDoctors().add(doctor);
    }

    @Transactional
    public void addTestVersion(Test test, TestVersion testVersion) {
        test.getVersions().add(testVersion);
    }
}
