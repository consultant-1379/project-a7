package com.projecta7.userinteraction.webuser;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


@RestController
public class UserREST {

    @Autowired
    GeneratorConfig generator;


    @GetMapping(value = "/highestFail/{filter}", produces = { "text/html" })
    public String getHighestFailRatePerFilter(@PathVariable String filter) throws IOException {
        return generator.format("Highest fail rate for " + filter + ": " + generator.getReports("/highestFail/" + filter));
    }


    @GetMapping(value = "/passfailrate/{filter}", produces = { "text/html" })
    public String getPassFailRate(@PathVariable String filter) throws IOException {
        return generator.format("Tests passed " + generator.getReports("/passfailrate/" + filter) + " Tests failed");
    }


    @GetMapping(value = "/numberoftestsrun/{filter}", produces = { "text/html" })
    public String getNumberOfTests(@PathVariable String filter, @RequestParam int days) throws IOException {
        return generator.format("Number of tests run for " + filter + ": " + generator.getReports("/numberoftestsrun/" + filter + "?days=" + Math.abs(days)));
    }
}
