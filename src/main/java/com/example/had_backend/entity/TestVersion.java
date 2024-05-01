package com.example.had_backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TestVersion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @JsonBackReference
//    @ToString.Exclude
//    @EqualsAndHashCode.Exclude
//    @ManyToOne
//    @JoinColumn(name = "testId", referencedColumnName = "id", nullable = false)
//    private Test test;
//
//    @JsonManagedReference
//    @OneToMany(mappedBy = "version", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Note> notes = new ArrayList<>();
}
