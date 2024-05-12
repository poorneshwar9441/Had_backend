package com.example.had_backend.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "userId", referencedColumnName = "id")
    private UserInfo user;

    @JsonManagedReference(value = "consultations_patient")
    @OneToMany(mappedBy = "patient")
    private Set<Consultation> consultations = new HashSet<>();

    @JsonManagedReference(value = "consent_patient")
    @OneToMany(mappedBy = "patient")
    private Set<ConsentRequest> consentRequests = new HashSet<>();
}
