package com.alex.room.unit;

import com.alex.room.repos.UserRepo;
import com.alex.room.service.RegistrationService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import utils.TestUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class RegistrationServiceUnitTest {

    @Autowired
    private RegistrationService registrationService;

    @Autowired
    private UserRepo userRepo;

    @MockBean
    private BindingResult bindingResult;

    @MockBean
    private Model model;

    @Test
    public void addUser(){
        //Need to add test for BindingResult
        Assert.assertEquals(registrationService.addUser(TestUtils.user, bindingResult, model, "USER"), "redirect:/login");
        Assert.assertEquals(registrationService.addUser(TestUtils.user, bindingResult, model, "USER"), "registrationPage");
        userRepo.deleteById(TestUtils.user.getId());
    }
}
