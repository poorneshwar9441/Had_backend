package com.example.had_backend.service;

import com.example.had_backend.entity.Doctor;
import com.example.had_backend.entity.FinalReport;
import com.example.had_backend.entity.Test;
import com.example.had_backend.entity.TestVersion1;
import com.example.had_backend.repository.FinalReportRepository;
import com.example.had_backend.repository.TestVersion1Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FinalReportService {
    @Autowired
    private FinalReportRepository finalReportRepository;

    public FinalReport createFinalReport(Long consultationId, Doctor doctor, byte[] imageData, String fileName) {
        try {
//            System.out.println("hereee");
            FinalReport finalReport = new FinalReport();
            finalReport.setConsultationId(consultationId);
            finalReport.setDoctor(doctor);
            finalReport.setData(imageData);
//            testVersion.

//        TestVersion createdTestVersion = testVersionRepository.save(testVersion);
//        return createdTestVersion;
            return finalReportRepository.save(finalReport);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create test version: " + e.getMessage(), e);
        }


    }

//    public TestVersion1 getTestVersion(Long testVersionId) {
//        return testVersion1Repository.findById(testVersionId)
//                .orElseThrow(() -> new EntityNotFoundException("Test version not found with id: " + testVersionId));
//    }

    public List<FinalReport> getAllFinalReports(Long consultationId){
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
        return finalReportRepository.findByConsultationId(consultationId);
//        return
    }
}
