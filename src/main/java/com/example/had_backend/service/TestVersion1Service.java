package com.example.had_backend.service;

import com.example.had_backend.entity.Doctor;
import com.example.had_backend.entity.Test;
import com.example.had_backend.entity.TestVersion;
import com.example.had_backend.entity.TestVersion1;
import com.example.had_backend.repository.TestVersion1Repository;
//import com.example.had_backend.repository.TestVersionRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TestVersion1Service {

    @Autowired
    TestVersion1Repository testVersion1Repository;

    public TestVersion1 createTestVersion(Test test, Doctor doctor, byte[] imageData, String fileName) {
        try {
//            System.out.println("hereee");
            TestVersion1 testVersion = new TestVersion1();
            testVersion.setTestId(test.getId());
            testVersion.setDoctor(doctor);
            testVersion.setData(imageData);
//            testVersion.

//        TestVersion createdTestVersion = testVersionRepository.save(testVersion);
//        return createdTestVersion;
            return testVersion1Repository.save(testVersion);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create test version: " + e.getMessage(), e);
        }


    }

//    public TestVersion1 getTestVersion(Long testVersionId) {
//        return testVersion1Repository.findById(testVersionId)
//                .orElseThrow(() -> new EntityNotFoundException("Test version not found with id: " + testVersionId));
//    }

    public List<TestVersion1> getAllVersions(Long testId){
//        List<TestVersion1> li;
//        try{
//            li=testVersion1Repository.findByTestId(testId);
//            return li;
//        }
//        catch (Exception e){
//            System.out.println(e);
//            List<TestVersion1> li2=new ArrayList<>();
//            return li2;
//        }
        return testVersion1Repository.findByTestId(testId);
//        return
    }
}
