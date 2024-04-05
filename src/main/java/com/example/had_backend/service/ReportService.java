package com.example.had_backend.service;

import com.example.had_backend.entity.Patient;
import com.example.had_backend.entity.Report;
import com.example.had_backend.repository.ReportRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportService {
    @Autowired
    ReportRepository reportRepository;

    public Report getReport(Long reportId) {
        return reportRepository.findById(reportId)
                .orElseThrow(() -> new EntityNotFoundException("Report not found with id: " + reportId));
    }

    public Report createReport() {
        Report report = new Report();
        return reportRepository.save(report);
    }

    public Report createReport(Patient patient, String description) {
        Report report = new Report();
        report.setPatient(patient);
        report.setDescription(description);
        return reportRepository.save(report);
    }
}
