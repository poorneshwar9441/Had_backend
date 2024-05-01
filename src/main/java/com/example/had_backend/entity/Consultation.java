package com.example.had_backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
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

    @JsonBackReference(value = "consultations_patient")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "patientId", referencedColumnName = "id", nullable = false)
    private Patient patient;

    @JsonBackReference(value="primaryConsultations_mainDoctor")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "mainDoctorId", referencedColumnName = "id", nullable = false)
    private Doctor mainDoctor;

    @JsonBackReference(value="secondaryConsultations_secondaryDoctors")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany
    @JoinTable(
            name = "consultation_doctor",
            joinColumns = @JoinColumn(name = "consultationId"),
            inverseJoinColumns = @JoinColumn(name = "doctorId")
    )
    private Set<Doctor> secondaryDoctors = new HashSet<>();

    @JsonManagedReference(value = "tests_consultation")
    @OneToMany(mappedBy = "consultation", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Test> tests = new HashSet<>();

//    @ManyToMany
//    @JoinTable(
//            name = "consultation_report",
//            joinColumns = @JoinColumn(name = "consultationId"),
//            inverseJoinColumns = @JoinColumn(name = "reportId")
//    )
//    private Set<Report> reports = new HashSet<>();
//
//    @OneToMany(mappedBy = "consultation")
//    private Set<Room> rooms = new HashSet<>();

    private String name;
    private String description;
    private Date date;

    private Boolean finished;
}
