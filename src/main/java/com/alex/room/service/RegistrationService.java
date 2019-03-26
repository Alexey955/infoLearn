package com.alex.room.service;

import com.alex.room.controllers.ControllerUtils;
import com.alex.room.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

@Service
public class RegistrationService {

    @Autowired
    private UserService userService;

    public String addUser(User user, BindingResult bindingResult, Model model, String radioRole){

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
