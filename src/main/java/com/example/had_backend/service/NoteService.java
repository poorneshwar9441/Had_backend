package com.example.had_backend.service;

import com.example.had_backend.entity.Note;
import com.example.had_backend.entity.TestVersion;
import com.example.had_backend.repository.NoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NoteService {
    @Autowired
    NoteRepository noteRepository;

    public Note createNote(TestVersion testVersion, String sender, String message) {
        Note note = new Note();
        note.setVersion(testVersion);
        note.setSender(sender);
        note.setMessage(message);

        return noteRepository.save(note);
    }
}
