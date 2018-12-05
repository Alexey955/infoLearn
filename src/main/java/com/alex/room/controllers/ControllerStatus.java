package com.alex.room.controllers;

import com.alex.room.domain.TableInfo;
import com.alex.room.repos.TableInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class ControllerStatus {

    @Autowired
    TableInfoRepo tableInfoRepo;

    @GetMapping("/pickNeedStat")
    public String pickNeedStat() {
        return "pickNeedStatPage";
    }

    @GetMapping("/showAllList")
    public String showAllList(Map<String, Object> model) {
        Iterable<TableInfo> tableInfoIterable = tableInfoRepo.findAll();
        model.put("EntireListElem", tableInfoIterable);
        return "AllListStatPage";
    }

    @GetMapping("pickDay")
    public String pickDay() {
        return "pickDayPage";
    }

    @GetMapping("/showDayList")
    public String showDayList(@RequestParam String dayField, Map<String, Object> model) throws ParseException {
        Iterable<TableInfo> listWithoutTail = tableInfoRepo.findByDateNextRep(dayField);
        model.put("DayListWithoutTail", listWithoutTail);

        Date dateForConver;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");
        dateForConver = simpleDateFormat.parse(dayField);

        Iterable<TableInfo> listWithTail = tableInfoRepo.findByTypeDateNextRepIsLessThanEqual(dateForConver);
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

            ((List<TableInfo>) listWithTail).remove(indexToDell);
            indexToDell = 0;
        }
        //stop to create a tail list
        model.put("DayListWithTail", listWithTail);

        if(((List<TableInfo>) listWithTail).isEmpty()) {
            return "showDayListPage";
        } else {
            String pickedDay = ((List<TableInfo>) listWithTail).get(0).getDateNextRep();
            model.put("pickedDay", pickedDay);

            return "showDayListPage";
        }
    }

    @GetMapping("/showAVGAccuracy")
    public String showAVGAccuracy(Map<String, Object> model) {
        Iterable<TableInfo> tableInfoIterable = tableInfoRepo.findAll();
        long allAmount = tableInfoRepo.count();
        int allPercentFalse = 0;

        for(TableInfo x: tableInfoIterable) {
            allPercentFalse += x.getPercentFalse();
        }
        long avgAccuracy = allPercentFalse / allAmount;
        model.put("avgAccuracy", avgAccuracy);

        return "AVGAccuracyPage";
    }

    @GetMapping("/showStageAccuracy")
    public String showStageAccuracy(Map<String, Object> model) {
        Iterable<TableInfo> tableInfoCount = tableInfoRepo.findTableInfoCount();
        model.put("GroupByStage", tableInfoCount);
        return "StageAccuracyPage";
    }

    @GetMapping("/showElemInStages")
    public String showElemInStages(Map<String, Object> model) {
        Iterable<TableInfo> tableInfoIterable;
        Iterable<TableInfo> tableInfoToAdd;
        Integer topStage = tableInfoRepo.findMaxStage();

        tableInfoIterable = tableInfoRepo.findByStage(1);

        for(int i = 2; i <= topStage; i++) {
            tableInfoToAdd = tableInfoRepo.findByStage(i);
            ((List<TableInfo>) tableInfoIterable).addAll( (List<TableInfo>) tableInfoToAdd);
        }
        model.put("ElemInStage", tableInfoIterable);
        return "ElemInStagesPage";
    }
}
