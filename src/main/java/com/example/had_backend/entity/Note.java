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
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @JsonBackReference
//    @ToString.Exclude
//    @EqualsAndHashCode.Exclude
//    @ManyToOne
//    @JoinColumn(name = "versionId")
//    private TestVersion version;
//
//    @ManyToOne
//    @JoinColumn(name = "userId")
//    private UserInfo sender;
//
//    private String message;
}
