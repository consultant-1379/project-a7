package com.projecta7.testreporttool.reportgenerator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TestGeneratorREST {

    @InjectMocks ReportGeneratorImpl rg;

    @Mock DatabaseParser dbp;

    @BeforeEach
    void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @DisplayName("Test Get Tests Run Per Period For ENM")
    @Test
    void testgetTestsRunPerPeriodForENM() throws SQLException {

        String resultENM = "Tests run per period for ENM";
        when(dbp.getEnmTestsRunPerPeriod("7")).thenReturn(resultENM);
        assertEquals(rg.getTestsRunPerPeriod("ENM","7"),resultENM);
    }

    @DisplayName("Test to Get Tests Run Per Period For Product")
    @Test
    void testgetTestsRunPerPeriodForProduct() throws SQLException {

        String resultProduct = "Tests run per period for Product";
        when(dbp.getProductTestsRunPerPeriod("7")).thenReturn(resultProduct);
        assertEquals(rg.getTestsRunPerPeriod("PRODUCT","7"),resultProduct);
    }

    @DisplayName("Test to Get Tests Run Per Period For CNA")
    @Test
    void testgetTestsRunPerPeriodForCNA() throws SQLException {

        String resultCNA = "Tests run per period for CNA";
        when(dbp.getCnaTestsRunPerPeriod("7")).thenReturn(resultCNA);
        assertEquals(rg.getTestsRunPerPeriod("CNA","7"),resultCNA);
    }

    @DisplayName("Test to Get Tests Run Per Period For Other")
    @Test
    void testgetTestsRunPerPeriodForOther() throws SQLException {

        String resultOther = "404: NOT FOUND";
        when(dbp.getCnaTestsRunPerPeriod(Mockito.anyString())).thenReturn(resultOther);
        assertEquals(rg.getTestsRunPerPeriod("test","123"),resultOther);
    }


    @DisplayName("Test to Get Pass Fail Rates For ENM")
    @Test
    void testGetPassFailRatesForENM(){

        String resultENM = "Pass Fail Rates for ENM";
        when(dbp.getEnmTestRates()).thenReturn(resultENM);
        assertEquals(rg.getPassFailRates("ENM"),resultENM);
    }

    @DisplayName("Test to Get Pass Fail Rates For Product")
    @Test
    void testGetPassFailRatesForProduct(){

        String resultProduct = "Pass Fail Rates for Product";
        when(dbp.getProductTestRates()).thenReturn(resultProduct);
        assertEquals(rg.getPassFailRates("PRODUCT"),resultProduct);
    }

    @DisplayName("Test Get Pass Fail Rates For CNA")
    @Test
    void testGetPassFailRatesForCNA(){

        String resultCNA = "Pass Fail Rates for CNA";
        when(dbp.getCnaTestRates()).thenReturn(resultCNA);
        assertEquals(rg.getPassFailRates("CNA"),resultCNA);
    }

    @DisplayName("Test to Get Pass Fail Rates For Other")
    @Test
    void testGetPassFailRatesForOther(){

        String resultOther = "404: NOT FOUND";
        when(dbp.getCnaTestRates()).thenReturn(resultOther);
        assertEquals(rg.getPassFailRates("test"),resultOther);
    }

    @DisplayName("Test to Get Highest Failure Rate For ENM")
    @Test
    void testGetHighestFailureRateForENM(){

        String resultENM = "Highest Failure Rate for ENM";
        when(dbp.getHighestFailENM()).thenReturn(resultENM);
        assertEquals(rg.getHighestFailureRate("ENM"),resultENM);
    }

    @DisplayName("Test to Get Highest Failure Rate For Product")
    @Test
    void testGetHighestFailureRateForProduct(){

        String resultProduct = "Highest Failure Rate for Product";
        when(dbp.getHighestFailProduct()).thenReturn(resultProduct);
        assertEquals(rg.getHighestFailureRate("PRODUCT"),resultProduct);
    }

    @DisplayName("Test to Get Highest Failure Rate For CNA")
    @Test
    void testGetHighestFailureRateForCNA(){

        String resultCNA = "Highest Failure Rate for CNA";
        when(dbp.getHighestFailCna()).thenReturn(resultCNA);
        assertEquals(rg.getHighestFailureRate("CNA"),resultCNA);
    }

    @DisplayName("Test to Get Highest Failure Rate For Other")
    @Test
    void testGetHighestFailureRateForOther(){
        String resultOther = "404: NOT FOUND";
        when(dbp.getHighestFailCna()).thenReturn(resultOther);
        assertEquals(rg.getHighestFailureRate("test"),resultOther);
    }

    @DisplayName("Test to Generate Alert")
    @Test
    void testGenerateAlert(){
        assertEquals(0, rg.generateAlert());
    }

}

