package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/file")
public class FileController {
    private final FileService fileService;
    private final UserService userService;

    public FileController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }
    // Upload file
    @PostMapping("/upload")
    public String uploadFile(
            @RequestParam("fileUpload") MultipartFile fileUpload,
            Authentication authentication) {

        if (fileUpload.isEmpty()) {
            return "redirect:/home?error=Please select a file!";
        }

        try {
            int userId = userService.getUser(authentication.getName()).getUserId();
            fileService.addFile(fileUpload, userId);
            return "redirect:/result?success";
        } catch (Exception e) {
            return "redirect:/result?error";
        }

    }
    // Delete file
    @GetMapping("/delete/{fileId}")
    public String deleteFile(@PathVariable Integer fileId) {
        try {
            fileService.deleteFile(fileId);
            return "redirect:/result?success";
        } catch (Exception e) {
            return "redirect:/result?error";
        }
    }
}
