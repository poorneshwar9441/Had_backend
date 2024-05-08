package com.example.had_backend.dto;

import lombok.Data;

@Data
public class NotesDTO {
    private Long id;
    private String sender;
    private String message;
}
