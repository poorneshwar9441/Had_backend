package com.example.had_backend.service;

import org.springframework.stereotype.Service;

@Service
public class RoomService {
//    @Autowired
//    RoomRepository roomRepository;
//
//    public Room getRoom(Long roomId) {
//        return roomRepository.findById(roomId)
//                .orElseThrow(() -> new EntityNotFoundException("Room not found with id: " + roomRepository));
//    }
//
//    public Room createRoom() {
//        Room room = new Room();
//        return roomRepository.save(room);
//    }
//
//    public Room createRoom(Consultation consultation, String description) {
//        Room room = new Room();
//        room.setConsultation(consultation);
//        room.getUsers().add(consultation.getMainDoctor().getUser_id());
//        room.getUsers().add(consultation.getPatient().getUser_id());
//        room.setDescription(description);
//
//        return roomRepository.save(room);
//    }
}
