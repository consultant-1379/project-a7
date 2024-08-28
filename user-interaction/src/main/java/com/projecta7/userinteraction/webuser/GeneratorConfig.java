package com.projecta7.userinteraction.webuser;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


@Component
public class GeneratorConfig {

    @Value("${generator.url}")
    private String generatorUrl;


    public GeneratorConfig() {
        // Constructor intentionally left blank for SpringBoot
    }


    public String getReports(String urlString) throws IOException {

        // ToDo: Should be replaced with a Logger
        System.out.println(urlString);

        URL url = new URL(generatorUrl + urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder content = new StringBuilder();

        while ((inputLine = in.readLine()) != null) {
            content.append(inputLine);
        }
        in.close();

        // ToDo: Should be replaced with a Logger
        System.out.println(content);

        return content.toString();
    }


    public String format(String generator) {
        String htmlStart = "<!DOCTYPE html>\n<html lang=\"en\">\n<head>\n<meta charset=\"UTF-8\">\n</head>\n<body>\n";
        String htmlFinish = "<form action=\"http://localhost:8080/\">\n<input type=\"submit\" value=\"Home\" />\n</form>\n</body>\n</html>";

        return htmlStart + generator + htmlFinish;
    }


    public void setGeneratorUrl(String generatorUrl) {
        this.generatorUrl = generatorUrl;
    }
}
