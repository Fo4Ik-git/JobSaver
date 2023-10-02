package com.fo4ik.jobsaver.database;

import com.fo4ik.jobsaver.entity.Job;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
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

    public boolean doesColumnExist(String columnName) {
        try (Statement statement = connection.createStatement()) {
            String query = "PRAGMA table_info(jobs)";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String existingColumnName = resultSet.getString("name");
                if (columnName.equals(existingColumnName)) {
                    return true; // The column exists
                }
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return false;
    }

    public void createTable() {
        String alertCss = "ALTER TABLE jobs ADD COLUMN css TEXT";
        String alertFolder = "ALTER TABLE jobs ADD COLUMN folder TEXT";

        String createTableSQL = "CREATE TABLE IF NOT EXISTS jobs (id INTEGER PRIMARY KEY AUTOINCREMENT, jobTitle TEXT, jobCompany TEXT, html TEXT, css TEXT, folder TEXT,  addDate DATE, status INT)";

        try (Statement statement = connection.createStatement()) {
            statement.execute(createTableSQL);
            if (!doesColumnExist("css")) {
                statement.execute(alertCss);
            }
            if (!doesColumnExist("folder")) {
                statement.execute(alertFolder);
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

    public void addJob(Job job) {
        String insertSQL = "INSERT INTO jobs (jobTitle, jobCompany, html, css, folder, addDate, status) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = this.connection.prepareStatement(insertSQL)) {
            gerPreparedStatment(job, preparedStatement);
            preparedStatement.executeUpdate();
            notifyDatabaseChange();
        } catch (SQLException e) {
            System.out.println("Error adding job to the database: " + e.getMessage());
        }

    }

    public void updateJob(Job job) {
        String updateSQL = "UPDATE jobs SET jobTitle = ?, jobCompany = ?, html = ?, css = ?, folder = ?, addDate = ? , status = ? WHERE id = ?";

        try (PreparedStatement preparedStatement = this.connection.prepareStatement(updateSQL)) {
            renameFolder(job);
            gerPreparedStatment(job, preparedStatement);
            preparedStatement.setInt(8, job.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error updating job in the database: " + e.getMessage());
        }

    }

    private void gerPreparedStatment(Job job, PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, job.getJobTitle());
        preparedStatement.setString(2, job.getJobCompany());
        preparedStatement.setString(3, job.getHtml());
        preparedStatement.setString(4, job.getCss());
        preparedStatement.setString(5, job.getFolder());
        preparedStatement.setDate(6, new Date(job.getAddDate().getTime()));
        preparedStatement.setInt(7, job.getStatus());
    }

    public void deleteJob(Job job) {
        String deleteSQL = "DELETE FROM jobs WHERE id = ?";
        try (PreparedStatement preparedStatement = this.connection.prepareStatement(deleteSQL)) {
            //Delete folder
            deleteFolder(job);

            preparedStatement.setInt(1, job.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error deleting job from the database: " + e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void deleteFolder(Job job) throws IOException {
        String jobFolder = job.getHtml().substring(0, job.getHtml().lastIndexOf("/"));
        File file = new File(jobFolder);
        FileUtils.deleteDirectory(file);
    }

    public void renameFolder(Job job) {
        if (job.getFolder() != null) {
            Path path = Paths.get(job.getFolder());
            String folderName = path.toString().substring(5);
            String[] folderNameArray = folderName.split("-");
            String newFolderName = "data/" + job.getJobTitle() + "-" + job.getJobCompany() + "-" + folderNameArray[2];
            File file = new File(path.toString());
            file.renameTo(new File(newFolderName));
            if (job.getFolder().equals(newFolderName)) return;
            job.setFolder(newFolderName);
            job.setHtml(newFolderName + "/job.html");
            if (job.getCss() != null) job.setCss(newFolderName + "/css.css");
            updateJob(job);
        } else {
            job.setFolder(parseJobFolder(job));
            updateJob(job);
        }

    }

    private String parseJobFolder(Job job) {
        String jobFolder = "data/" + job.getJobTitle() + "-" + job.getJobCompany() + "-undefined";
        File folder = new File(jobFolder);
        if (folder.exists()) {
            int i = 1;
            while (folder.exists()) {
                folder = new File(jobFolder + i);
                i++;
            }
            jobFolder = jobFolder + "-" + i;
        }
        return folder.getPath();
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
            String css = resultSet.getString("css");
            String folder = resultSet.getString("folder");
            Date addDate = resultSet.getDate("addDate");
            int status = resultSet.getInt("status");
            Job job = new Job(id, jobTitle, jobCompany, html, css, folder, addDate, status);
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

    private void notifyDatabaseChange() {
        Iterator var1 = this.listeners.iterator();

        while (var1.hasNext()) {
            DatabaseChangeListener listener = (DatabaseChangeListener) var1.next();
            listener.onDatabaseChange();
        }

    }
}
