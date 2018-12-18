package com.alex.room;

import com.alex.room.controllers.AdminController;
import com.alex.room.controllers.ControllerStatus;
import com.alex.room.controllers.MainController;
import com.alex.room.domain.TableInfo;
import com.alex.room.domain.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

@RunWith(SpringRunner.class)
@SpringBootTest
@WithUserDetails("Alexandr")
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-user-before.sql", "/tableInfo-list-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/tableInfo-list-after.sql", "/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class TransitionsTest {

    @Autowired
    private MainController mainController;

    @Autowired
    private ControllerStatus controllerStatus;

    @Autowired
    private AdminController adminController;

    @MockBean
    private Model model;


    @MockBean
    private BindingResult bindingResult;

    @Test
    public void truePickNumForEditTest() {
        TableInfo tableInfo = new TableInfo();
        User user = new User();

        tableInfo.setNumber(1);
        user.setUsername("Alexandr");

        Assert.assertEquals("editElemPage", mainController.getEditElemPage(user,tableInfo,bindingResult,model));
    }

    @Test
    public void truePickNumForDayListTest() {
        User Alexandr = new User();
        Alexandr.setUsername("Alexandr");

        String dayField = "01.12.2020";

        Assert.assertEquals("showDayListPage", controllerStatus.showDayList(dayField, model, Alexandr));
    }

    @Test
    @WithUserDetails("Alexey")
    public void truePickUserByAdminTest() {
        User Alexandr = new User();
        Alexandr.setUsername("Alexandr");

        Assert.assertEquals("pickOperatUserPage", adminController.pickOperatUser(Alexandr, model));
    }

    @Test
    @WithUserDetails("Alexey")
    public void trueDeleteUserOrNotTest() {
        User Alexandr = new User();
        Alexandr.setId(Long.valueOf(3));

        Assert.assertEquals("deleteUserOrNotPage", adminController.deleteUserOrNot(Alexandr));
    }
}