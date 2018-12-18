package com.alex.room;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class RegistrationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void trueRegistrationTest() throws Exception {
        MockHttpServletRequestBuilder multipart = multipart("/registration")
                .param("username", "Username")
                .param("password", "Password")
                .with(csrf());

        this.mockMvc.perform(multipart)
                .andDo(print())
                .andExpect(redirectedUrl("/login"));

        this.mockMvc.perform(SecurityMockMvcRequestBuilders.formLogin().user("Username").password("Password"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    public void badRegLessSymbols() throws Exception {
        MockHttpServletRequestBuilder multipart = multipart("/registration")
                .param("username", "User")
                .param("password", "Pass")
                .with(csrf());

        this.mockMvc.perform(multipart)
                .andDo(print())
                .andExpect(MockMvcResultMatchers.xpath("//input[@name='username']/following-sibling::div").string("Less than 6 symbols."))
                .andExpect(MockMvcResultMatchers.xpath("//input[@name='password']/following-sibling::div").string("Less than 6 symbols."));
    }

    @Test
    public void badRegMoreSymbols() throws Exception {
        MockHttpServletRequestBuilder multipart = multipart("/registration")
                .param("username", "UserTestMore")
                .param("password", "PasswordMore")
                .with(csrf());

        this.mockMvc.perform(multipart)
                .andDo(print())
                .andExpect(MockMvcResultMatchers.xpath("//input[@name='username']/following-sibling::div").string("More than 10 symbols."))
                .andExpect(MockMvcResultMatchers.xpath("//input[@name='password']/following-sibling::div").string("More than 10 symbols."));
    }

    @Test
    public void badRegUsernameExists() throws Exception {
        MockHttpServletRequestBuilder multipart = multipart("/registration")
                .param("username", "Valery")
                .param("password", "Password")
                .with(csrf());

        this.mockMvc.perform(multipart)
                .andDo(print())
                .andExpect(MockMvcResultMatchers.xpath("//input[@name='username']/following-sibling::div").string("User Valery exists."));
    }
}
