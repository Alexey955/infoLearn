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
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@SessionAttributes({"strForEdit", "pickedUser", "qualityFails", "numberFromTable", "listRep"})
public class MainController {

    @Autowired
    private TableInfoRepo tableInfoRepo;

    @GetMapping("/")
    public String decideUserOrAdmin(@AuthenticationPrincipal User user, Map<String, Object> model) {

        if(user.getRoles().iterator().next().toString().equals("ADMIN")) {
            return "adminMainPage";
        } else {
            model.put("pickedUser", user);
            return "userMainPage";
        }
    }

    @GetMapping("/userMain")
    public String userMain(Map<String, Object> model) {

        return "userMainPage";
    }

    @GetMapping("/addNew")
    public String addNewElem() {
        return "addNewElemPage";
    }

    @PostMapping("/wallAftAdd")
    public String wallAftAdd(@AuthenticationPrincipal User user, @Valid TableInfo tableInfo, BindingResult bindingResult,
                             Model model) throws ParseException {

        if(bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errorsMap);

            return "addNewElemPage";

        } else {

            TableInfo numberFromDb = tableInfoRepo.findByNumberAndUsername(tableInfo.getNumber(), user.getUsername());

            if(numberFromDb != null) {
                model.addAttribute("numberError", "number " + numberFromDb.getNumber() + " exists.");

                return "addNewElemPage";
            }

            if (tableInfo.getAmountElem() < tableInfo.getAmountMistakes()) {
                model.addAttribute("amountMistakesError", "Amount of mistakes is too large.");

                return "addNewElemPage";
            }

            Date nowadays = new Date();
            Date datePriorRep = new Date();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");

            String strDatePriorRep = simpleDateFormat.format(datePriorRep);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(nowadays);
            calendar.add(Calendar.DAY_OF_MONTH, Periods.Two.getDayAmount());
            nowadays = calendar.getTime();
            String strNextDateRep = simpleDateFormat.format(nowadays);

            int percentMistakes = (tableInfo.getAmountMistakes() * 100) / tableInfo.getAmountElem();

            TableInfo tableInfoTwo = new TableInfo(tableInfo.getNumber(), tableInfo.getAmountElem(), tableInfo.getAmountMistakes(),
                    percentMistakes, strDatePriorRep, strNextDateRep, 1, user.getUsername());
            tableInfoRepo.save(tableInfoTwo);

            return "wallpaperPage";
        }
    }

    @GetMapping("/delElem")
    public String delAnElem() {
        return "deleteElemPage";
    }

    @PostMapping("/wallAftDelOne")
    public String wallAftOneDel(@AuthenticationPrincipal User user, @Valid TableInfo tableInfo, BindingResult bindingResult, Model model) {

        if(bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errorsMap);

            return "deleteElemPage";

        } else {

            TableInfo numberFromDb = tableInfoRepo.findByNumberAndUsername(tableInfo.getNumber(), user.getUsername());

            if (numberFromDb == null) {
                model.addAttribute("errorNumberExists", "number " + tableInfo.getNumber() + " doesn't exist.");

                return "deleteElemPage";
            }

            int idTableInfo = tableInfoRepo.findTableInfoIdByNumberAndUsername(tableInfo.getNumber(), user.getUsername());
            tableInfoRepo.deleteById(idTableInfo);

            return "wallpaperPage";
        }
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
    public String wallAftDelSeveral(@RequestParam int numberFieldFrom, @RequestParam int numberFieldTo,
                                    @AuthenticationPrincipal User user, Model model) {
        if(numberFieldFrom <= 0 || numberFieldFrom >= 1001 || numberFieldTo <= 0 || numberFieldTo >= 1001) {
            model.addAttribute("errorFromOrTo", "Select numbers from 1 to 1000.");
            return "deleteSeveralElemPage";
        } else if(numberFieldFrom > numberFieldTo) {
            model.addAttribute("errorFromOrTo", "To is less than from.");
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
        Date nowadays = new Date();
        List<TableInfo> tableInfoIterableAll = tableInfoRepo.findByUsernameAndTypeDateNextRepIsLessThanEqual(user.getUsername(), nowadays);

        model.put("listRep", tableInfoIterableAll);
        return "repeatListPage";
    }

    @GetMapping("/pickElemRepeat")
    public String pickElemRepeat(Map<String, Object> model, @AuthenticationPrincipal User user) {
        Date nowadays = new Date();
        /*SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        String strNowadaysSimple = simpleDateFormat.format(nowadays);*/
        List<TableInfo> tableInfoList = tableInfoRepo.findByUsernameAndTypeDateNextRepIsLessThanEqual(user.getUsername(), nowadays);

        model.put("listRep", tableInfoList);
        return "pickElemRepeatPage";
    }

    @PostMapping("/wallAftRepeat")
    public String wallAftRepeatSeveral(/*@RequestParam Integer inputNum, @RequestParam Integer inputMistakes,*/
                                       @AuthenticationPrincipal User user, @Valid TableInfo tableInfo,
                                       BindingResult bindingResult, Model model) throws ParseException {

        if(bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errorsMap);

            return "pickElemRepeatPage";

        } else {

            TableInfo numberFromDb = tableInfoRepo.findByNumberAndUsername(tableInfo.getNumber(), user.getUsername());

            if (numberFromDb == null) {
                model.addAttribute("numberError", "number " + tableInfo.getNumber() + " doesn't exist.");

                return "pickElemRepeatPage";
            }
            int amountElemFromDB = tableInfoRepo.findAmountElemByUsernameAndNumber(user.getUsername(), tableInfo.getNumber());
            if (amountElemFromDB < tableInfo.getAmountMistakes()) {
                model.addAttribute("amountMistakesError", "Amount of mistakes is too large.");

                return "pickElemRepeatPage";
            }

            TableInfo tableInfoOld = tableInfoRepo.findByNumberAndUsername(tableInfo.getNumber(), user.getUsername());

            int percentFalse = (tableInfo.getAmountMistakes() * 100) / tableInfoOld.getAmountElem();

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

            TableInfo tableInfoNew = new TableInfo(tableInfoOld.getNumber(), tableInfoOld.getAmountElem(), tableInfo.getAmountMistakes(),
                    percentFalse, strDatePriorRep, strNextDateRep, tableInfoOld.getStage() + 1, tableInfoOld.getUsername());

            int idTableInfo = tableInfoRepo.findTableInfoIdByNumberAndUsername(tableInfo.getNumber(), user.getUsername());
            tableInfoRepo.deleteById(idTableInfo);
            tableInfoRepo.save(tableInfoNew);

            return "wallpaperPage";
        }
    }

    @GetMapping("/pickElemForEdit")
    public String pickElemForEdit() {
        return "pickElemForEditPage";
    }

    @GetMapping("/editElem")
    public String getEditElemPage(@AuthenticationPrincipal User user, @Valid TableInfo tableInfo,
                                  BindingResult bindingResult, Model model) {

        if(bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errorsMap);

            return "pickElemForEditPage";

        } else {

            TableInfo numberFromDb = tableInfoRepo.findByNumberAndUsername(tableInfo.getNumber(), user.getUsername());

            if (numberFromDb == null) {
                model.addAttribute("numberError", "number " + tableInfo.getNumber() + " doesn't exist.");

                return "pickElemForEditPage";
            }

            TableInfo strForEdit = tableInfoRepo.findByNumberAndUsername(tableInfo.getNumber(), user.getUsername());

            model.addAttribute("qualityFails", strForEdit.getAmountMistakes());
            model.addAttribute("strForEdit", strForEdit);
            model.addAttribute("numberFromTable", strForEdit.getNumber());
            return "editElemPage";
        }
    }

    @PostMapping("/wallAftEdit")
    public String wallAftEdit(@AuthenticationPrincipal User user, @Valid TableInfo tableInfo, BindingResult bindingResult,
                              Model model, @ModelAttribute("numberFromTable") int numberFromTable) throws ParseException {

        if(bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errorsMap);

            return "editElemPage";

        } else {

            if (tableInfo.getAmountElem() < tableInfo.getAmountMistakes()) {
                model.addAttribute("amountMistakesError", "Amount of mistakes is too large.");

                return "editElemPage";
            }

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
            Date dateForTestCorrect;

            try{
                dateForTestCorrect = simpleDateFormat.parse(tableInfo.getDatePriorRep());

            } catch (Exception exc) {
                model.addAttribute("datePriorRepError", "Need dd.mm.yyyy format.");
                return "editElemPage";
            }

            try{
                dateForTestCorrect = simpleDateFormat.parse(tableInfo.getDateNextRep());

            } catch (Exception exc) {

                model.addAttribute("dateNextRepError", "Need dd.mm.yyyy format.");
                return "editElemPage";
            }
            Date nowadays = new Date();
            dateForTestCorrect = simpleDateFormat.parse(tableInfo.getDatePriorRep());
            if (dateForTestCorrect.after(nowadays)) {
                model.addAttribute("datePriorRepError", "More than today.");
                return "editElemPage";
            }

            dateForTestCorrect = simpleDateFormat.parse(tableInfo.getDateNextRep());
            if (dateForTestCorrect.before(nowadays)) {
                model.addAttribute("dateNextRepError", "Less than tomorrow.");
                return "editElemPage";
            }

            int percentFalse = (tableInfo.getAmountMistakes() * 100) / tableInfo.getAmountElem();
            int idTableInfo = tableInfoRepo.findTableInfoIdByNumberAndUsername(numberFromTable, user.getUsername());
            TableInfo tableInfoNew = new TableInfo(numberFromTable, tableInfo.getAmountElem(), tableInfo.getAmountMistakes(), percentFalse,
                    tableInfo.getDatePriorRep(), tableInfo.getDateNextRep(), tableInfo.getStage(), user.getUsername());

            tableInfoRepo.deleteById(idTableInfo);
            tableInfoRepo.save(tableInfoNew);
            return "wallpaperPage";
        }
    }
}
