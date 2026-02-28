package com.udacity.jwdnd.course1.cloudstorage.controller;


import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/note")
public class NoteController {

    private final NoteService noteService;
    private final UserService userService;

    public NoteController(NoteService noteService, UserService userService) {
        this.noteService = noteService;
        this.userService=userService;
    }

    @PostMapping("/save")
    public String saveNote(
            @RequestParam(required = false) Integer noteId,
            @RequestParam String noteTitle,
            @RequestParam String noteDescription,
            Authentication authentication) {

        int userId = userService.getUser(authentication.getName()).getUserId();

        if (noteId != null) {
            noteService.updateNote(noteId, noteTitle, noteDescription,userId);
        } else {
            noteService.createNote(userId, noteTitle, noteDescription);
        }
        return "redirect:/home";
    }

    @GetMapping("/delete/{id}")
    public String deleteNote(@PathVariable Integer id) {
        noteService.deleteNote(id);
        return "redirect:/home";
    }
}