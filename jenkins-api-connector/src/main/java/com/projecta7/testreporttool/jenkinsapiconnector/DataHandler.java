package com.projecta7.testreporttool.jenkinsapiconnector;


import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.model.Build;
import com.offbytwo.jenkins.model.JobWithDetails;
import com.offbytwo.jenkins.model.TestResult;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;


public class DataHandler {
    private Connection connection;


    private void createConnection() {

        try {
            InputStream stream = getClass().getResourceAsStream("/application.properties");
            Properties p = new Properties();
            p.load(stream);
            String url = (String) p.get("URL");
            String username = (String) p.get("Uname");
            String password = (String) p.get("password");
            connection = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void readDatabase() throws SQLException {
        createConnection();
        String prest = "SELECT CRA, CNA, RESPONSIBLE_LINE_MANAGER, JENKINS_BUILD_URL FROM enm_products";

        try (PreparedStatement preparedStmt = connection.prepareStatement(prest)) {

            ResultSet rs = preparedStmt.executeQuery();

            while (rs.next()) {
                String cra = rs.getString(1);
                String cna = rs.getString(2);
                String lineManager = rs.getString(3);
                String url = rs.getString(4);
                getDataFromJenkins(cra, cna, lineManager, url);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.close();
        }
    }


    public void getDataFromJenkins(String cra, String cna, String lineManager, String jobUrl) {
        String[] splittedURI = jobUrl.split("job/");
        URI uri = null;

        try {
            uri = new URI(splittedURI[0]);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        String jobName = splittedURI[1];

        JenkinsServer jenkins;
        assert uri != null;
        jenkins = new JenkinsServer(uri, "", "");


        try {
            JobWithDetails details = jenkins.getJob(jobName);
            List<Build> builds = details.getAllBuilds();


            for (Build build1 : builds) {
                DataFromJenkins jenkinsdata = new DataFromJenkins(cra, cna, lineManager, jobName);
                try {
                    jenkinsdata.setBuildResult(build1.details().getResult().toString());

                    long timestamp = build1.details().getTimestamp(); //Example -> in ms
                    new java.util.Date(timestamp);
                    Timestamp timestamp2 = new Timestamp(timestamp);
                    jenkinsdata.setD(new Date(timestamp2.getTime()));

                    TestResult results = build1.getTestResult();
                    jenkinsdata.setPass(results.getPassCount());
                    jenkinsdata.setFail(results.getFailCount());
                    jenkinsdata.setSkip(results.getSkipCount());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                updateDatabase(jenkinsdata);
            }
        } catch (IOException | SQLException e) {
            e.printStackTrace();
        }
    }


    private void updateDatabase(DataFromJenkins jenkinsData) throws SQLException {
        String query = " insert into builds_data (PRODUCT, CNA, JOB_NAME, RESPONSIBLE_LINE_MANAGER, BUILD_STATUS,"
                + " NUMBER_OF_PASS, NUMBER_OF_FAILS, NUMBER_OF_SKIPS, DATE_CREATED)"
                + " values (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        // create the mysql insert preparedstatement
        PreparedStatement preparedStmt = null;
        try {
            preparedStmt = connection.prepareStatement(query);
            try {
                preparedStmt.setString(1, jenkinsData.getCra());
                preparedStmt.setString(2, jenkinsData.getCna());
                preparedStmt.setString(3, jenkinsData.getJob());
                preparedStmt.setString(4, jenkinsData.getLineManager());
                preparedStmt.setString(5, jenkinsData.getBuildResult());
                preparedStmt.setInt(6, jenkinsData.getPass());
                preparedStmt.setInt(7, jenkinsData.getFail());
                preparedStmt.setInt(8, jenkinsData.getSkip());
                preparedStmt.setDate(9, (Date) jenkinsData.getD());
            } catch (SQLException e) {
                e.printStackTrace();
            }
            preparedStmt.execute();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            assert preparedStmt != null;
            preparedStmt.close();
        }
    }
}
