package com.projecta7.userinteraction.webuser;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;


@RunWith(SpringRunner.class)
@WebMvcTest(UserHome.class)
public class TestUserHome {

    @Autowired
    MockMvc mockMvc;


    @Test
    public void testGetHome() throws Exception {
        mockMvc.perform(get("/")).andExpect(status().isOk()).andExpect(xpath("/html/head/title[contains(text(), 'Test Report Tool')]").exists());
    }
}
