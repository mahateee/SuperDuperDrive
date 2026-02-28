package com.udacity.jwdnd.course1.cloudstorage.controller;


import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/credential")
public class CredentialController {

    private final CredentialService credentialService;
    private final UserService userService;

    public CredentialController(CredentialService credentialService,
                                UserService userService) {
        this.credentialService = credentialService;
        this.userService = userService;
    }

    @PostMapping("/save")
    public String saveCredential(
            @RequestParam(required = false) Integer credentialId,
            @RequestParam String url,
            @RequestParam String username,
            @RequestParam String password,
            Authentication authentication) {

        int userId = userService.getUser(authentication.getName()).getUserId();

        if (credentialId != null) {
            // edit existing
            credentialService.updateCredential(credentialId, url, username, password);
        } else {
            // add new
            credentialService.createCredential(url, username, password, userId);
        }
        return "redirect:/home";
    }

    @GetMapping("/delete/{credentialId}")
    public String deleteCredential(@PathVariable Integer credentialId) {
        credentialService.deleteCredential(credentialId);
        return "redirect:/home";
    }
}