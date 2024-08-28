package com.projecta7.testreporttool.reportgenerator;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.SQLException;


@RestController
public class GeneratorRESTController {


    public final ReportGenerator reportGenerator = new ReportGeneratorImpl();


    @GetMapping(value = "/highestFail/{filter}", produces = {"text/plain"})
    public String getHighestFailRatePerFilter(@PathVariable String filter) {
        return reportGenerator.getHighestFailureRate(filter);
    }


    @GetMapping(value = "/passfailrate/{filter}", produces = {"text/plain"})
    public String getPassFailRate(@PathVariable String filter) {
        return reportGenerator.getPassFailRates(filter);
    }


    @GetMapping(value = "/numberoftestsrun/{filter}", produces = {"text/plain"})
    public String getNumberOfTests(@PathVariable String filter, @RequestParam String days) throws SQLException {
        return reportGenerator.getTestsRunPerPeriod(filter, days);
    }


}
