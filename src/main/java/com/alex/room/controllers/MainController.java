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
import java.util.*;

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
        Date datePriorRep = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");

        String strDatePriorRep = simpleDateFormat.format(datePriorRep);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(nowadays);
        calendar.add(Calendar.DAY_OF_MONTH, Periods.Two.getDayAmount());
        nowadays = calendar.getTime();
        String strNextDateRep = simpleDateFormat.format(nowadays);

        int percentMistakes = (mistakesField*100)/thesesField;

        TableInfo tableInfo = new TableInfo(numberField, thesesField, percentMistakes, strDatePriorRep, strNextDateRep, 1);
        tableInfoRepo.save(tableInfo);

        return "wallpaperPage";
    }

    @PostMapping("/delElem")
    public String delAnElem() {
        return "deleteElemPage";
    }

    @PostMapping("/wallAftDelOne")
    public String wallAftOneDel(@RequestParam Integer numberField) {
        int num = tableInfoRepo.findByNumber(numberField).getId();
        tableInfoRepo.deleteById(num);
        return "wallpaperPage";
    }

    @PostMapping("/delOneOrSeveral")
    public String pickDelOneOrSeveral(@RequestParam String radioDel) {
        if(radioDel.equals("one")) {
            return "deleteElemPage";
        } else {
            return "deleteSeveralElemPage";
        }
    }

    @PostMapping("/delSeveralElem")
    public String delSeveralElements() {
        return "deleteSeveralElemPage";
    }
    @PostMapping("/wallAftDelSeveral")
    public String wallAftDelSeveral(@RequestParam Integer numberFieldFrom, @RequestParam Integer numberFieldInto) {
        int num;
        for(int i = numberFieldFrom; i != numberFieldInto +1; i++) {
            num = tableInfoRepo.findByNumber(i).getId();
            tableInfoRepo.deleteById(num);
        }
        return "wallpaperPage";
    }

    @GetMapping("/listRep")
    public String showListRep(Map<String, Object> model) {
        Date nowadays = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String strNowadaysSimple = simpleDateFormat.format(nowadays);
        Iterable<TableInfo> tableInfoList = tableInfoRepo.findByDateNextRep(strNowadaysSimple);

        model.put("listRep", tableInfoList);
        return "repeatListPage";
    }

    @GetMapping("/pickElemRepeat")
    public String pickElemRepeat(Map<String, Object> model) {
        Date nowadays = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String strNowadaysSimple = simpleDateFormat.format(nowadays);
        Iterable<TableInfo> tableInfoList = tableInfoRepo.findByDateNextRep(strNowadaysSimple);

        model.put("listRep", tableInfoList);
        return "pickElemRepeatPage";
    }

    @PostMapping("/wallAftRepeatSeveral")
    public String wallAftRepeatSeveral(@RequestParam Integer inputNum, @RequestParam Integer inputMistakes) {
        TableInfo tableInfoOld = tableInfoRepo.findByNumber(inputNum);

        int percentFalse = (inputMistakes * 100)/tableInfoOld.getAmountElem();

        Date nowadays = new Date();
        Date datePriorRep = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String strDatePriorRep = simpleDateFormat.format(datePriorRep);

        Periods periods[] = Periods.values();
        Integer amountDaysToAdd = periods[tableInfoOld.getStage() + 1].getDayAmount();

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(nowadays);
        calendar.add(Calendar.DAY_OF_MONTH, amountDaysToAdd);
        nowadays = calendar.getTime();
        String strNextDateRep = simpleDateFormat.format(nowadays);


        TableInfo tableInfoNew = new TableInfo(tableInfoOld.getNumber(), tableInfoOld.getAmountElem(),
                percentFalse, strDatePriorRep, strNextDateRep,  tableInfoOld.getStage() + 1);

        int num = tableInfoRepo.findByNumber(inputNum).getId();
        tableInfoRepo.deleteById(num);
        tableInfoRepo.save(tableInfoNew);
        return "wallpaperPage";
    }
}
