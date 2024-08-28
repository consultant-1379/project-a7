package com.projecta7.testreporttool.reportgenerator;

import com.mysql.cj.x.protobuf.MysqlxPrepare;

import java.io.InputStream;
import java.sql.*;
import java.util.Properties;


public class DatabaseParser {
    private Connection connection;

    private void createConnection(){

        try {
            InputStream stream = getClass().getResourceAsStream("/application.properties");
            Properties p=new Properties ();
            p.load (stream);
            String url= (String) p.get ("URL");
            String username= (String) p.get ("Uname");
            String password= (String) p.get ("password");
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connected Opened to database");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getEnmTestsRunPerPeriod(String days) throws SQLException {
        createConnection();
        String prest= "SELECT SUM(NUMBER_OF_PASS+NUMBER_OF_FAILS + NUMBER_OF_SKIPS) as TOTAL_TESTS_RUN," +
                "SUM(NUMBER_OF_PASS) as TOTAL_PASS,\n" +
                "SUM(NUMBER_OF_FAILS) as TOTAL_FAIL,\n" +
                "SUM(NUMBER_OF_SKIPS) as TOTAL_SKIP \n" +
                "FROM builds_data\n" +
                "WHERE DATE_CREATED>=CURDATE()-?;";
        StringBuilder toReturn = new StringBuilder();

        try (PreparedStatement preparedStmt = connection.prepareStatement(prest)) {
            preparedStmt.setString(1, days);
            ResultSet rs = preparedStmt.executeQuery();


            while (rs.next()) {
                String total = rs.getString(1);
                String pass = rs.getString(2);
                String fail = rs.getString(3);
                String skip = rs.getString(4);
                toReturn.append(String.format("<br/>Total pass: %s<br/> Total fail: %s<br/> Total skip:%s<br/> Total tests:%s<br/>", pass, fail, skip, total));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return toReturn.toString();
    }

    public String getProductTestsRunPerPeriod(String days) {
        createConnection();
        String prest= "SELECT PRODUCT, SUM(NUMBER_OF_PASS+NUMBER_OF_FAILS + NUMBER_OF_SKIPS) as TOTAL_TESTS_RUN," +
                "SUM(NUMBER_OF_PASS) as TOTAL_PASS,\n" +
                "SUM(NUMBER_OF_FAILS) as TOTAL_FAIL,\n" +
                "SUM(NUMBER_OF_SKIPS) as TOTAL_SKIP \n" +
                "FROM builds_data\n" +
                "WHERE DATE_CREATED>=CURDATE()-?" +
                "GROUP BY PRODUCT;";
        StringBuilder toReturn = new StringBuilder();

        try (PreparedStatement preparedStmt = connection.prepareStatement(prest)) {
            preparedStmt.setString(1, days);
            ResultSet rs = preparedStmt.executeQuery();

            while (rs.next()) {
                String product = rs.getString(1);
                String total = rs.getString(2);
                String pass = rs.getString(3);
                String fail = rs.getString(4);
                String skip = rs.getString(5);
                toReturn.append(String.format("<br/>Product Name:%s<br/> Total pass: %s<br/> Total fail: %s<br/> Total skip:%s<br/> Total tests:%s<br/>", product, pass, fail, skip, total));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return toReturn.toString();
    }

    public String getCnaTestsRunPerPeriod(String days) {
        createConnection();
        String prest= "SELECT CNA, SUM(NUMBER_OF_PASS+NUMBER_OF_FAILS + NUMBER_OF_SKIPS) as TOTAL_TESTS_RUN," +
                "SUM(NUMBER_OF_PASS) as TOTAL_PASS,\n" +
                "SUM(NUMBER_OF_FAILS) as TOTAL_FAIL,\n" +
                "SUM(NUMBER_OF_SKIPS) as TOTAL_SKIP \n" +
                "FROM builds_data\n" +
                "WHERE DATE_CREATED>=CURDATE()-?" +
                "GROUP BY CNA;";
        StringBuilder toReturn = new StringBuilder();

        try (PreparedStatement preparedStmt = connection.prepareStatement(prest)) {
            preparedStmt.setString(1, days);
            ResultSet rs = preparedStmt.executeQuery();

            while (rs.next()) {
                if (rs.getInt(2) != 0) {
                    String cna = rs.getString(1);
                    String total = rs.getString(2);
                    String pass = rs.getString(3);
                    String fail = rs.getString(4);
                    String skip = rs.getString(5);
                    toReturn.append(String.format("<br/>CNA Name:%s<br/> Total pass: %s<br/> Total fail: %s<br/> Total skip:%s<br/> Total tests:%s<br/>", cna, pass, fail, skip, total));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return toReturn.toString();
    }

    public String getEnmTestRates() {
        createConnection();
        String prest = "select concat(round((sum(NUMBER_OF_PASS)/sum(NUMBER_OF_PASS+NUMBER_OF_FAILS+NUMBER_OF_SKIPS))*100,2),'%') as PASS_RATE,\n" +
                "concat(round((sum(NUMBER_OF_FAILS)/sum(NUMBER_OF_PASS+NUMBER_OF_FAILS+NUMBER_OF_SKIPS))*100,2),'%') as FAIL_RATE,\n" +
                "concat(round((sum(NUMBER_OF_SKIPS)/sum(NUMBER_OF_PASS+NUMBER_OF_FAILS+NUMBER_OF_SKIPS))*100,2),'%') as SKIP_RATE\n" +
                "from builds_data;";
        StringBuilder toReturn = new StringBuilder();

        try (PreparedStatement preparedStmt = connection.prepareStatement(prest)) {

            ResultSet rs = preparedStmt.executeQuery();

            while (rs.next()) {
                String passRate = rs.getString(1);
                String failRate = rs.getString(2);
                String skipRate = rs.getString(3);
                toReturn.append(String.format("<br/>Pass Rate: %s<br/> Fail Rate: %s<br/> Skip Rate: %s<br/>", passRate, failRate, skipRate));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return toReturn.toString();

    }

    public String getProductTestRates() {
        createConnection();
        String prest = "select PRODUCT, concat(round((sum(NUMBER_OF_PASS)/sum(NUMBER_OF_PASS+NUMBER_OF_FAILS+NUMBER_OF_SKIPS))*100,2),'%') as PASS_RATE,\n" +
                "concat(round((sum(NUMBER_OF_FAILS)/sum(NUMBER_OF_PASS+NUMBER_OF_FAILS+NUMBER_OF_SKIPS))*100,2),'%') as FAIL_RATE,\n" +
                "concat(round((sum(NUMBER_OF_SKIPS)/sum(NUMBER_OF_PASS+NUMBER_OF_FAILS+NUMBER_OF_SKIPS))*100,2),'%') as SKIP_RATE\n" +
                "from builds_data\n" +
                "GROUP BY PRODUCT;";
        StringBuilder toReturn = new StringBuilder();

        try (PreparedStatement preparedStmt = connection.prepareStatement(prest)) {

            ResultSet rs = preparedStmt.executeQuery();

            while (rs.next()) {
                String product = rs.getString(1);
                String passRate = rs.getString(2);
                String failRate = rs.getString(3);
                String skipRate = rs.getString(4);
                toReturn.append(String.format("<br/>Product: %s<br/> Pass Rate: %s<br/> Fail Rate: %s<br/> Skip Rate: %s<br/>", product, passRate, failRate, skipRate));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return toReturn.toString();

    }

    public String getCnaTestRates() {
        createConnection();
        String prest = "select CNA, concat(round((sum(NUMBER_OF_PASS)/sum(NUMBER_OF_PASS+NUMBER_OF_FAILS+NUMBER_OF_SKIPS))*100,2),'%') as PASS_RATE,\n" +
                "concat(round((sum(NUMBER_OF_FAILS)/sum(NUMBER_OF_PASS+NUMBER_OF_FAILS+NUMBER_OF_SKIPS))*100,2),'%') as FAIL_RATE,\n" +
                "concat(round((sum(NUMBER_OF_SKIPS)/sum(NUMBER_OF_PASS+NUMBER_OF_FAILS+NUMBER_OF_SKIPS))*100,2),'%') as SKIP_RATE\n" +
                "from builds_data\n" +
                "GROUP BY CNA;";
        StringBuilder toReturn = new StringBuilder();

        try (PreparedStatement preparedStmt = connection.prepareStatement(prest)) {

            ResultSet rs = preparedStmt.executeQuery();

            while (rs.next()) {
                String cna = rs.getString(1);
                String passRate = rs.getString(2);
                String failRate = rs.getString(3);
                String skipRate = rs.getString(4);
                toReturn.append(String.format("<br/>CNA: %s<br/> Pass Rate: %s<br/> Fail Rate: %s<br/> Skip Rate: %s<br/>", cna, passRate, failRate, skipRate));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return toReturn.toString();
    }

    public String getHighestFailENM() {
        createConnection();
        String prest = "select JOB_NAME, sum(NUMBER_OF_FAILS)/(sum(NUMBER_OF_PASS+NUMBER_OF_FAILS+NUMBER_OF_SKIPS)) as FAIL_RATE\n" +
                "from builds_data\n" +
                "group by JOB_NAME;";
        StringBuilder toReturn = new StringBuilder();

        try (PreparedStatement preparedStmt = connection.prepareStatement(prest)) {

            ResultSet rs = preparedStmt.executeQuery();
            String jobName = "";
            double failRate = 0;

            while (rs.next()) {
                if (rs.getDouble(2) > failRate) {
                    jobName = rs.getString(1);
                    failRate = rs.getDouble(2);

                }
            }
            toReturn.append(String.format("<br/>Job Name: %s<br/> Fail Rate: %.2f%%<br/>", jobName, failRate * 100));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return toReturn.toString();
    }

    public String getHighestFailProduct() {
        createConnection();
        String prest = "select PRODUCT, JOB_NAME, sum(NUMBER_OF_FAILS)/(sum(NUMBER_OF_PASS+NUMBER_OF_FAILS+NUMBER_OF_SKIPS)) as FAIL_RATE\n" +
                "from builds_data\n" +
                "group by PRODUCT,JOB_NAME;";
        StringBuilder toReturn = new StringBuilder();

        try (PreparedStatement preparedStmt = connection.prepareStatement(prest)) {

            ResultSet rs = preparedStmt.executeQuery();
            String productName = "";
            String jobName = "";
            double failRate = 0;

            while (rs.next()) {
                if (productName.equals("")) {
                    productName = rs.getString(1);
                    jobName = rs.getString(2);
                    failRate = rs.getDouble(3);
                } else if (productName.equals(rs.getString(1))) {
                    if (rs.getDouble(3) > failRate) {
                        jobName = rs.getString(2);
                        failRate = rs.getDouble(3);
                    }
                } else if (!productName.equals(rs.getString(1))) {
                    toReturn.append(String.format("<br/>Product name: %s<br/> Job Name: %s<br/> Fail Rate: %.2f%%<br/>", productName, jobName, failRate * 100));
                    productName = rs.getString(1);
                    jobName = rs.getString(2);
                    failRate = rs.getDouble(3);
                }
            }
            toReturn.append(String.format("<br/>Product name: %s<br/> Job Name: %s<br/> Fail Rate: %.2f%%<br/>", productName, jobName, failRate * 100));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return toReturn.toString();
    }

    public String getHighestFailCna() {
        createConnection();
        String prest = "select CNA, JOB_NAME,  sum(NUMBER_OF_FAILS)/(sum(NUMBER_OF_PASS+NUMBER_OF_FAILS+NUMBER_OF_SKIPS)) as FAIL_RATE\n" +
                "from builds_data\n" +
                "group by CNA,JOB_NAME;";
        StringBuilder toReturn = new StringBuilder();

        try (PreparedStatement preparedStmt = connection.prepareStatement(prest)) {

            ResultSet rs = preparedStmt.executeQuery();
            String cna = "";
            String jobName = "";
            double failRate = 0;

            while (rs.next()) {
                if (cna.equals("")) {
                    cna = rs.getString(1);
                    jobName = rs.getString(2);
                    failRate = rs.getDouble(3);
                } else if (cna.equals(rs.getString(1))) {
                    if (rs.getDouble(3) > failRate) {
                        jobName = rs.getString(2);
                        failRate = rs.getDouble(3);
                    }
                } else if (!cna.equals(rs.getString(1))) {
                    if (failRate == 0.00) {
                        toReturn.append(String.format("<br/>CNA name: %s<br/> Fail Rate: %.2f%%<br/>", cna, failRate));
                    } else {
                        toReturn.append(String.format("<br/>CNA name: %s<br/> Job Name: %s<br/> Fail Rate: %.2f%%<br/>", cna, jobName, failRate * 100));
                    }
                    cna = rs.getString(1);
                    jobName = rs.getString(2);
                    failRate = rs.getDouble(3);

                }
            }
            toReturn.append(String.format("<br/>CNA name: %s<br/> Job Name: %s<br/> Fail Rate: %.2f%%<br/>", cna, jobName, failRate * 100));

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return toReturn.toString();
    }

}
