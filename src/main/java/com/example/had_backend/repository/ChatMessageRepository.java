package com.example.had_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.had_backend.entity.ChatMessage;


@Repository
public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    // Additional methods for custom queries can be added here if needed

}
