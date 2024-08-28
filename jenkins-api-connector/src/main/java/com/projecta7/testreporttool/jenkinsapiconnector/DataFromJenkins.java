package com.projecta7.testreporttool.jenkinsapiconnector;


import java.util.Date;


public class DataFromJenkins {

    private String cra;
    private String cna;
    private String lineManager;
    private String job;
    private String buildResult;
    private int pass = 0;
    private int fail = 0;
    private int skip = 0;
    private Date d;


    public DataFromJenkins(String cra, String cna, String lineManager, String job) {
        this.cra = cra;
        this.cna = cna;
        this.lineManager = lineManager;
        this.job = job;
    }


    public String getCra() {
        return cra;
    }


    public void setCra(String cra) {
        this.cra = cra;
    }


    public String getCna() {
        return cna;
    }


    public void setCna(String cna) {
        this.cna = cna;
    }


    public String getLineManager() {
        return lineManager;
    }


    public void setLineManager(String lineManager) {
        this.lineManager = lineManager;
    }


    public int getPass() {
        return pass;
    }


    public void setPass(int pass) {
        this.pass = pass;
    }


    public int getFail() {
        return fail;
    }


    public void setFail(int fail) {
        this.fail = fail;
    }


    public int getSkip() {
        return skip;
    }


    public void setSkip(int skip) {
        this.skip = skip;
    }


    public String getJob() {
        return job;
    }


    public void setJob(String job) {
        this.job = job;
    }


    public String getBuildResult() {
        return buildResult;
    }


    public void setBuildResult(String buildResult) {
        this.buildResult = buildResult;
    }


    public Date getD() {
        return d;
    }


    public void setD(Date d) {
        this.d = d;
    }

}
