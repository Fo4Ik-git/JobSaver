package com.fo4ik.entity;

import java.util.Date;

public class Job {

    private int id;
    private String JobTitle, JobCompany, html;
    private Date addDate;


    public Job(int id, String jobTitle, String jobCompany, String html, Date addDate) {
        this.id = id;
        this.JobTitle = jobTitle;
        this.JobCompany = jobCompany;
        this.html = html;
        this.addDate = addDate;
    }

    public Job(String jobTitle, String jobCompany, String html, Date addDate) {
        JobTitle = jobTitle;
        JobCompany = jobCompany;
        this.html = html;
        this.addDate = addDate;
    }

    public Job() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJobTitle() {
        return JobTitle;
    }

    public void setJobTitle(String jobTitle) {
        JobTitle = jobTitle;
    }

    public String getJobCompany() {
        return JobCompany;
    }

    public void setJobCompany(String jobCompany) {
        JobCompany = jobCompany;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public Date getAddDate() {
        return addDate;
    }

    public void setAddDate(Date addDate) {
        this.addDate = addDate;
    }
}
