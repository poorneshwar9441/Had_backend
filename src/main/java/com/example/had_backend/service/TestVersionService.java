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

    public TestVersion createTestVersion(Test test, Doctor doctor, byte[] imageData) {
        TestVersion testVersion = new TestVersion();
        testVersion.setTest(test);
        testVersion.setDoctor(doctor);
        testVersion.setData(imageData);

        return testVersionRepository.save(testVersion);
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
