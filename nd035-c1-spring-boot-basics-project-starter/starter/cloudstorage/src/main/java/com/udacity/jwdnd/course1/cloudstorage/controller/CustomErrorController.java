package com.udacity.jwdnd.course1.cloudstorage.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object statusObj = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        if (statusObj != null) {
            Integer statusCode = Integer.valueOf(statusObj.toString());
            model.addAttribute("status", statusCode);

            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                model.addAttribute("error", "Page Not Found");
                model.addAttribute("message", "Sorry, the page you requested does not exist.");
                return "error";
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                model.addAttribute("error", "Internal Server Error");
                model.addAttribute("message", "An unexpected error occurred.");
                return "error";
            }
        }
        model.addAttribute("error", "Unknown error");
        model.addAttribute("message", "Something went wrong.");
        return "error";
    }
}