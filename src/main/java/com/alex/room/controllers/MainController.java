package com.alex.room.controllers;

import com.alex.room.domain.TableInfo;
import com.alex.room.domain.User;
import com.alex.room.enums.Periods;
import com.alex.room.repos.TableInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;

@Controller
@SessionAttributes({"strForEdit", "pickedUser", "qualityFails", "numberFromTable", "listRep"})
public class MainController {

    @Autowired
    private TableInfoRepo tableInfoRepo;

    @GetMapping("/")
    public String decideUserOrAdmin(@AuthenticationPrincipal User user, Model model) {

        if(user.getRoles().iterator().next().toString().equals("ADMIN")) {
            return "adminMainPage";
        } else {
            model.addAttribute("pickedUser", user);
            return "userMainPage";
        }
    }

    @GetMapping("/userMain")
    public String userMain() { return "userMainPage"; }

    @GetMapping("/addNew")
    public String addNewElem() {
        return "addNewElemPage";
    }

    @PostMapping("/wallAftAdd")
    public String wallAftAdd(@AuthenticationPrincipal User user, @Valid TableInfo tableInfo, BindingResult bindingResult,
                             Model model) throws ParseException {

        TableInfo tableInfoValid = tableInfoRepo.findByNumberAndUsername(tableInfo.getNumber(), user.getUsername());
        if(bindingResult.hasErrors() || tableInfoValid != null || (tableInfo.getAmountElem() < tableInfo.getAmountMistakes())) {
            ControllerUtils.addErrorToModelIfBindingResultError(bindingResult, model);
            ControllerUtils.addErrorToModelIfNumberExists(tableInfoValid, model);
            ControllerUtils.addErrorIfMistakesTooLarge(tableInfo.getAmountElem(), tableInfo, model);

            return "addNewElemPage";
        }

        LocalDate datePriorRep = LocalDate.now();
        LocalDate dateNextRep = datePriorRep.plusDays(Periods.TWO.getDayAmount());

        String strDatePriorRep = datePriorRep.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        String strDateNextRep = dateNextRep.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

        int percentMistakes = (tableInfo.getAmountMistakes() * 100) / tableInfo.getAmountElem();

        TableInfo tableInfoTwo = new TableInfo(tableInfo.getNumber(), tableInfo.getAmountElem(), tableInfo.getAmountMistakes(),
                    percentMistakes, strDatePriorRep, strDateNextRep, 1, user.getUsername());
        tableInfoRepo.save(tableInfoTwo);

        return "wallpaperPage";
    }

    @GetMapping("/delElem")
    public String delAnElem() { return "deleteElemPage"; }

    @PostMapping("/wallAftDelOne")
    public String wallAftOneDel(@AuthenticationPrincipal User user, @Valid TableInfo tableInfo, BindingResult bindingResult, Model model) {

        TableInfo tableInfoValid = tableInfoRepo.findByNumberAndUsername(tableInfo.getNumber(), user.getUsername());
        if(tableInfoValid == null) {
            ControllerUtils.addErrorToModelIfNumberDoesntExist(tableInfoValid, tableInfo.getNumber(), model);

            return "deleteElemPage";
        }

        int idTableInfo = tableInfoRepo.findTableInfoIdByNumberAndUsername(tableInfo.getNumber(), user.getUsername());
        tableInfoRepo.deleteById(idTableInfo);

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
    public String delSeveralElements() { return "deleteSeveralElemPage"; }

    @PostMapping("/wallAftDelSeveral")
    public String wallAftDelSeveral(@RequestParam int numberFieldFrom, @RequestParam int numberFieldTo,
                                    @AuthenticationPrincipal User user, Model model) {

        if(numberFieldFrom <= 0 || numberFieldFrom >= 1001 || numberFieldTo <= 0 || numberFieldTo >= 1001 || numberFieldFrom > numberFieldTo) {

            if (numberFieldFrom <= 0 || numberFieldFrom >= 1001 || numberFieldTo <= 0 || numberFieldTo >= 1001) {
                model.addAttribute("errorFromOrTo", "Select numbers from 1 to 1000.");
            }

            if (numberFieldFrom > numberFieldTo) {
                model.addAttribute("errorFromOrTo", "To is less than from.");
            }

            return "deleteSeveralElemPage";
        }

        Integer idTableInfo;
        for(int i = numberFieldFrom; i <= numberFieldTo; i++) {
            idTableInfo = tableInfoRepo.findTableInfoIdByNumberAndUsername(i, user.getUsername());

            if (idTableInfo == null) {
                continue;
            }
            tableInfoRepo.deleteById(idTableInfo);
        }
        return "wallpaperPage";
    }

    @GetMapping("/listRep")
    public String showListRep(Map<String, Object> model, @AuthenticationPrincipal User user) {

        LocalDate nowadays = LocalDate.now();

        List<TableInfo> tableInfoIterableAll = tableInfoRepo.findByUsernameAndTypeDateNextRepIsLessThanEqual(user.getUsername(), nowadays);

        model.put("listRep", tableInfoIterableAll);
        return "repeatListPage";
    }

    @GetMapping("/pickElemRepeat")
    public String pickElemRepeat(Map<String, Object> model, @AuthenticationPrincipal User user) {

        LocalDate nowadays = LocalDate.now();

        List<TableInfo> tableInfoList = tableInfoRepo.findByUsernameAndTypeDateNextRepIsLessThanEqual(user.getUsername(), nowadays);

        model.put("listRep", tableInfoList);
        return "pickElemRepeatPage";
    }

    @PostMapping("/wallAftRepeat")
    public String wallAftRepeat(@AuthenticationPrincipal User user, @Valid TableInfo tableInfo,
                                       BindingResult bindingResult, Model model) {

        TableInfo tableInfoOld = tableInfoRepo.findByNumberAndUsername(tableInfo.getNumber(), user.getUsername());

        int amountElements;
        if(tableInfoOld != null) {
            amountElements = tableInfoOld.getAmountElem();
        } else {
            amountElements = 1000;
        }

        if(bindingResult.hasErrors() || tableInfoOld == null || (tableInfoOld.getAmountElem() < tableInfo.getAmountMistakes())) {
            ControllerUtils.addErrorToModelIfBindingResultError(bindingResult, model);
            ControllerUtils.addErrorToModelIfNumberDoesntExist(tableInfoOld, tableInfo.getNumber(), model);
            ControllerUtils.addErrorIfMistakesTooLarge(amountElements, tableInfo, model);

            return "pickElemRepeatPage";
        }

        int percentFalse = (tableInfo.getAmountMistakes() * 100) / tableInfoOld.getAmountElem();

        Periods periods[] = Periods.values();
        Integer amountDaysToAdd = periods[tableInfoOld.getStage() + 1].getDayAmount();

        LocalDate datePriorRep = LocalDate.now();
        LocalDate dateNextRep = LocalDate.now().plusDays(amountDaysToAdd);

        String strDatePriorRep = datePriorRep.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        String strDateNextRep = dateNextRep.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));

        tableInfoOld.setAmountMistakes(tableInfo.getAmountMistakes());
        tableInfoOld.setPercentFalse(percentFalse);
        tableInfoOld.setDatePriorRep(strDatePriorRep);
        tableInfoOld.setDateNextRep(strDateNextRep);
        tableInfoOld.setStage(tableInfoOld.getStage() + 1);

        tableInfoRepo.save(tableInfoOld);
        return "wallpaperPage";
    }

    @GetMapping("/pickElemForEdit")
    public String pickElemForEdit() { return "pickElemForEditPage"; }

    @GetMapping("/editElem")
    public String getEditElemPage(@AuthenticationPrincipal User user, @Valid TableInfo tableInfo,
                                  BindingResult bindingResult, Model model) {

        TableInfo strForEdit = tableInfoRepo.findByNumberAndUsername(tableInfo.getNumber(), user.getUsername());

        if(strForEdit == null) {
            ControllerUtils.addErrorToModelIfNumberDoesntExist(strForEdit, tableInfo.getNumber(), model);

            return "pickElemForEditPage";
        }

        model.addAttribute("qualityFails", strForEdit.getAmountMistakes());
        model.addAttribute("strForEdit", strForEdit);
        model.addAttribute("numberFromTable", strForEdit.getNumber());
        return "editElemPage";
    }

    @PostMapping("/wallAftEdit")
    public String wallAftEdit(@AuthenticationPrincipal User user, @Valid TableInfo tableInfo, BindingResult bindingResult,
                              Model model, @ModelAttribute("numberFromTable") int numberFromTable) {

        if(bindingResult.hasErrors() || (tableInfo.getAmountElem() < tableInfo.getAmountMistakes())) {
            ControllerUtils.addErrorToModelIfBindingResultError(bindingResult, model);
            ControllerUtils.addErrorIfMistakesTooLarge(tableInfo.getAmountElem(), tableInfo, model);

            return "editElemPage";
        }

        LocalDate dateForTestCorrect;
        try{
            dateForTestCorrect = LocalDate.parse(tableInfo.getDatePriorRep(), DateTimeFormatter.ofPattern("dd.MM.yyyy"));

           } catch (DateTimeParseException exc) {
               model.addAttribute("datePriorRepError", "Need dd.mm.yyyy format.");
               return "editElemPage";
           }

        try{
            dateForTestCorrect = LocalDate.parse(tableInfo.getDateNextRep(), DateTimeFormatter.ofPattern("dd.MM.yyyy"));

           } catch (DateTimeParseException exc) {

            model.addAttribute("dateNextRepError", "Need dd.mm.yyyy format.");
            return "editElemPage";
            }

        LocalDate nowadays = LocalDate.now();
        LocalDate priorDateForTestCorrect = LocalDate.parse(tableInfo.getDatePriorRep(), DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        LocalDate nextDateForTestCorrect = LocalDate.parse(tableInfo.getDateNextRep(), DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        if(priorDateForTestCorrect.isAfter(nowadays) || nextDateForTestCorrect.isBefore(nowadays)) {
            ControllerUtils.addErrorIfDateLessThanToday(nextDateForTestCorrect, nowadays, model);
            ControllerUtils.addErrorIfDateMoreThanToday(priorDateForTestCorrect, nowadays, model);

            return "editElemPage";
            }

        int percentFalse = (tableInfo.getAmountMistakes() * 100) / tableInfo.getAmountElem();

        TableInfo tableInfoOld = tableInfoRepo.findByNumberAndUsername(numberFromTable, user.getUsername());
        tableInfoOld.setAmountElem(tableInfo.getAmountElem());
        tableInfoOld.setAmountMistakes(tableInfo.getAmountMistakes());
        tableInfoOld.setPercentFalse(percentFalse);
        tableInfoOld.setDatePriorRep(tableInfo.getDatePriorRep());
        tableInfoOld.setDateNextRep(tableInfo.getDateNextRep());
        tableInfoOld.setStage(tableInfo.getStage());

        tableInfoRepo.save(tableInfoOld);
        return "wallpaperPage";
    }

    @GetMapping("/showIntro")
    public String showIntro() { return "showIntroPage"; }
}
