package com.example.had_backend.dto;

import lombok.Data;

import java.util.Date;

@Data
public class PrimaryConsultationsDTO {
    private String name;
    private String description;
    private String patientName;
    private Date date;
}
