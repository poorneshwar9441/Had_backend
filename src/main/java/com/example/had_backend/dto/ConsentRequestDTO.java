package com.example.had_backend.dto;

import lombok.Data;

@Data
public class ConsentRequestDTO {
    private Long id;
    private String consultationName;
    private String TestName;
    private String doctorName;
}
