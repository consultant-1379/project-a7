package com.projecta7.testreporttool.reportgenerator;


import org.springframework.stereotype.Component;

import java.sql.SQLException;


@Component
public class ReportGeneratorImpl implements ReportGenerator {

    DatabaseParser parser = new DatabaseParser();

    @Override
    public String getTestsRunPerPeriod(String filter, String days) throws SQLException {
        switch (filter) {
            case "ENM":
                return parser.getEnmTestsRunPerPeriod(days);
            case "PRODUCT":
                return parser.getProductTestsRunPerPeriod(days);
            case "CNA":
                return parser.getCnaTestsRunPerPeriod(days);
            default:
                return "404: NOT FOUND";
        }
    }


    @Override
    public String getPassFailRates(String filter) {
        switch (filter) {
            case "ENM":
                return parser.getEnmTestRates();
            case "PRODUCT":
                return parser.getProductTestRates();
            case "CNA":
                return parser.getCnaTestRates();
            default:
                return "404: NOT FOUND";
        }
    }

    @Override
    public String getHighestFailureRate(String filter) {
        switch (filter) {
            case "ENM":
                return parser.getHighestFailENM();
            case "PRODUCT":
                return parser.getHighestFailProduct();
            case "CNA":
                return parser.getHighestFailCna();
            default:
                return "404: NOT FOUND";
        }
    }

    @Override
    public int generateAlert() {
        return 0;
    }
}
