package com.udacity.jwdnd.course1.cloudstorage.controller;


import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/note")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }


    @PostMapping("/save")
    public String saveNote(
            @RequestParam(required = false) Integer noteId,
            @RequestParam String noteTitle,
            @RequestParam String noteDescription) {

        if (noteId != null) {
            // has ID = edit
            noteService.updateNote(noteId, noteTitle, noteDescription);
        } else {
            // no ID = add new
            noteService.createNote(noteTitle, noteDescription);
        }
        return "redirect:/home";
    }
    @GetMapping("/delete/{id}")
    public String deleteNote(@PathVariable Integer id) {
        noteService.deleteNote(id);
        return "redirect:/home";
    }
}