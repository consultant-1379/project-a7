package com.projecta7.testreporttool.reportgenerator;


import java.sql.SQLException;


public interface ReportGenerator {

    String getTestsRunPerPeriod(String filter, String days) throws SQLException;

    String getPassFailRates(String filter);

    String getHighestFailureRate(String filter);

    int generateAlert();


}
