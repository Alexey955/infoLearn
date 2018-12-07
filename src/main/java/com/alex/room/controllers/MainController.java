package com.alex.room.controllers;

import com.alex.room.domain.TableInfo;
import com.alex.room.domain.User;
import com.alex.room.enums.Periods;
import com.alex.room.repos.TableInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@SessionAttributes("numberElement")
public class MainController {

    @Autowired
    private TableInfoRepo tableInfoRepo;

    @GetMapping("/")
    public String startPage(Map<String, Object> model) {
        return "mainPage";
    }

    @GetMapping("/addNew")
    public String addNewElem(Map<String, Object> model) {
        return "addNewElemPage";
    }

    @PostMapping("/wallAftAdd")
    public String wallAftAdd(@AuthenticationPrincipal User user, @RequestParam Integer numberField,
                             @RequestParam Integer thesesField, @RequestParam Integer mistakesField,
                             Map<String, Object> model) throws ParseException {

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

        TableInfo tableInfo = new TableInfo(numberField, thesesField, percentMistakes,
                strDatePriorRep, strNextDateRep, 1, user.getUsername());
        tableInfoRepo.save(tableInfo);

        return "wallpaperPage";
    }

    @GetMapping("/delElem")
    public String delAnElem() {
        return "deleteElemPage";
    }

    @PostMapping("/wallAftDelOne")
    public String wallAftOneDel(@RequestParam Integer numberField) {
        int numTableInfo = tableInfoRepo.findByNumber(numberField).getId();
        tableInfoRepo.deleteById(numTableInfo);
        return "wallpaperPage";
    }

    @GetMapping("/delOneOrSeveral")
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
        int numTableInfo;
        for(int i = numberFieldFrom; i != numberFieldInto +1; i++) {
            numTableInfo = tableInfoRepo.findByNumber(i).getId();
            tableInfoRepo.deleteById(numTableInfo);
        }
        return "wallpaperPage";
    }

    @GetMapping("/listRep")
    public String showListRep(Map<String, Object> model) {
        Date nowadays = new Date();
        Iterable<TableInfo> tableInfoIterableAll = tableInfoRepo.findByTypeDateNextRepIsLessThanEqual(nowadays);

        model.put("listRep", tableInfoIterableAll);
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

    @PostMapping("/wallAftRepeat")
    public String wallAftRepeatSeveral(@RequestParam Integer inputNum, @RequestParam Integer inputMistakes) throws ParseException {
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
                percentFalse, strDatePriorRep, strNextDateRep,  tableInfoOld.getStage() + 1, tableInfoOld.getUsername());

        int numTableInfo = tableInfoRepo.findByNumber(inputNum).getId();
        tableInfoRepo.deleteById(numTableInfo);
        tableInfoRepo.save(tableInfoNew);

        return "wallpaperPage";
    }

    @GetMapping("/pickElemForEdit")
    public String pickElemForEdit() {
        return "pickElemForEditPage";
    }

    @GetMapping("/editElem")
    public String getEditElemPage(@RequestParam Integer numberField, Map<String, Object> model) {
        TableInfo tableInfo = tableInfoRepo.findByNumber(numberField);
        model.put("DateForPrompt", tableInfo);

        Integer qualityFails = (tableInfo.getPercentFalse()/tableInfo.getAmountElem());
        model.put("qualityFails", qualityFails);
        model.put("numberElement", numberField);
        return "editElemPage";
    }

    @PostMapping("/wallAftEdit")
    public String wallAftEdit(@RequestParam Integer thesesField, @RequestParam String priorDateField,
                              @RequestParam Integer mistakesField, @RequestParam String nextRepDateField,
                              @RequestParam Integer inpStage, @ModelAttribute("numberElement") Integer numberField) throws ParseException {
        int percentFalse = (mistakesField*100)/thesesField;
        int idTableInfo = tableInfoRepo.findByNumber(numberField).getId();
        String username = tableInfoRepo.findById(idTableInfo).get().getUsername();
        TableInfo tableInfo = new TableInfo(numberField, thesesField, percentFalse,
                priorDateField, nextRepDateField, inpStage, username);

        tableInfoRepo.deleteById(idTableInfo);

        tableInfoRepo.save(tableInfo);
        return "wallpaperPage";
    }
}
