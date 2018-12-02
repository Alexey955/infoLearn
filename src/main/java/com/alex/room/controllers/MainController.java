package com.alex.room.controllers;

import com.alex.room.domain.TableInfo;
import com.alex.room.enums.Periods;
import com.alex.room.repos.TableInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

@Controller
public class MainController {

    @Autowired
    private TableInfoRepo tableInfoRepo;

    @GetMapping("/")
    public String startPage(Map<String, Object> model) {
        return "mainPage";
    }

    @PostMapping("/addNew")
    public String addNewElem(Map<String, Object> model) {
        return "addNewElemPage";
    }

    @PostMapping("/wallAftAdd")
    public String wallAftAdd(@RequestParam Integer numberField, @RequestParam Integer thesesField,
                             @RequestParam Integer mistakesField, Map<String, Object> model) {
        Date nowadays = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(nowadays);
        calendar.add(Calendar.DAY_OF_MONTH, Periods.Two.getDayAmount());
        nowadays = calendar.getTime();
        String strNextDateRep = simpleDateFormat.format(nowadays);

        int percentMistakes = (mistakesField*100)/thesesField;

        TableInfo tableInfo = new TableInfo(numberField, thesesField, percentMistakes, strNextDateRep, 1);

        tableInfoRepo.save(tableInfo);

        return "wallpaperPage";
    }

    @PostMapping("/again")
    public String againFun() {
        return "wallpaperPage";
    }
}
