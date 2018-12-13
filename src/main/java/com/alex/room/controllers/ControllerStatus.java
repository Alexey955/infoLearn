package com.alex.room.controllers;

import com.alex.room.domain.Stats;
import com.alex.room.domain.TableInfo;
import com.alex.room.domain.User;
import com.alex.room.repos.TableInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@SessionAttributes("pickedUser")
public class ControllerStatus {

    @Autowired
    TableInfoRepo tableInfoRepo;

    @GetMapping("/pickNeedStat")
    public String pickNeedStat() {
        return "pickNeedStatPage";
    }

    @GetMapping("/showAllList")
    public String showAllList(Map<String, Object> model, @ModelAttribute("pickedUser") User user) {

        List<TableInfo> tableInfoIterable = tableInfoRepo.findByUsername(user.getUsername());
        model.put("EntireListElem", tableInfoIterable);
        return "AllListStatPage";
    }

    @GetMapping("pickDay")
    public String pickDay() {
        return "pickDayPage";
    }

    @GetMapping("/showDayList")
    public String showDayList(@RequestParam String dayField, Model model,
                              @AuthenticationPrincipal User user) {

        LocalDate dateForTestCorrect;

        try{
            dateForTestCorrect = LocalDate.parse(dayField, DateTimeFormatter.ofPattern("dd.MM.yyyy"));

        } catch (DateTimeParseException exc) {
            model.addAttribute("dateSelectError", "Need dd.mm.yyyy format.");
            return "pickDayPage";
        }

        LocalDate nowadays = LocalDate.now();
        dateForTestCorrect = LocalDate.parse(dayField, DateTimeFormatter.ofPattern("dd.MM.yyyy"));

        if (dateForTestCorrect.isBefore(nowadays)) {
            model.addAttribute("dateSelectError", "Less than today.");
            return "pickDayPage";
        }

        LocalDate pickedDateLocalDateType = LocalDate.parse(dayField, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        List<TableInfo> listWithoutTail = tableInfoRepo.findByTypeDateNextRepAndUsername(pickedDateLocalDateType, user.getUsername());
        model.addAttribute("DayListWithoutTail", listWithoutTail);


        List<TableInfo> listWithTail = tableInfoRepo.findByUsernameAndTypeDateNextRepIsLessThanEqual(user.getUsername(), pickedDateLocalDateType);
        listWithTail.removeAll(listWithoutTail);

        model.addAttribute("DayListWithTail", listWithTail);
        model.addAttribute("pickedDay", dayField);

        return "showDayListPage";
    }

    @GetMapping("/showAVGAccuracy")
    public String showAVGAccuracy(Map<String, Object> model, @ModelAttribute("pickedUser") User user) {

        Integer avgAccuracy = tableInfoRepo.countAvgPercentFalse(user.getUsername());
        if (avgAccuracy == null) {
            return "AvgAccuracyPage";
        }
        model.put("avgAccuracy", avgAccuracy);
        return "AvgAccuracyPage";
    }

    @GetMapping("/showStageAccuracy")
    public String showStageAccuracy(Map<String, Object> model, @ModelAttribute("pickedUser") User user) {

        List<Stats> groupByStage = tableInfoRepo.findTableInfoCount(user.getUsername());

        if (groupByStage == null) {
            return "StageAccuracyPage";
        }

        model.put("GroupByStage", tableInfoRepo.findTableInfoCount(user.getUsername()));
        return "StageAccuracyPage";
    }

    @GetMapping("/showElemInStages")
    public String showElemInStages(Map<String, Object> model, @ModelAttribute("pickedUser") User user) {

        List<TableInfo> tableInfoList = new ArrayList<>();
        Integer topStage = tableInfoRepo.findMaxStage(user.getUsername());
        /*tableInfoRepo.findAll().sort(Comparator.comparing(TableInfo::getStage).thenComparing(TableInfo::getNumber));*/

        if (topStage == null) {
            return "ElemInStagesPage";
        }

        for(int i = 1; i <= topStage; i++) {
            tableInfoList.addAll(tableInfoRepo.findByStageAndUsername(i, user.getUsername()));
        }

        model.put("ElemInStage", tableInfoList);
        return "ElemInStagesPage";
    }
}
