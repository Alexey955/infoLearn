package com.alex.room.controllers;

import com.alex.room.domain.TableInfo;
import com.alex.room.domain.User;
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

    static void addErrorToModelIfBindingResultError(BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errorsMap);
        }
    }

    static void addErrorToModelIfNumberExists(TableInfo tableInfoValid, Model model) {
        if(tableInfoValid != null) {
            model.addAttribute("numberError", "number " + tableInfoValid.getNumber() + " exists.");
        }
    }

    static void addErrorToModelIfNumberDoesntExist(TableInfo tableInfoValid, int numDoesntExist, Model model) {
        if(tableInfoValid == null) {
            model.addAttribute("numberError", "number " + numDoesntExist + " doesn't exist.");
        }
    }

    static void addErrorIfMistakesTooLarge(TableInfo tableInfo, Model model) {
        if (tableInfo.getAmountElem() < tableInfo.getAmountMistakes()) {
            model.addAttribute("amountMistakesError", "Amount of mistakes are too large.");
        }
    }

    static void addErrorIfDateMoreThanToday(LocalDate dateForTestCorrect, LocalDate nowadays, Model model) {
        if (dateForTestCorrect.isAfter(nowadays)) {
            model.addAttribute("datePriorRepError", "More than today.");
        }
    }

    static void addErrorIfDateLessThanToday(LocalDate dateForTestCorrect, LocalDate nowadays, Model model) {
        if (dateForTestCorrect.isBefore(nowadays)) {
            model.addAttribute("dateNextRepError", "Less than today.");
        }
    }

    static void addErrorToModelIfUserExists(User userValid, Model model) {
        if(userValid != null) {
            model.addAttribute("usernameError", "user " + userValid.getUsername() + " exists.");
        }
    }
}