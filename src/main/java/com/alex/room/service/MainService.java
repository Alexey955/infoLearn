package com.alex.room.service;

import com.alex.room.controllers.ControllerUtils;
import com.alex.room.domain.TableInfo;
import com.alex.room.domain.User;
import com.alex.room.enums.Periods;
import com.alex.room.repos.TableInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
@SessionAttributes("pickedUser")
public class MainService {

    @Autowired
    private TableInfoRepo tableInfoRepo;

    public String pickMainPage(User user, Model model){

        String mainPage = null;

        switch (user.getRoles().iterator().next().toString()){
            case "USER":
                model.addAttribute("pickedUser", user);
                mainPage = "userMainPage"; break;

            case "ADMIN":
                mainPage = "adminMainPage"; break;
        }
        return mainPage;
    }

    public String addElement(User user, TableInfo tableInfo, BindingResult bindingResult, Model model){

        TableInfo tableInfoValid = tableInfoRepo.findByNumberAndUser(tableInfo.getNumber(), user);

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
                percentMistakes, strDatePriorRep, strDateNextRep, 1);

        tableInfoTwo.setUser(user);
        tableInfoRepo.save(tableInfoTwo);

        return "wallpaperPage";
    }

    public String deleteOneElement(User user, TableInfo tableInfo, Model model){

        TableInfo tableInfoValid = tableInfoRepo.findByNumberAndUser(tableInfo.getNumber(), user);

        if(tableInfoValid == null) {
            ControllerUtils.addErrorToModelIfNumberDoesntExist(null, tableInfo.getNumber(), model);

            return "deleteElemPage";
        }

        int idTableInfo = tableInfoRepo.findTableInfoIdByNumberAndUser(tableInfo.getNumber(), user);
        tableInfoRepo.deleteById(idTableInfo);

        return "wallpaperPage";
    }

    public String pickDelOneOrSeveral(String radioDel){
        String oneOrSeveral = null;

        switch (radioDel){
            case "one": oneOrSeveral = "deleteElemPage"; break;
            case "several": oneOrSeveral = "deleteSeveralElemPage"; break;
        }
        return oneOrSeveral;
    }

    public String deleteSeveralElements(int numberFieldFrom, int numberFieldTo, User user, Model model){
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
            idTableInfo = tableInfoRepo.findTableInfoIdByNumberAndUser(i, user);

            if (idTableInfo == null) {
                continue;
            }
            tableInfoRepo.deleteById(idTableInfo);
        }
        return "wallpaperPage";
    }

    public String showListRep(Model model, User user){

        LocalDate nowadays = LocalDate.now();
        List<TableInfo> tableInfoIterableAll = tableInfoRepo.findByUserAndTypeDateNextRepIsLessThanEqual(user, nowadays);

        model.addAttribute("listRep", tableInfoIterableAll);
        return "repeatListPage";
    }

    public String pickElemRepeat(Model model, User user){
        LocalDate nowadays = LocalDate.now();

        List<TableInfo> tableInfoList = tableInfoRepo.findByUserAndTypeDateNextRepIsLessThanEqual(user, nowadays);

        model.addAttribute("listRep", tableInfoList);
        return "pickElemRepeatPage";
    }

    public String repeatElement(User user, TableInfo tableInfo, BindingResult bindingResult, Model model){

        TableInfo tableInfoOld = tableInfoRepo.findByNumberAndUser(tableInfo.getNumber(), user);

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

        Periods[] periods = Periods.values();
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

    public String getEditElemPage(User user, TableInfo tableInfo, BindingResult bindingResult, Model model){

        TableInfo strForEdit = tableInfoRepo.findByNumberAndUser(tableInfo.getNumber(), user);

        if(strForEdit == null) {
            ControllerUtils.addErrorToModelIfNumberDoesntExist(null, tableInfo.getNumber(), model);

            return "pickElemForEditPage";
        }

        model.addAttribute("qualityFails", strForEdit.getAmountMistakes());
        model.addAttribute("strForEdit", strForEdit);
        model.addAttribute("numberFromTable", strForEdit.getNumber());
        return "editElemPage";
    }

    public String editElement(User user, TableInfo tableInfo, BindingResult bindingResult,
                              Model model, int numberFromTable){
        if(bindingResult.hasErrors() || (tableInfo.getAmountElem() < tableInfo.getAmountMistakes())) {
            ControllerUtils.addErrorToModelIfBindingResultError(bindingResult, model);
            ControllerUtils.addErrorIfMistakesTooLarge(tableInfo.getAmountElem(), tableInfo, model);

            return "editElemPage";
        }

        try{
            LocalDate.parse(tableInfo.getDatePriorRep(), DateTimeFormatter.ofPattern("dd.MM.yyyy"));

        } catch (DateTimeParseException exc) {
            model.addAttribute("datePriorRepError", "Need dd.mm.yyyy format.");
            return "editElemPage";
        }

        try{
            LocalDate.parse(tableInfo.getDateNextRep(), DateTimeFormatter.ofPattern("dd.MM.yyyy"));

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

        TableInfo tableInfoOld = tableInfoRepo.findByNumberAndUser(numberFromTable, user);
        tableInfoOld.setAmountElem(tableInfo.getAmountElem());
        tableInfoOld.setAmountMistakes(tableInfo.getAmountMistakes());
        tableInfoOld.setPercentFalse(percentFalse);
        tableInfoOld.setDatePriorRep(tableInfo.getDatePriorRep());
        tableInfoOld.setDateNextRep(tableInfo.getDateNextRep());
        tableInfoOld.setTypeDatePriorRep(LocalDate.parse(tableInfo.getDatePriorRep(), DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        tableInfoOld.setTypeDateNextRep(LocalDate.parse(tableInfo.getDateNextRep(), DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        tableInfoOld.setStage(tableInfo.getStage());

        tableInfoRepo.save(tableInfoOld);
        return "wallpaperPage";
    }
}
