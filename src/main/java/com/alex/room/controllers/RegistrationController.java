package com.alex.room.controllers;

import com.alex.room.domain.User;
import com.alex.room.enums.Roles;
import com.alex.room.enums.UserActivity;
import com.alex.room.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Collections;
import java.util.Map;

@Controller
public class RegistrationController {

    @Autowired
    private UserRepo userRepo;

    @GetMapping("/registration")
    public String registration() {
        return "registrationPage";
    }

    @PostMapping("/registration")
    public String addUser(User user, Map<String, Object> model) {
        User userfFromDb = userRepo.findByUsername(user.getUsername());
        if(userfFromDb != null) {
            model.put("UserExistMess", "User with name" + user.getUsername() + " exists");
            return "registrationPage";
        }

        user.setUserActivity(UserActivity.ACTIVE.getAmountDays());
        user.setRoles(Collections.singleton(Roles.USER));
        userRepo.save(user);
        return "redirect:/login";
    }
}
