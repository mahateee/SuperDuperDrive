package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.entity.Note;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    private static final org.slf4j.Logger logger =
            org.slf4j.LoggerFactory.getLogger(NoteService.class);
    private final NoteMapper noteMapper;

    public NoteService(NoteMapper noteMapper) {
        this.noteMapper = noteMapper;
    }

    public int createNote(String title, String description) {
        Note note = new Note(null, title, description);
        return noteMapper.insert(note);
    }
    public List<Note> getAllNotes() {
        return noteMapper.getAllNotes();
    }
    public int updateNote(Integer noteId, String title, String description) {
        Note note = new Note(noteId, title, description);
        return noteMapper.update(note);
    }
    public void deleteNote(Integer noteId) {
        noteMapper.delete(noteId);
    }
}
