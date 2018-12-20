package com.alex.room;

import com.alex.room.domain.User;
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
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails("Alexandr")
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-user-before.sql", "/tableInfo-list-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/tableInfo-list-after.sql", "/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class MainControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void trueAddElemTest() throws Exception {
        User user = new User();
        user.setUsername("Alexandr");
        MockHttpServletRequestBuilder multipart = multipart("/wallAftAdd")
                .param("number", "4")
                .param("amountElem", "10")
                .param("amountMistakes", "4")
                .with(csrf());

        this.mockMvc.perform(multipart);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/showAllList").sessionAttr("pickedUser", user))
                .andDo(print())
                .andExpect(SecurityMockMvcResultMatchers.authenticated())
                .andExpect(MockMvcResultMatchers.xpath("/html/body/div/div[4]/span[1]").string("4 number,"))
                .andExpect(MockMvcResultMatchers.xpath("/html/body/div/div").nodeCount(4));

    }
    @Test
    public void badAddElemLessTest() throws Exception {
        User user = new User();
        user.setUsername("Alexandr");
        MockHttpServletRequestBuilder multipart = multipart("/wallAftAdd")
                .param("number", "0")
                .param("amountElem", "0")
                .param("amountMistakes", "-1")
                .with(csrf());

        this.mockMvc.perform(multipart)
                .andDo(print())
                .andExpect(MockMvcResultMatchers.xpath("//input[@name='number']/following-sibling::div").string("Less than 1."))
                .andExpect(MockMvcResultMatchers.xpath("//input[@name='amountElem']/following-sibling::div").string("Less than 1."))
                .andExpect(MockMvcResultMatchers.xpath("//input[@name='amountMistakes']/following-sibling::div").string("Less than 0."));

    }

    @Test
    public void badAddElemMoreTest() throws Exception {
        User user = new User();
        user.setUsername("Alexandr");
        MockHttpServletRequestBuilder multipart = multipart("/wallAftAdd")
                .param("number", "1001")
                .param("amountElem", "1001")
                .param("amountMistakes", "1002")
                .with(csrf());

        this.mockMvc.perform(multipart)
                .andDo(print())
                .andExpect(MockMvcResultMatchers.xpath("//input[@name='number']/following-sibling::div").string("More than 1000."))
                .andExpect(MockMvcResultMatchers.xpath("//input[@name='amountElem']/following-sibling::div").string("More than 1000."))
                .andExpect(MockMvcResultMatchers.xpath("//input[@name='amountMistakes']/following-sibling::div").string("Amount of mistakes are too large."));
    }

    @Test
    public void trueDeleteOneElemTest() throws Exception {
        User Alexandr = new User();
        User Valery = new User();

        Alexandr.setUsername("Alexandr");
        Valery.setUsername("Valery");

        MockHttpServletRequestBuilder multipart = multipart("/wallAftDelOne")
                .param("number", "1")
                .with(csrf());

        this.mockMvc.perform(multipart);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/showAllList").sessionAttr("pickedUser", Alexandr))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.xpath("/html/body/div/div").nodeCount(2));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/showAllList").sessionAttr("pickedUser", Valery))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.xpath("/html/body/div/div").nodeCount(3));
    }

    @Test
    public void badDeleteOneElemDoesntExistTest() throws Exception {
        User Alexandr = new User();
        Alexandr.setUsername("Alexandr");

        MockHttpServletRequestBuilder multipart = multipart("/wallAftDelOne")
                .param("number", "0")
                .with(csrf());

        this.mockMvc.perform(multipart)
                .andDo(print())
                .andExpect(MockMvcResultMatchers.xpath("//input[@name='number']/following-sibling::div").string("Number 0 doesn't exist."));
    }

    @Test
    public void trueDeleteSeveralElemTest() throws Exception {
        User Alexandr = new User();
        User Valery = new User();

        Alexandr.setUsername("Alexandr");
        Valery.setUsername("Valery");

        MockHttpServletRequestBuilder multipart = multipart("/wallAftDelSeveral")
                .param("numberFieldFrom", "1")
                .param("numberFieldTo", "3")
                .with(csrf());

        this.mockMvc.perform(multipart);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/showAllList").sessionAttr("pickedUser", Alexandr))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.xpath("/html/body/div/div").nodeCount(0));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/showAllList").sessionAttr("pickedUser", Valery))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.xpath("/html/body/div/div").nodeCount(3));
    }

    @Test
    public void badDeleteSeveralElemToIsLessTest() throws Exception {

        MockHttpServletRequestBuilder multipart = multipart("/wallAftDelSeveral")
                .param("numberFieldFrom", "3")
                .param("numberFieldTo", "1")
                .with(csrf());

        this.mockMvc.perform(multipart)
                .andDo(print())
                .andExpect(MockMvcResultMatchers.xpath("//input[@name='numberFieldTo']/following-sibling::div").string("To is less than from."));
    }

    @Test
    public void badDeleteSeveralElemLessAndMoreTest() throws Exception {

        MockHttpServletRequestBuilder multipart = multipart("/wallAftDelSeveral")
                .param("numberFieldFrom", "0")
                .param("numberFieldTo", "1001")
                .with(csrf());

        this.mockMvc.perform(multipart)
                .andDo(print())
                .andExpect(MockMvcResultMatchers.xpath("//input[@name='numberFieldTo']/following-sibling::div").string("Select numbers from 1 to 1000."));
    }

    @Test
    public void badPickNumForEditDoesntExistTest() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.get("/editElem")
                .param("number", "0"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.xpath("//input[@name='number']/following-sibling::div").string("Number 0 doesn't exist."));
    }

    @Test
    public void trueEditElemTest() throws Exception {

        User Alexandr = new User();
        User Valery = new User();

        Alexandr.setUsername("Alexandr");
        Valery.setUsername("Valery");

        Integer numberFromTable = 1;

        MockHttpServletRequestBuilder multipart = multipart("/wallAftEdit").sessionAttr("numberFromTable", numberFromTable)
                .param("amountElem", "20")
                .param("datePriorRep", "01.12.2018")
                .param("amountMistakes", "2")
                .param("dateNextRep", "01.12.2020")
                .param("stage", "2")
                .with(csrf());

        this.mockMvc.perform(multipart);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/showAllList").sessionAttr("pickedUser", Alexandr))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.xpath("/html/body/div/div[1]/span[2]").string("2 stage,"));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/showAllList").sessionAttr("pickedUser", Valery))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.xpath("/html/body/div/div[1]/span[2]").string("1 stage,"));
    }

    @Test
    public void badEditElemLessTest() throws Exception {
        int numberFromTable = 1;

        MockHttpServletRequestBuilder multipart = multipart("/wallAftEdit").sessionAttr("numberFromTable", numberFromTable)
                .param("amountElem", "0")
                .param("datePriorRep", "01.12.2018")
                .param("amountMistakes", "-1")
                .param("dateNextRep", "01.12.2020")
                .param("stage", "0")
                .with(csrf());

        this.mockMvc.perform(multipart)
                .andDo(print())
                .andExpect(MockMvcResultMatchers.xpath("//input[@name='amountElem']/following-sibling::div").string("Less than 1."))
                .andExpect(MockMvcResultMatchers.xpath("//input[@name='amountMistakes']/following-sibling::div").string("Less than 0."))
                .andExpect(MockMvcResultMatchers.xpath("//input[@name='stage']/following-sibling::div").string("Less than 1."));
    }

    @Test
    public void badEditElemMoreTest() throws Exception {
        int numberFromTable = 1;

        MockHttpServletRequestBuilder multipart = multipart("/wallAftEdit").sessionAttr("numberFromTable", numberFromTable)
                .param("amountElem", "1001")
                .param("datePriorRep", "01.12.2018")
                .param("amountMistakes", "1002")
                .param("dateNextRep", "01.12.2020")
                .param("stage", "8")
                .with(csrf());

        this.mockMvc.perform(multipart)
                .andDo(print())
                .andExpect(MockMvcResultMatchers.xpath("//input[@name='amountElem']/following-sibling::div").string("More than 1000."))
                .andExpect(MockMvcResultMatchers.xpath("//input[@name='amountMistakes']/following-sibling::div").string("Amount of mistakes are too large."))
                .andExpect(MockMvcResultMatchers.xpath("//input[@name='stage']/following-sibling::div").string("More than 7."));
    }

    @Test
    public void badEditElemDateFormatTest() throws Exception {
        int numberFromTable = 1;

        MockHttpServletRequestBuilder multipart = multipart("/wallAftEdit").sessionAttr("numberFromTable", numberFromTable)
                .param("amountElem", "10")
                .param("datePriorRep", "123")
                .param("amountMistakes", "1")
                .param("dateNextRep", "123")
                .param("stage", "1")
                .with(csrf());

        this.mockMvc.perform(multipart)
                .andDo(print())
                .andExpect(MockMvcResultMatchers.xpath("//input[@name='datePriorRep']/following-sibling::div").string("Need dd.mm.yyyy format."));
    }
    @Test
    public void badEditElemPriorDateMoreTodayTest() throws Exception {
        int numberFromTable = 1;

        MockHttpServletRequestBuilder multipart = multipart("/wallAftEdit").sessionAttr("numberFromTable", numberFromTable)
                .param("amountElem", "10")
                .param("datePriorRep", "01.12.2020")
                .param("amountMistakes", "1")
                .param("dateNextRep", "01.12.2020")
                .param("stage", "1")
                .with(csrf());

        this.mockMvc.perform(multipart)
                .andDo(print())
                .andExpect(MockMvcResultMatchers.xpath("//input[@name='datePriorRep']/following-sibling::div").string("More than today."));
    }
    @Test
    public void badEditElemNextDateLessTodayTest() throws Exception {
        int numberFromTable = 1;

        MockHttpServletRequestBuilder multipart = multipart("/wallAftEdit").sessionAttr("numberFromTable", numberFromTable)
                .param("amountElem", "10")
                .param("datePriorRep", "01.12.2018")
                .param("amountMistakes", "1")
                .param("dateNextRep", "01.12.2018")
                .param("stage", "1")
                .with(csrf());

        this.mockMvc.perform(multipart)
                .andDo(print())
                .andExpect(MockMvcResultMatchers.xpath("//input[@name='dateNextRep']/following-sibling::div").string("Less than today."));
    }

    @Test
    public void trueRepeatElemTest() throws Exception {

        User Alexandr = new User();
        User Valery = new User();

        Alexandr.setUsername("Alexandr");
        Valery.setUsername("Valery");

        MockHttpServletRequestBuilder multipart = multipart("/wallAftRepeat")
                .param("number", "1")
                .param("amountMistakes", "2")
                .with(csrf());

        this.mockMvc.perform(multipart);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/showAllList").sessionAttr("pickedUser", Alexandr))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.xpath("/html/body/div/div[1]/span[2]").string("2 stage,"));

        this.mockMvc.perform(MockMvcRequestBuilders.get("/showAllList").sessionAttr("pickedUser", Valery))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.xpath("/html/body/div/div[1]/span[2]").string("1 stage,"));
    }

    @Test
    public void badRepeatElemDoesntExistTest() throws Exception {

        MockHttpServletRequestBuilder multipart = multipart("/wallAftRepeat")
                .param("number", "0")
                .param("amountMistakes", "1")
                .with(csrf());

        this.mockMvc.perform(multipart)
                .andDo(print())
                .andExpect(MockMvcResultMatchers.xpath("//input[@name='number']/preceding-sibling::span").string("Number 0 doesn't exist."));
    }

    @Test
    public void badRepeatElemLessTest() throws Exception {

        MockHttpServletRequestBuilder multipart = multipart("/wallAftRepeat")
                .param("number", "1")
                .param("amountMistakes", "-1")
                .with(csrf());

        this.mockMvc.perform(multipart)
                .andDo(print())
                .andExpect(MockMvcResultMatchers.xpath("//input[@name='amountMistakes']/preceding-sibling::span").string("Less than 0."));
    }

    @Test
    public void badRepeatElemMoreTest() throws Exception {

        MockHttpServletRequestBuilder multipart = multipart("/wallAftRepeat")
                .param("number", "1")
                .param("amountMistakes", "11")
                .with(csrf());

        this.mockMvc.perform(multipart)
                .andDo(print())
                .andExpect(MockMvcResultMatchers.xpath("//input[@name='amountMistakes']/preceding-sibling::span").string("Amount of mistakes are too large."));
    }
}
