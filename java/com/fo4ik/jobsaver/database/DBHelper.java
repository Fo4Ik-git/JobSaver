package com.fo4ik.jobsaver.database;

import com.fo4ik.jobsaver.entity.Job;

import java.sql.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class DBHelper {
    private static DBHelper instance;
    private Connection connection;
    private List<DatabaseChangeListener> listeners = new ArrayList();

    public DBHelper() {
    }

    public static DBHelper getInstance() {
        if (instance == null) {
            instance = new DBHelper();
        }

        return instance;
    }

    public void connect() {
        try {
            this.connection = DriverManager.getConnection("jdbc:sqlite:database.db");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

    public void createTable() {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS jobs (id INTEGER PRIMARY KEY AUTOINCREMENT,jobTitle TEXT,jobCompany TEXT,html TEXT,addDate DATE,status INT)";

        try (Statement statement = connection.createStatement()) {
            statement.execute(createTableSQL);
        } catch (SQLException var7) {
            System.out.println("Error: " + var7.getMessage());
        }

    }

    public void addJob(Job job) {
        String insertSQL = "INSERT INTO jobs (jobTitle, jobCompany, html, addDate, status) VALUES (?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = this.connection.prepareStatement(insertSQL)) {
            this.gerPreparedStatment(job, preparedStatement);
            preparedStatement.executeUpdate();
            this.notifyDatabaseChange();
        } catch (SQLException e) {
            System.out.println("Error adding job to the database: " + e.getMessage());
        }

    }

    public void updateJob(Job job) {
        String updateSQL = "UPDATE jobs SET jobTitle = ?, jobCompany = ?, html = ?, addDate = ? , status = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = this.connection.prepareStatement(updateSQL)) {
            gerPreparedStatment(job, preparedStatement);
            preparedStatement.setInt(6, job.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error updating job in the database: " + e.getMessage());
        }

    }

    private void gerPreparedStatment(Job job, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, job.getJobTitle());
        preparedStatement.setString(2, job.getJobCompany());
        preparedStatement.setString(3, job.getHtml());
        preparedStatement.setDate(4, new Date(job.getAddDate().getTime()));
        preparedStatement.setInt(5, job.getStatus());
    }

    public void deleteJob(Job job) {
        String deleteSQL = "DELETE FROM jobs WHERE id = ?";

        try (PreparedStatement preparedStatement = this.connection.prepareStatement(deleteSQL)) {
            preparedStatement.setInt(1, job.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error deleting job from the database: " + e.getMessage());
        }

    }

    public List<Job> getAllJobs() {
        List<Job> jobs = new ArrayList();
        String selectSQL = "SELECT * FROM jobs";

        try (PreparedStatement preparedStatement = this.connection.prepareStatement(selectSQL)) {
            getJobResult(jobs, preparedStatement);
        } catch (SQLException e) {
            System.out.println("Error fetching jobs from the database: " + e.getMessage());
        }

        return jobs;
    }

    //find Job by title or company / title and company
    public List<Job> searchJobsByTitleAndCompany(String title, String company) {
        List<Job> matchingJobs = new ArrayList();
        String searchSQL = "SELECT * FROM jobs WHERE jobTitle LIKE ? AND jobCompany LIKE ?";

        try (PreparedStatement preparedStatement = this.connection.prepareStatement(searchSQL)) {
            String searchTitle = "%" + title + "%";
            String searchCompany = "%" + company + "%";
            preparedStatement.setString(1, searchTitle);
            preparedStatement.setString(2, searchCompany);
            getJobResult(matchingJobs, preparedStatement);
        } catch (SQLException e) {
            System.out.println("Error searching jobs in the database: " + e.getMessage());
        }

        return matchingJobs;
    }

    public List<Job> searchJobsByTitleOrCompany(String keyword) {
        List<Job> matchingJobs = new ArrayList();
        String searchSQL = "SELECT * FROM jobs WHERE jobTitle LIKE ? OR jobCompany LIKE ?";

        try (PreparedStatement preparedStatement = this.connection.prepareStatement(searchSQL)) {
            String searchKeyword = "%" + keyword + "%";
            preparedStatement.setString(1, searchKeyword);
            preparedStatement.setString(2, searchKeyword);
            getJobResult(matchingJobs, preparedStatement);
        } catch (SQLException e) {
            System.out.println("Error searching jobs in the database: " + e.getMessage());
        }

        return matchingJobs;
    }

    private void getJobResult(List<Job> jobs, PreparedStatement preparedStatement) throws SQLException {
        ResultSet resultSet = preparedStatement.executeQuery();

        while (resultSet.next()) {
            int id = resultSet.getInt("id");
            String jobTitle = resultSet.getString("jobTitle");
            String jobCompany = resultSet.getString("jobCompany");
            String html = resultSet.getString("html");
            Date addDate = resultSet.getDate("addDate");
            int status = resultSet.getInt("status");
            Job job = new Job(id, jobTitle, jobCompany, html, addDate, status);
            jobs.add(job);
        }

    }

    public void close() {
        try {
            if (this.connection != null) {
                this.connection.close();
            }
        } catch (SQLException var2) {
            System.out.println("Error: " + var2.getMessage());
        }

    }

    public void registerDatabaseChangeListener(DatabaseChangeListener listener) {
        this.listeners.add(listener);
    }

    public void removeDatabaseChangeListener(DatabaseChangeListener listener) {
        this.listeners.remove(listener);
    }

    private void notifyDatabaseChange() {
        Iterator var1 = this.listeners.iterator();

        while (var1.hasNext()) {
            DatabaseChangeListener listener = (DatabaseChangeListener) var1.next();
            listener.onDatabaseChange();
        }

    }
}
