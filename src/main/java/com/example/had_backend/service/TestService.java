package com.example.had_backend.service;

import com.example.had_backend.repository.TestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestService {
    @Autowired
    TestRepository testRepository;
}
