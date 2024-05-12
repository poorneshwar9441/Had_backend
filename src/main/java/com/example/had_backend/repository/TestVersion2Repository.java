package com.example.had_backend.repository;

import com.example.had_backend.entity.TestVersion1;
import com.example.had_backend.entity.TestVersion2;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TestVersion2Repository extends JpaRepository<TestVersion2,Long> {
    List<TestVersion2> findByTestId(Long testId);
}
