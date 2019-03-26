package com.alex.room.service;

import com.alex.room.domain.User;
import com.alex.room.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Service
public class AdminService {

    @Autowired
    private UserRepo userRepo;

    public String showUserList(Model model){

        List<User> userList = userRepo.findAll();

        for(int i = 0; i < userList.size(); i++) {
            if(userList.get(i).getRoles().iterator().next().toString().equals("ADMIN")) {
                userList.remove(i);
            }
        }
        model.addAttribute("userList", userList);
        return "userListPage";
    }
}
