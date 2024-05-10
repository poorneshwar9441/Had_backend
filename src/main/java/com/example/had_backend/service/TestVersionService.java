package com.example.had_backend.service;

import com.example.had_backend.entity.Doctor;
import com.example.had_backend.entity.Test;
import com.example.had_backend.entity.TestVersion;
import com.example.had_backend.repository.TestVersionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestVersionService {
    @Autowired
    TestVersionRepository testVersionRepository;

    public TestVersion createTestVersion(Test test, Doctor doctor, byte[] imageData, String fileName) {
        try {
//            System.out.println("hereee");
            TestVersion testVersion = new TestVersion();
            testVersion.setTest(test);
            testVersion.setDoctor(doctor);
            testVersion.setData(imageData);
//            testVersion.

//        TestVersion createdTestVersion = testVersionRepository.save(testVersion);
//        return createdTestVersion;
            return testVersionRepository.save(testVersion);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create test version: " + e.getMessage(), e);
        }


    }

    public TestVersion getTestVersion(Long testVersionId) {
        return testVersionRepository.findById(testVersionId)
                .orElseThrow(() -> new EntityNotFoundException("Test version not found with id: " + testVersionId));
    }

//    @Transactional
//    public void addNote(TestVersion testVersion, Note note) {
//        testVersion.getNotes().add(note);
//    }
}
