package com.alex.room.controllers;

import com.alex.room.domain.TableInfo;
import com.alex.room.domain.User;
import com.alex.room.repos.TableInfoRepo;
import com.alex.room.service.MainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@SessionAttributes({"strForEdit", "pickedUser", "qualityFails", "numberFromTable", "listRep"})
public class MainController {

    @Autowired
    private TableInfoRepo tableInfoRepo;

    @Autowired
    private MainService mainService;

    @GetMapping("/")
    public String pickMainPage(@AuthenticationPrincipal User user, Model model) {

        return mainService.pickMainPage(user, model);
    }

    @GetMapping("/userMain")
    public String userMain() { return "userMainPage"; }

    @GetMapping("/addNew")
    public String addNewElem() {
        return "addNewElemPage";
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/wallAftAdd")
    public String wallAftAdd(@AuthenticationPrincipal User user, @Valid TableInfo tableInfo,
                             BindingResult bindingResult, Model model) {

        return mainService.addElement(user, tableInfo, bindingResult, model);
    }

    @GetMapping("/delElem")
    public String delAnElem() { return "deleteElemPage"; }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/wallAftDelOne")
    public String wallAftOneDel(@AuthenticationPrincipal User user, @Valid TableInfo tableInfo, BindingResult bindingResult, Model model) {

        return mainService.deleteOneElement(user, tableInfo, model);
    }

    @GetMapping("/delOneOrSeveral")
    public String pickDelOneOrSeveral(@RequestParam String radioDel) {

        return mainService.pickDelOneOrSeveral(radioDel);
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/wallAftDelSeveral")
    public String wallAftDelSeveral(@RequestParam int numberFieldFrom, @RequestParam int numberFieldTo,
                                    @AuthenticationPrincipal User user, Model model) {

        return mainService.deleteSeveralElements(numberFieldFrom, numberFieldTo, user, model);
    }

    @GetMapping("/listRep")
    public String showListRep(Model model, @AuthenticationPrincipal User user) {

       return mainService.showListRep(model, user);
    }

    @GetMapping("/pickElemRepeat")
    public String pickElemRepeat(Model model, @AuthenticationPrincipal User user) {

        return mainService.pickElemRepeat(model, user);
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/wallAftRepeat")
    public String wallAftRepeat(@AuthenticationPrincipal User user, @Valid TableInfo tableInfo,
                                       BindingResult bindingResult, Model model) {

        return mainService.repeatElement(user, tableInfo, bindingResult, model);
    }

    @GetMapping("/pickElemForEdit")
    public String pickElemForEdit() { return "pickElemForEditPage"; }

    @GetMapping("/editElem")
    public String getEditElemPage(@AuthenticationPrincipal User user, @Valid TableInfo tableInfo, BindingResult bindingResult, Model model) {

        return mainService.getEditElemPage(user, tableInfo, bindingResult, model);
    }

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/wallAftEdit")
    public String wallAftEdit(@AuthenticationPrincipal User user, @Valid TableInfo tableInfo, BindingResult bindingResult,
                              Model model, @ModelAttribute("numberFromTable") int numberFromTable) {

        return mainService.editElement(user, tableInfo, bindingResult, model, numberFromTable);
    }

    @GetMapping("/showIntro")
    public String showIntro() { return "showIntroPage"; }
}
