package com.alex.room.controllers;

import com.alex.room.repos.TableInfoRepo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
public class MainController {

    private TableInfoRepo tableInfoRepo;

    @GetMapping("/")
    public String startPage(Map<String, Object> model) {
        return "test";
    }
}
