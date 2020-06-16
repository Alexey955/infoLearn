package com.alex.room.unit;

import com.alex.room.service.AdminService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.Model;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AdminServiceUnitTest {

    @Autowired
    private AdminService adminService;

    @MockBean
    private Model model;

    @Test
    public void showUserList(){
        Assert.assertEquals(adminService.showUserList(model), "userListPage");
    }
}
