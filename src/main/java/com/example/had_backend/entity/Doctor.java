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
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private UserInfo user_id;

    @OneToMany(mappedBy = "mainDoctor")
    private Set<Consultation> primaryConsultations = new HashSet<>();

    @ManyToMany(mappedBy = "secondaryDoctors")
    private Set<Consultation> secondaryConsultations = new HashSet<>();
}
