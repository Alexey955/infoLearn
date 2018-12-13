package com.alex.room.controllers;

import com.alex.room.domain.User;
import com.alex.room.enums.Roles;
import com.alex.room.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;
import java.util.Collections;

@Controller
public class RegistrationController {

    @Autowired
    private UserRepo userRepo;

    @GetMapping("/registration")
    public String registration() {
        return "registrationPage";
    }

    @PostMapping("/registration")
    public String addUser(@Valid User user, BindingResult bindingResult, Model model) {

        User userValid = userRepo.findByUsername(user.getUsername());
        if(bindingResult.hasErrors() || userValid != null) {
            ControllerUtils.addErrorToModelIfBindingResultError(bindingResult, model);
            ControllerUtils.addErrorToModelIfUserExists(userValid, model);

            return "registrationPage";
        }
        user.setRoles(Collections.singleton(Roles.USER));
        userRepo.save(user);

        return "redirect:/login";
    }
}
