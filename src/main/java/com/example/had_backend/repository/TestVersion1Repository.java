package com.example.had_backend.repository;

//import com.example.had_backend.entity.TestVersion;
import com.example.had_backend.entity.TestVersion1;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestVersion1Repository extends JpaRepository<TestVersion1, Long> {
    List<TestVersion1> findByTestId(Long testId);
}
