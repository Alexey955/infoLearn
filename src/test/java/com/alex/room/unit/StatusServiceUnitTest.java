package com.alex.room.unit;

import com.alex.room.service.StatusService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.Model;
import utils.TestUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/tableInfo-list-after.sql", "/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class StatusServiceUnitTest {

    @Autowired
    private StatusService statusService;

    @MockBean
    private Model model;

    @Test
    public void pickNeedStat(){
        Assert.assertEquals(statusService.pickNeedStat(TestUtils.user, model), "pickNeedStatPage");
    }

    @Test
    public void showAllList(){
        Assert.assertEquals(statusService.showAllList(model, TestUtils.user), "AllListStatPage");
    }

    @Test
    public void showDayList(){
        Assert.assertEquals(statusService.showDayList("01.01.01", model, TestUtils.user), "pickDayPage");
        Assert.assertEquals(statusService.showDayList(TestUtils.strDateBeforeToday, model, TestUtils.user), "pickDayPage");
        Assert.assertEquals(statusService.showDayList(TestUtils.strDatePriorRep, model, TestUtils.user), "showDayListPage");
    }

    @Test
    public void showAVGAccuracy(){
        Assert.assertEquals(statusService.showAVGAccuracy(model, TestUtils.user), "AvgAccuracyPage");
        Assert.assertEquals(statusService.showAVGAccuracy(model, TestUtils.valery), "AvgAccuracyPage");
    }

    @Test
    public void showStageAccuracy(){

        Assert.assertEquals(statusService.showStageAccuracy(model, TestUtils.user), "StageAccuracyPage");
        Assert.assertEquals(statusService.showStageAccuracy(model, TestUtils.valery), "StageAccuracyPage");
    }

    @Test
    public void showElemInStages(){

        Assert.assertEquals(statusService.showElemInStages(model, TestUtils.user), "ElemInStagesPage");
        Assert.assertEquals(statusService.showElemInStages(model, TestUtils.valery), "ElemInStagesPage");
    }
}
