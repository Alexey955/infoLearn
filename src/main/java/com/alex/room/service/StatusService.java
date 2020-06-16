package com.alex.room.service;

import com.alex.room.domain.Stats;
import com.alex.room.domain.TableInfo;
import com.alex.room.domain.User;
import com.alex.room.repos.TableInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Service
@SessionAttributes("pickedUser")
public class StatusService {

    @Autowired
    private TableInfoRepo tableInfoRepo;

    public String pickNeedStat(User user, Model model){

        if (model.asMap().isEmpty()) {
            model.addAttribute("pickedUser", user);
        }
        return "pickNeedStatPage";
    }

    public String showAllList(Model model, User user){

        List<TableInfo> tableInfoIterable = tableInfoRepo.findByUser(user);
        tableInfoIterable.sort(Comparator.comparing(TableInfo::getNumber));
        model.addAttribute("EntireListElem", tableInfoIterable);
        return "AllListStatPage";
    }

    public String showDayList(String dayField, Model model, User user){

        try {
            LocalDate.parse(dayField, DateTimeFormatter.ofPattern("dd.MM.yyyy"));

        } catch (DateTimeParseException exc) {
            model.addAttribute("dateSelectError", "Need dd.mm.yyyy format.");
            return "pickDayPage";
        }

        LocalDate nowadays = LocalDate.now();
        LocalDate dateForTestCorrect;
        dateForTestCorrect = LocalDate.parse(dayField, DateTimeFormatter.ofPattern("dd.MM.yyyy"));

        if (dateForTestCorrect.isBefore(nowadays)) {
            model.addAttribute("dateSelectError", "Less than today.");
            return "pickDayPage";
        }

        LocalDate pickedDateLocalDateType = LocalDate.parse(dayField, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        List<TableInfo> listWithoutTail = tableInfoRepo.findByTypeDateNextRepAndUser(pickedDateLocalDateType, user);
        model.addAttribute("DayListWithoutTail", listWithoutTail);


        List<TableInfo> listWithTail = tableInfoRepo.findByUserAndTypeDateNextRepIsLessThanEqual(user, pickedDateLocalDateType);
        listWithTail.removeAll(listWithoutTail);

        model.addAttribute("DayListWithTail", listWithTail);
        model.addAttribute("pickedDay", dayField);

        return "showDayListPage";
    }

    public String showAVGAccuracy(Model model, User user){

        Integer avgAccuracy = tableInfoRepo.countAvgPercentFalse(user);
        if (avgAccuracy == null) {
            return "AvgAccuracyPage";
        }
        model.addAttribute("avgAccuracy", avgAccuracy);
        return "AvgAccuracyPage";
    }

    public String showStageAccuracy(Model model, User user){

        List<Stats> groupByStage = tableInfoRepo.findTableInfoCount(user);

        if (groupByStage == null) {
            return "StageAccuracyPage";
        }

        model.addAttribute("GroupByStage", tableInfoRepo.findTableInfoCount(user));
        return "StageAccuracyPage";
    }

    public String showElemInStages(Model model, User user){

        List<TableInfo> tableInfoList = new ArrayList<>();
        Integer topStage = tableInfoRepo.findMaxStage(user);

        if (topStage == null) {
            return "ElemInStagesPage";
        }

        for (int i = 1; i <= topStage; i++) {
            tableInfoList.addAll(tableInfoRepo.findByStageAndUser(i, user));
        }

        model.addAttribute("ElemInStage", tableInfoList);
        return "ElemInStagesPage";
    }
}
