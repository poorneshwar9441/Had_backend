package com.example.had_backend.repository;

import com.example.had_backend.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatientRepository extends JpaRepository<Patient, Long> {
    @Query("SELECT p FROM Patient p WHERE p.user.id = :userId")
    Optional<Patient> findByUserId(@Param("userId") Long userId);

    @Query("SELECT p FROM Patient p JOIN p.user u WHERE u.name = :patientName")
    Optional<Patient> findByPatientName(@Param("patientName") String patientName);
}
