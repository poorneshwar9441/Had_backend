package com.example.had_backend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @ManyToMany
//    @JoinTable(
//            name = "room_user",
//            joinColumns = @JoinColumn(name = "roomId"),
//            inverseJoinColumns = @JoinColumn(name = "userId")
//    )
//    private Set<UserInfo> users = new HashSet<>();

//    @ManyToOne
//    @JoinColumn(name = "consultation", referencedColumnName = "id", nullable = false)
//    private Consultation consultation;

    private String description;
}
