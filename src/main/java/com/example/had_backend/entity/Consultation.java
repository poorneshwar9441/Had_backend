package com.example.had_backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Consultation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "patient", referencedColumnName = "id", nullable = false)
    private Patient patient;

    @ManyToOne
    @JoinColumn(name = "mainDoctor", referencedColumnName = "id", nullable = false)
    private Doctor mainDoctor;

    @ManyToMany
    @JoinTable(
            name = "consultation_doctor",
            joinColumns = @JoinColumn(name = "consultationId"),
            inverseJoinColumns = @JoinColumn(name = "doctorId")
    )
    private Set<Doctor> secondaryDoctors = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "consultation_report",
            joinColumns = @JoinColumn(name = "consultationId"),
            inverseJoinColumns = @JoinColumn(name = "reportId")
    )
    private Set<Report> reports = new HashSet<>();

    @OneToMany(mappedBy = "consultation")
    private Set<Room> rooms = new HashSet<>();

    private String description;
    private Boolean finished;
}
