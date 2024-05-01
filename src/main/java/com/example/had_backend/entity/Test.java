package com.example.had_backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;

    @JsonBackReference(value = "tests_consultation")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "consultationId", referencedColumnName = "id", nullable = false)
    private Consultation consultation;


    @JsonBackReference(value="visibleTests_permittedDoctors")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany
    @JoinTable(
            name = "test_doctor",
            joinColumns = @JoinColumn(name = "testId"),
            inverseJoinColumns = @JoinColumn(name = "doctorId")
    )
    private Set<Doctor> permittedDoctors = new HashSet<>();


//    @JsonManagedReference
//    @OneToMany(mappedBy = "test", cascade = CascadeType.ALL, orphanRemoval = true)
//    private Set<TestVersion> versions = new HashSet<>();
}
