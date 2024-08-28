package com.projecta7.userinteraction.webuser;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;


@WebMvcTest(UserREST.class)
class TestUserREST {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GeneratorConfig generatorConfig;


    @DisplayName("Test success rate API")
    @Test
    void getHighestSuccessRateTest() throws Exception {
        String result = "Highest Success Rate";
        Mockito.when(generatorConfig.getReports("http://localhost:8080/highestFail/" + Mockito.anyString())).thenReturn(result);

        assertEquals(generatorConfig.getReports("http://localhost:8080/highestFail/CNA"), result);
        assertEquals(generatorConfig.getReports("http://localhost:8080/highestFail/ENM"), result);
        assertEquals(generatorConfig.getReports("http://localhost:8080/highestFail/PRODUCT"), result);
    }


    @DisplayName("Test Pass Fail Rate API for user")
    @Test
    void testPassFailRates() throws IOException {
        String result = "Pass Fail";
        Mockito.when(generatorConfig.getReports("http://localhost:8080/passfailrate/" + Mockito.anyString())).thenReturn(result);

        assertEquals(generatorConfig.getReports("http://localhost:8080/passfailrate/CNA"), result);
        assertEquals(generatorConfig.getReports("http://localhost:8080/passfailrate/ENM"), result);
        assertEquals(generatorConfig.getReports("http://localhost:8080/passfailrate/PRODUCT"), result);

    }


    @DisplayName("Test number of tests run API for user")
    @Test
    void testNumberOfTestsRun() throws IOException {
        String result = "Pass Fail";
        Mockito.when(generatorConfig.getReports("http://localhost:8080/numberoftestsrun/" + Mockito.anyString())).thenReturn(result);

        assertEquals(generatorConfig.getReports("http://localhost:8080/numberoftestsrun/CNA?days=7"), result);
        assertEquals(generatorConfig.getReports("http://localhost:8080/numberoftestsrun/ENM?days=7"), result);
        assertEquals(generatorConfig.getReports("http://localhost:8080/numberoftestsrun/PRODUCT?days=7"), result);
    }
}
