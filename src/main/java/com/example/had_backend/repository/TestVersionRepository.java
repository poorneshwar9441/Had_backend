package com.example.had_backend.repository;

import com.example.had_backend.entity.TestVersion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TestVersionRepository extends JpaRepository<TestVersion, Long> {
}
