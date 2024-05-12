package com.example.had_backend.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FinalReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    private Long consultationId;
//    @JsonBackReference(value = "versions_test")
//    @ToString.Exclude
//    @EqualsAndHashCode.Exclude
//    @ManyToOne
//    @JoinColumn(name = "testId", referencedColumnName = "id", nullable = false)
//    private Test test;

//    @OneToOne
//    @JoinColumn(name = "imageId", referencedColumnName = "id")
//    private Image testImage;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToOne
    @JoinColumn(name = "doctorId", referencedColumnName = "id")
    private Doctor doctor;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @Lob
    @Column(length = Integer.MAX_VALUE)
    private byte[] data; // Byte array to store image data

//    @JsonManagedReference(value = "notes_version")
//    @OneToMany(mappedBy = "version", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Note> notes = new ArrayList<>();
}

//package com.example.had_backend.entity;
//
//import com.fasterxml.jackson.annotation.JsonBackReference;
//import jakarta.persistence.*;
//import lombok.*;
//
//@Entity
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//public class TestVersion {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
////    @JsonBackReference(value = "notes_version")
////    @ToString.Exclude
////    @EqualsAndHashCode.Exclude
////    @ManyToOne
////    @JoinColumn(name = "versionId")
////    private TestVersion version;
//
//    @ToString.Exclude
//    @EqualsAndHashCode.Exclude
//    @OneToOne
//    @JoinColumn(name = "doctorId", referencedColumnName = "id")
//    private Doctor doctor;
//
//    @JsonBackReference(value = "versions_test")
//    @ToString.Exclude
//    @EqualsAndHashCode.Exclude
//    @ManyToOne
//    @JoinColumn(name = "testId")
//    private Test test;
//}

