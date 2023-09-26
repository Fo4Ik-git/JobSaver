package com.fo4ik.jobsaver.entity;

import java.util.Date;

public class Job {
    private int id;
    private String JobTitle;
    private String JobCompany;
    private String html;
    private int status;
    private Date addDate;

    public Job(int id, String jobTitle, String jobCompany, String html, Date addDate, int status) {
        this.id = id;
        this.JobTitle = jobTitle;
        this.JobCompany = jobCompany;
        this.html = html;
        this.addDate = addDate;
        this.status = status;
    }

    public Job(String jobTitle, String jobCompany, String html, Date addDate, int status) {
        this.JobTitle = jobTitle;
        this.JobCompany = jobCompany;
        this.html = html;
        this.addDate = addDate;
        this.status = status;
    }

    public Job() {
    }

    public int getId() {
        return this.id;
    }

    public String getJobTitle() {
        return this.JobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.JobTitle = jobTitle;
    }

    public String getJobCompany() {
        return this.JobCompany;
    }

    public void setJobCompany(String jobCompany) {
        this.JobCompany = jobCompany;
    }

    public String getHtml() {
        return this.html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public Date getAddDate() {
        return this.addDate;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
