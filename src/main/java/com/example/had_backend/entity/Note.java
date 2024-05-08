package com.example.had_backend.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @JsonBackReference(value = "notes_version")
//    @ToString.Exclude
//    @EqualsAndHashCode.Exclude
//    @ManyToOne
//    @JoinColumn(name = "versionId")
//    private TestVersion version;

    @JsonBackReference(value = "notes_test")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "testId")
    private Test test;

//    @ManyToOne
//    @JoinColumn(name = "userId")
//    private UserInfo sender;

    private String sender;
    private String message;
}
