package com.alex.room.controllers;

import com.alex.room.domain.User;
import com.alex.room.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class RegistrationController {

    @Autowired
    private UserService userService;

    @GetMapping("/registration")
    public String registration() {
        return "registrationPage";
    }

    @PostMapping("/registration")
    public String addUser(@Valid User user, BindingResult bindingResult, Model model, String radioRole) {

        if(bindingResult.hasErrors()) {
            ControllerUtils.addErrorToModelIfBindingResultError(bindingResult, model);

            return "registrationPage";
        }

        if (!userService.addUser(user, radioRole)) {
            model.addAttribute("usernameError", "User " + user.getUsername() +" exists.");
            return "registrationPage";
        }

        return "redirect:/login";
    }
}
