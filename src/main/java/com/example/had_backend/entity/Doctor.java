package com.example.had_backend.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private UserInfo user;

    @JsonManagedReference(value="primaryConsultations_mainDoctor")
    @OneToMany(mappedBy = "mainDoctor", cascade = CascadeType.ALL)
    private Set<Consultation> primaryConsultations = new HashSet<>();

    @ManyToMany(mappedBy = "secondaryDoctors")
    private Set<Consultation> secondaryConsultations = new HashSet<>();

    private Long hospitalId;
}
