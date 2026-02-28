package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.entity.Note;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
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

    public int createNote(Integer userId, String title, String description) {
        Note note = new Note(null, title, description,userId);
        return noteMapper.insert(note);
    }

    public List<Note> getNotesByUserId(Integer userId) {
        return noteMapper.getNotesByUserId(userId);
    }
    public int updateNote(Integer noteId, String title, String description,Integer userId) {
        Note note = new Note(noteId, title, description,userId);
        return noteMapper.update(note);
    }
    public void deleteNote(Integer noteId) {
        noteMapper.delete(noteId);
    }
}
