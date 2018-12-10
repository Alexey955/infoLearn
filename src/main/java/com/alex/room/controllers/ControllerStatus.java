package com.alex.room.controllers;

import com.alex.room.domain.TableInfo;
import com.alex.room.domain.User;
import com.alex.room.repos.TableInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

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
    public String showDayList(@RequestParam String dayField, Map<String, Object> model,
                              @AuthenticationPrincipal User user) throws ParseException {

        List<TableInfo> listWithoutTail = tableInfoRepo.findByDateNextRepAndUsername(dayField, user.getUsername());
        model.put("DayListWithoutTail", listWithoutTail);

        Date dateForConver;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        dateForConver = simpleDateFormat.parse(dayField);

        List<TableInfo> listWithTail = tableInfoRepo.findByUsernameAndTypeDateNextRepIsLessThanEqual(user.getUsername(), dateForConver);
        //start to create a tail list
        int numToDelete;
        int indexToDell = 0;

        for(TableInfo x: listWithoutTail) {
            numToDelete = x.getNumber();

            for(TableInfo y: listWithTail) {
                if(numToDelete == y.getNumber()) {
                    break;
                }
                indexToDell += 1;
            }

            listWithTail.remove(indexToDell);
            indexToDell = 0;
        }
        //stop to create a tail list
        model.put("DayListWithTail", listWithTail);
        model.put("pickedDay", dayField);

        return "showDayListPage";
    }

    @GetMapping("/showAVGAccuracy")
    public String showAVGAccuracy(Map<String, Object> model, @ModelAttribute("pickedUser") User user) {

        int avgAccuracy = tableInfoRepo.countAvgPercentFalse(user.getUsername());
        model.put("avgAccuracy", avgAccuracy);
        return "AvgAccuracyPage";
    }

    @GetMapping("/showStageAccuracy")
    public String showStageAccuracy(Map<String, Object> model, @ModelAttribute("pickedUser") User user) {

        model.put("GroupByStage", tableInfoRepo.findTableInfoCount(user.getUsername()));
        return "StageAccuracyPage";
    }

    @GetMapping("/showElemInStages")
    public String showElemInStages(Map<String, Object> model, @ModelAttribute("pickedUser") User user) {

        List<TableInfo> tableInfoIterable = new ArrayList<>();
        Integer topStage = tableInfoRepo.findMaxStage(user.getUsername());
        /*tableInfoRepo.findAll().sort(Comparator.comparing(TableInfo::getStage).thenComparing(TableInfo::getNumber));*/

        for(int i = 1; i <= topStage; i++) {
            tableInfoIterable.addAll(tableInfoRepo.findByStageAndUsername(i, user.getUsername()));
        }
        model.put("ElemInStage", tableInfoIterable);
        return "ElemInStagesPage";
    }
}
