package com.example.had_backend.service;



import com.example.had_backend.entity.ChatMessage;
import com.example.had_backend.repository.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class ChatMessageService {

    @Autowired
    private ChatMessageRepository chatMessageRepository;

    public List<ChatMessage> getAllMessages() {
        return chatMessageRepository.findAll();
    }

    public ChatMessage saveMessage(ChatMessage message) {
        return chatMessageRepository.save(message);
    }
}