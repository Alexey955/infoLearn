package com.alex.room.unit;

import com.alex.room.service.MainService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import utils.TestUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/tableInfo-list-after.sql", "/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class MainServiceUnitTest {

    @Autowired
    private MainService mainService;

    @MockBean
    private Model model;

    @MockBean
    private BindingResult bindingResult;

    @Test
    public void pickMainPage(){
        Assert.assertEquals(mainService.pickMainPage(TestUtils.user, model), "userMainPage");
        Assert.assertEquals(mainService.pickMainPage(TestUtils.admin, model), "adminMainPage");
    }

//    @Test
//    @WithUserDetails("Valery")
//    public void addElement(){
//        Assert.assertEquals(mainService.addElement(valery, tableInfo, bindingResult, model), "wallpaperPage");
//
//        ObjectError error = new ObjectError("error","Error message");
//        bindingResult.addError(error);
//        Assert.assertEquals(mainService.addElement(valery, tableInfoTwo, bindingResult, model), "addNewElemPage");
//    }

    @Test
    @WithUserDetails("Valery")
    public void deleteOneElement(){
        Assert.assertEquals(mainService.deleteOneElement(TestUtils.user, TestUtils.tableInfo, model), "deleteElemPage");

        mainService.addElement(TestUtils.valery, TestUtils.tableInfo, bindingResult, model);
        Assert.assertEquals(mainService.deleteOneElement(TestUtils.valery, TestUtils.tableInfo, model), "wallpaperPage");
    }

    @Test
    public void pickDelOneOrSeveral(){
        Assert.assertEquals(mainService.pickDelOneOrSeveral("one"), "deleteElemPage");
        Assert.assertEquals(mainService.pickDelOneOrSeveral("several"), "deleteSeveralElemPage");
    }

    @Test
    @WithUserDetails("Valery")
    public void deleteSeveralElements(){
        Assert.assertEquals(mainService.deleteSeveralElements(-1, 10, TestUtils.valery, model), "deleteSeveralElemPage");
        Assert.assertEquals(mainService.deleteSeveralElements(1002, 10, TestUtils.valery, model), "deleteSeveralElemPage");
        Assert.assertEquals(mainService.deleteSeveralElements(1, -1, TestUtils.valery, model), "deleteSeveralElemPage");
        Assert.assertEquals(mainService.deleteSeveralElements(10, 9, TestUtils.valery, model), "deleteSeveralElemPage");

        Assert.assertEquals(mainService.deleteSeveralElements(1, 5, TestUtils.valery, model), "wallpaperPage");
    }

    @Test
    @WithUserDetails("Valery")
    public void showListRep(){
        Assert.assertEquals(mainService.showListRep(model, TestUtils.valery), "repeatListPage");
    }

    @Test
    @WithUserDetails("Valery")
    public void pickElemRepeat(){
        Assert.assertEquals(mainService.pickElemRepeat(model, TestUtils.valery), "pickElemRepeatPage");
    }

    @Test
    @WithUserDetails("Valery")
    public void repeatElement(){
//        ObjectError error = new ObjectError("error","Error message");
//        bindingResult.addError(error);
//        Assert.assertEquals(mainService.repeatElement(valery, tableInfoTwo, bindingResult, model), "pickElemRepeatPage");

        mainService.addElement(TestUtils.valery, TestUtils.tableInfo, bindingResult, model);
        Assert.assertEquals(mainService.repeatElement(TestUtils.valery, TestUtils.tableInfo, bindingResult, model), "wallpaperPage");
        mainService.deleteOneElement(TestUtils.valery, TestUtils.tableInfo, model);
    }

    @Test
    public void getEditElemPage(){
        Assert.assertEquals(mainService.getEditElemPage(TestUtils.valery, TestUtils.tableInfo, bindingResult, model), "pickElemForEditPage");

        mainService.addElement(TestUtils.valery, TestUtils.tableInfo, bindingResult, model);
        Assert.assertEquals(mainService.getEditElemPage(TestUtils.valery, TestUtils.tableInfo, bindingResult, model), "editElemPage");
        mainService.deleteOneElement(TestUtils.valery, TestUtils.tableInfo, model);
    }

    @Test
    public void editElement(){
//        ObjectError error = new ObjectError("error","Error message");
//        bindingResult.addError(error);
//        Assert.assertEquals(mainService.repeatElement(valery, tableInfoTwo, bindingResult, model), "pickElemRepeatPage");

        mainService.addElement(TestUtils.valery, TestUtils.tableInfo, bindingResult, model);

        Assert.assertEquals(mainService.editElement(TestUtils.valery, TestUtils.tableInfoErrorOne, bindingResult, model, 1), "editElemPage");
        Assert.assertEquals(mainService.editElement(TestUtils.valery, TestUtils.tableInfoErrorTwo, bindingResult, model, 1), "editElemPage");
        Assert.assertEquals(mainService.editElement(TestUtils.valery, TestUtils.tableInfoErrorTwo, bindingResult, model, 1), "editElemPage");
        Assert.assertEquals(mainService.editElement(TestUtils.valery, TestUtils.tableInfoTwo, bindingResult, model, 1), "wallpaperPage");

        mainService.deleteOneElement(TestUtils.valery, TestUtils.tableInfoTwo, model);
    }
}
