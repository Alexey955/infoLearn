package com.alex.room.controllers;

import com.alex.room.domain.TableInfo;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.time.LocalDate;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class ControllerUtils {
    static Map<String, String> getErrors(BindingResult bindingResult) {
        Collector<FieldError, ?, Map<String, String>> collector = Collectors.toMap(
                fieldError -> fieldError.getField() + "Error",
                FieldError::getDefaultMessage
        );
        return bindingResult.getFieldErrors().stream().collect(collector);
    }

    public static void addErrorToModelIfBindingResultError(BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errorsMap);
        }
    }

    public static void addErrorToModelIfNumberExists(TableInfo tableInfoValid, Model model) {
        if(tableInfoValid != null) {
            model.addAttribute("numberError", "Number " + tableInfoValid.getNumber() + " exists.");
        }
    }

    public static void addErrorToModelIfNumberDoesntExist(TableInfo tableInfoValid, int numDoesntExist, Model model) {
        if(tableInfoValid == null) {
            model.addAttribute("numberError", "Number " + numDoesntExist + " doesn't exist.");
        }
    }

    public static void addErrorIfMistakesTooLarge(int amountElements, TableInfo tableInfo, Model model) {
        if (amountElements < tableInfo.getAmountMistakes()) {
            model.addAttribute("amountMistakesError", "Amount of mistakes are too large.");
        }
    }

    public static void addErrorIfDateMoreThanToday(LocalDate dateForTestCorrect, LocalDate nowadays, Model model) {
        if (dateForTestCorrect.isAfter(nowadays)) {
            model.addAttribute("datePriorRepError", "More than today.");
        }
    }

    public static void addErrorIfDateLessThanToday(LocalDate dateForTestCorrect, LocalDate nowadays, Model model) {
        if (dateForTestCorrect.isBefore(nowadays)) {
            model.addAttribute("dateNextRepError", "Less than today.");
        }
    }
}