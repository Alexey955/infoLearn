package com.alex.room;

import com.alex.room.domain.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WithUserDetails("Alexey")
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-user-before.sql", "/tableInfo-list-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/tableInfo-list-after.sql", "/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class AdminControllerTest {

//    @Autowired
//    private MockMvc mockMvc;
//
//    @Test
//    public void trueShowFullAvgAccuracyTest() throws Exception {
//
//        this.mockMvc.perform(MockMvcRequestBuilders.get("/showFullAvgAccuracy"))
//                .andDo(print())
//                .andExpect(SecurityMockMvcResultMatchers.authenticated())
//                .andExpect(MockMvcResultMatchers.xpath("/html/body/h3/span").string("20"));
//    }
//
//    @Test
//    public void trueShowFullStageAccuracyTest() throws Exception {
//
//        this.mockMvc.perform(MockMvcRequestBuilders.get("/showFullStageAccuracy"))
//                .andDo(print())
//                .andExpect(SecurityMockMvcResultMatchers.authenticated())
//                .andExpect(MockMvcResultMatchers.xpath("/html/body/div/div[1]/span[1]").string("6"))
//                .andExpect(MockMvcResultMatchers.xpath("/html/body/div/div[1]/span[2]").string("1"));
//    }
//
//    @Test
//    public void trueShowAllListPickedUserTest() throws Exception {
//
//        User Alexandr = new User();
//        Alexandr.setUsername("Alexandr");
//
//        this.mockMvc.perform(MockMvcRequestBuilders.get("/showAllList").sessionAttr("pickedUser", Alexandr))
//                .andDo(print())
//                .andExpect(SecurityMockMvcResultMatchers.authenticated())
//                .andExpect(MockMvcResultMatchers.xpath("/html/body/div/div").nodeCount(3));
//    }
//
//    @Test
//    public void trueShowAVGAccuracyPickedUserTest() throws Exception {
//
//        User Alexandr = new User();
//        Alexandr.setUsername("Alexandr");
//
//        this.mockMvc.perform(MockMvcRequestBuilders.get("/showAVGAccuracy").sessionAttr("pickedUser", Alexandr))
//                .andDo(print())
//                .andExpect(SecurityMockMvcResultMatchers.authenticated())
//                .andExpect(MockMvcResultMatchers.xpath("/html/body/h3/span").string("20"));
//    }
//
//    @Test
//    public void trueShowStageAccuracyPickedUserTest() throws Exception {
//
//        User Alexandr = new User();
//        Alexandr.setUsername("Alexandr");
//
//        this.mockMvc.perform(MockMvcRequestBuilders.get("/showStageAccuracy").sessionAttr("pickedUser", Alexandr))
//                .andDo(print())
//                .andExpect(SecurityMockMvcResultMatchers.authenticated())
//                .andExpect(MockMvcResultMatchers.xpath("/html/body/div/div[1]/span[1]").string("3"))
//                .andExpect(MockMvcResultMatchers.xpath("/html/body/div/div[1]/span[2]").string("1"));
//    }
//
//    @Test
//    public void trueShowElemInStagesPickedUserTest() throws Exception {
//
//        User Alexandr = new User();
//        Alexandr.setUsername("Alexandr");
//
//        this.mockMvc.perform(MockMvcRequestBuilders.get("/showElemInStages").sessionAttr("pickedUser", Alexandr))
//                .andDo(print())
//                .andExpect(SecurityMockMvcResultMatchers.authenticated())
//                .andExpect(MockMvcResultMatchers.xpath("/html/body/div").nodeCount(3));
//    }
//
//    @Test
//    public void trueDeletePickedUserTest() throws Exception {
//
//        User Alexandr = new User();
//        Alexandr.setUsername("Alexandr");
//        Alexandr.setId(3L);
//
//        this.mockMvc.perform(MockMvcRequestBuilders.post("/deleteTheUser")
//                .sessionAttr("pickedUser", Alexandr)
//                .with(csrf()))
//                .andDo(print())
//                .andExpect(SecurityMockMvcResultMatchers.authenticated());
//
//        this.mockMvc.perform(SecurityMockMvcRequestBuilders.formLogin().user("Alexandr").password("333333"))
//                .andDo(print())
//                .andExpect(status().is3xxRedirection())
//                .andExpect(redirectedUrl("/login?error"));
//    }
}