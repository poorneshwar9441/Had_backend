package com.example.had_backend.service;

import com.example.had_backend.entity.Consultation;
import com.example.had_backend.entity.Test;
import com.example.had_backend.repository.TestRepository;
import jakarta.persistence.EntityNotFoundException;
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
}
