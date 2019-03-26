package com.alex.room;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import utils.TestUtils;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails("Alexandr")
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-user-before.sql", "/tableInfo-list-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/tableInfo-list-after.sql", "/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class ControllerStatusTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void allListTest() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/showAllList").sessionAttr("pickedUser", TestUtils.alexandr))
                .andDo(print())
                .andExpect(SecurityMockMvcResultMatchers.authenticated())
                .andExpect(MockMvcResultMatchers.xpath("/html/body/div/div").nodeCount(3));
    }

    @Test
    public void trueShowDayListTest() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/showDayList")
                .param("dayField", "20.12.2020"))
                .andDo(print())
                .andExpect(SecurityMockMvcResultMatchers.authenticated())
                .andExpect(MockMvcResultMatchers.xpath("/html/body/span/div").nodeCount(3));
    }

    @Test
    public void badShowDayListLessTest() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/showDayList")
                .param("dayField", "01.12.2018"))
                .andDo(print())
                .andExpect(SecurityMockMvcResultMatchers.authenticated())
                .andExpect(MockMvcResultMatchers.xpath("//input[@name='dayField']/following-sibling::div").string("Less than today."));
    }

    @Test
    public void badShowDayListDateFormatTest() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/showDayList")
                .param("dayField", "123"))
                .andDo(print())
                .andExpect(SecurityMockMvcResultMatchers.authenticated())
                .andExpect(MockMvcResultMatchers.xpath("//input[@name='dayField']/following-sibling::div").string("Need dd.mm.yyyy format."));
    }

    @Test
    public void trueShowAvgAccuracyTest() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/showAVGAccuracy").sessionAttr("pickedUser", TestUtils.alexandr))
                .andDo(print())
                .andExpect(SecurityMockMvcResultMatchers.authenticated())
                .andExpect(MockMvcResultMatchers.xpath("/html/body/h3/span").string("20"));
    }

    @Test
    public void trueShowAVGAccuracyEmptyTest() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/showAVGAccuracy").sessionAttr("pickedUser", TestUtils.dmitry))
                .andDo(print())
                .andExpect(SecurityMockMvcResultMatchers.authenticated())
                .andExpect(MockMvcResultMatchers.xpath("/html/body/h3").string("There are not elements."));
    }

    @Test
    public void trueShowStageAccuracyTest() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/showStageAccuracy").sessionAttr("pickedUser", TestUtils.alexandr))
                .andDo(print())
                .andExpect(SecurityMockMvcResultMatchers.authenticated())
                .andExpect(MockMvcResultMatchers.xpath("/html/body/div/div/span[1]").string("3"))
                .andExpect(MockMvcResultMatchers.xpath("/html/body/div/div/span[2]").string("1"));
    }

    @Test
    public void trueShowStageAccuracyEmptyTest() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/showStageAccuracy").sessionAttr("pickedUser", TestUtils.dmitry))
                .andDo(print())
                .andExpect(SecurityMockMvcResultMatchers.authenticated())
                .andExpect(MockMvcResultMatchers.xpath("/html/body/div/h3").string("There are not elements."));
    }

    @Test
    public void trueShowElemInStagesTest() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/showElemInStages").sessionAttr("pickedUser", TestUtils.alexandr))
                .andDo(print())
                .andExpect(SecurityMockMvcResultMatchers.authenticated())
                .andExpect(MockMvcResultMatchers.xpath("/html/body/div").nodeCount(3));
    }

    @Test
    public void trueShowElemInStagesEmptyTest() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/showElemInStages").sessionAttr("pickedUser", TestUtils.dmitry))
                .andDo(print())
                .andExpect(SecurityMockMvcResultMatchers.authenticated())
                .andExpect(MockMvcResultMatchers.xpath("/html/body/h3[2]").string("There are not elements."));
    }

}
