package com.example.had_backend.repository;

import com.example.had_backend.entity.ConsentRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsentRequestRepository extends JpaRepository<ConsentRequest, Long> {
}
