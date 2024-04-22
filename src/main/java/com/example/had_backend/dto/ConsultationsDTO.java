package com.example.had_backend.dto;

import lombok.Data;

import java.util.Date;

@Data
public class ConsultationsDTO {
    private Long id;
    private String name;
    private String description;
    private String patientName;
    private String doctorName;
    private Date date;
}
