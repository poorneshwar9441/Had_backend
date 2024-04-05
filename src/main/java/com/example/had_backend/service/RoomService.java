package com.example.had_backend.service;

import com.example.had_backend.entity.Consultation;
import com.example.had_backend.entity.Room;
import com.example.had_backend.repository.RoomRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoomService {
    @Autowired
    RoomRepository roomRepository;

    public Room getRoom(Long roomId) {
        return roomRepository.findById(roomId)
                .orElseThrow(() -> new EntityNotFoundException("Room not found with id: " + roomRepository));
    }

    public Room createRoom() {
        Room room = new Room();
        return roomRepository.save(room);
    }

    public Room createRoom(Consultation consultation, String description) {
        Room room = new Room();
        room.setConsultation(consultation);
        room.getUsers().add(consultation.getMainDoctor().getUser_id());
        room.getUsers().add(consultation.getPatient().getUser_id());
        room.setDescription(description);

        return roomRepository.save(room);
    }
}
