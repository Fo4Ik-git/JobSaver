package com.fo4ik.databse;

import com.fo4ik.config.Config;
import com.fo4ik.entity.Job;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DBHelper {

    private Connection connection;

    public void connect() {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + Config.APP_DATABASE_NAME);
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void createTable() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS jobs (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "jobTitle TEXT," +
                "jobCompany TEXT," +
                "html TEXT," +
                "addDate DATE" +
                ")";
        try (Statement statement = connection.createStatement()) {
            statement.execute(createTableSQL);
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void addJob(Job job) {
        String insertSQL = "INSERT INTO jobs (jobTitle, jobCompany, html, addDate) VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(insertSQL)) {
            preparedStatement.setString(1, job.getJobTitle());
            preparedStatement.setString(2, job.getJobCompany());
            preparedStatement.setString(3, job.getHtml());
            preparedStatement.setDate(4, new java.sql.Date(job.getAddDate().getTime()));
        } catch (SQLException e) {
            System.out.println("Error adding job to the database: " + e.getMessage());
        }
    }

    //Create method to update job
    public void updateJob(Job job) {
        String updateSQL = "UPDATE jobs SET jobTitle = ?, jobCompany = ?, html = ?, addDate = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(updateSQL)) {
            preparedStatement.setString(1, job.getJobTitle());
            preparedStatement.setString(2, job.getJobCompany());
            preparedStatement.setString(3, job.getHtml());
            preparedStatement.setDate(4, new java.sql.Date(job.getAddDate().getTime()));
            preparedStatement.setInt(5, job.getId());
        } catch (SQLException e) {
            System.out.println("Error updating job in the database: " + e.getMessage());
        }
    }

    public List<Job> getAllJobs() {
        List<Job> jobs = new ArrayList<>();

        String selectSQL = "SELECT * FROM jobs";

        try (PreparedStatement preparedStatement = connection.prepareStatement(selectSQL)) {
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String jobTitle = resultSet.getString("jobTitle");
                String jobCompany = resultSet.getString("jobCompany");
                String html = resultSet.getString("html");
                Date addDate = resultSet.getDate("addDate");

                Job job = new Job(id, jobTitle, jobCompany, html, addDate);
                jobs.add(job);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching jobs from the database: " + e.getMessage());
        }

        return jobs;
    }

    public void close() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

}
