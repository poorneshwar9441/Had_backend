package com.example.had_backend.service;

import com.example.had_backend.repository.TestVersionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TestVersionService {
    @Autowired
    TestVersionRepository testVersionRepository;
}
