package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.entity.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


@Controller
@RequestMapping("/home")

public class HomeController {

    private final NoteService noteService;
    private final FileService fileService;
    private final UserService userService;
    private final CredentialService credentialService;

    public HomeController(NoteService noteService, FileService fileService, UserService userService,CredentialService credentialService) {
        this.noteService = noteService;
        this.fileService = fileService;
        this.userService = userService;
        this.credentialService=credentialService;
    }

    @GetMapping
    public String home(Model model, Authentication authentication) {
        int userId = userService.getUser(authentication.getName()).getUserId();
        model.addAttribute("files", fileService.getAllFiles(userId));
        model.addAttribute("notes", noteService.getNotesByUserId(userId));
        model.addAttribute("credentials",  credentialService.getCredentialsByUserId(userId));
        return "home";
    }
}

