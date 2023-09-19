package com.fo4ik.panel;

import com.fo4ik.Main;
import com.fo4ik.entity.Job;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TopPanel extends Main {

    public void getTopPanel() {
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTextField searchField = new JTextField();
        searchField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JPanel buttonsPanel = new JPanel(new GridLayout(1, 2));
        buttonsPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JButton clearButton = new JButton("Clear");

        JButton searchButton = new JButton("Search");
        searchButton.setActionCommand("Search");
        searchButton.addActionListener(e -> {
            String searchQuery = searchField.getText();
            if (!searchQuery.isEmpty()) {
                dbHelper.connect();
                clearButton.setVisible(true);
                List<Job> jobs = dbHelper.searchJobsByTitleOrCompany(searchQuery);
                if (jobs.isEmpty()) {
                    update();
                } else {
                    update(jobs);
                }
                dbHelper.close();
            }
        });

        clearButton.addActionListener(e -> {
            update();
        });

        buttonsPanel.add(searchButton);
        buttonsPanel.add(clearButton);

        topPanel.add(searchField, BorderLayout.CENTER);
        topPanel.add(buttonsPanel, BorderLayout.EAST);

        frame.getContentPane().add(topPanel, BorderLayout.NORTH);
    }

}
