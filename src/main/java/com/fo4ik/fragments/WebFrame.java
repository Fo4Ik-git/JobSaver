package com.fo4ik.fragments;

import com.fo4ik.Main;
import com.fo4ik.entity.Job;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import javax.swing.*;
import java.awt.*;

public class WebFrame {
    private static JTextField jobTitleField;
    private static JTextField jobCompanyField;

    private static JFXPanel jfxPanel;


    public static JPanel getWebFrame(Job job) {
        JPanel panel = new JPanel();
        JPanel webPanel = getWebPanel(job);
        JPanel editPanel = getEditPanel(job);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, editPanel, webPanel);
        splitPane.setDividerLocation(0.5);
        splitPane.setResizeWeight(0.5);

        panel.setLayout(new BorderLayout());
        panel.add(splitPane, BorderLayout.CENTER);

        return panel;
    }

    private static JPanel getEditPanel(Job job) {
        JPanel editPanel = new JPanel();
        editPanel.setLayout(new BoxLayout(editPanel, BoxLayout.Y_AXIS));
        editPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        jobTitleField = new JTextField(job.getJobTitle());
        jobCompanyField = new JTextField(job.getJobCompany());

        //Set max dimension for text fields
        Dimension maxFieldSize = new Dimension(Integer.MAX_VALUE, 100);
        jobTitleField.setMaximumSize(maxFieldSize);
        jobCompanyField.setMaximumSize(maxFieldSize);

        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {

            if (jobTitleField.getText().isEmpty() || jobCompanyField.getText().isEmpty()) {
                JOptionPane.showMessageDialog(null, "Please fill all fields");
                return;
            }

            Main.frame.getContentPane().removeAll();
            Main.frame.getContentPane().add(Main.getTopPanel(), BorderLayout.NORTH);
            Main.frame.getContentPane().add(Main.listFrame);
            Main.frame.revalidate();
            Main.frame.repaint();
        });

        JButton saveButton = new JButton("Save");
        saveButton.setBackground(Color.green);
        JButton deleteButton = new JButton("Delete");
        deleteButton.setBackground(Color.red);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.add(backButton);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(saveButton);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(deleteButton);


        editPanel.add(new JLabel("Job Title:"));
        editPanel.add(jobTitleField);
        editPanel.add(new JLabel("Job Company:"));
        editPanel.add(jobCompanyField);
        editPanel.add(Box.createVerticalStrut(10));
        editPanel.add(buttonPanel);

        return editPanel;
    }

    private static JPanel getWebPanel(Job job) {
        JPanel webPanel = new JPanel();
        jfxPanel = new JFXPanel();
        webPanel.setLayout(new BorderLayout());
        webPanel.add(jfxPanel, BorderLayout.CENTER);

        // Используем Timer для задержки загрузки страницы
        Platform.runLater(() -> {
            System.out.println("Loading Web Page");
            WebView webView = new WebView();
            WebEngine webEngine = webView.getEngine();

            // Html file to string
            webEngine.loadContent(job.getHtml());

            StackPane stackPane = new StackPane();
            stackPane.getChildren().add(webView);
            Scene scene = new Scene(stackPane, 800, 600);
            jfxPanel.setScene(scene);
        });

        return webPanel;
    }
}
