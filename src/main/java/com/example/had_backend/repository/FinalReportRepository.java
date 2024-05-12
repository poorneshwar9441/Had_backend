package com.example.had_backend.repository;

import com.example.had_backend.entity.FinalReport;
//import com.example.had_backend.entity.TestVersion1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FinalReportRepository extends JpaRepository<FinalReport,Long> {
    List<FinalReport> findByConsultationId(Long consultationId);
}
