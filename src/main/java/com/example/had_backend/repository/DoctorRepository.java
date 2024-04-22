package com.example.had_backend.repository;

import com.example.had_backend.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    @Query("SELECT d FROM Doctor d JOIN d.user u WHERE u.name = :doctorName")
    Optional<Doctor> findByDoctorName(@Param("doctorName") String doctorName);
}
