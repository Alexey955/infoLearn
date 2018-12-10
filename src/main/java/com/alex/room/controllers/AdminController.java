package com.alex.room.controllers;

import com.alex.room.domain.TableInfo;
import com.alex.room.domain.User;
import com.alex.room.repos.TableInfoRepo;
import com.alex.room.repos.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@PreAuthorize("hasAuthority('ADMIN')")
@SessionAttributes("pickedUser")
public class AdminController {
    @Autowired
    private UserRepo userRepo;

    @Autowired
    private TableInfoRepo tableInfoRepo;

    @GetMapping("/adminMain")
    public String adminMain() {
        return "adminMainPage";
    }

    @GetMapping("/showUserList")
    public String showUserList(Map<String, Object> model) {
        List<User> userList = userRepo.findAll();
        model.put("userList", userList);
        return "userListPage";
    }

    @GetMapping("/pickOperatUser/{user}")
    public String pickOperatUser(@PathVariable User user, Map<String, Object> model) {

        model.put("pickedUser", user);
        return "pickOperatUserPage";
    }

    @GetMapping("/showFullAvgAccuracy")
    public String showFullAvgAccuracy(Map<String, Object> model) {

        int fullAvgAccuracy = tableInfoRepo.countFullAvgPercentFalse();
        model.put("avgAccuracy", fullAvgAccuracy);
        return "AvgAccuracyPage";
    }

    @GetMapping("/showFullStageAccuracy")
    public String showFullStageAccuracy(Map<String, Object> model) {

        model.put("GroupByStage", tableInfoRepo.findFullTableInfoCount());
        return "StageAccuracyPage";
    }

    @GetMapping("/deleteUserOrNot")
    public String deleteUserOrNot(@ModelAttribute("pickedUser") User user) {

        return "deleteUserOrNotPage";
    }

    @PostMapping("/deteteTheUser")
    public String deteteTheUser(@ModelAttribute("pickedUser") User user) {

        userRepo.deleteById(user.getId());

        List<TableInfo> tableInfo = tableInfoRepo.findByUsername(user.getUsername());
        for (TableInfo x: tableInfo) {
            tableInfoRepo.deleteById(x.getId());
        }

        return "wallpaperPage";
    }
}
