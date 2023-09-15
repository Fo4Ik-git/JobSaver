package com.fo4ik.fragments;

import com.fo4ik.databse.DBHelper;
import com.fo4ik.engine.HtmlParser;
import com.fo4ik.entity.Job;

import javax.swing.*;
import java.awt.*;
import java.util.Date;

public class AddJobFragment extends JFrame {

    private static DBHelper dbHelper;

    public AddJobFragment() {
        setTitle("Add Job");

        dbHelper = new DBHelper();

        setSize(800, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        JPanel panel = createPanel();
        add(panel);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel createPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2, 10, 10));

        JLabel linkLabel = new JLabel("Link:");
        JTextField linkField = new JTextField(20);

        JLabel hintLabel = new JLabel();
        hintLabel.setBackground(Color.darkGray);
        hintLabel.setFont(new Font("Serif", Font.BOLD, 20));
        hintLabel.setForeground(Color.red);

        panel.add(linkLabel);
        panel.add(linkField);

        panel.add(hintLabel);

        JButton addButton = new JButton("Add Job");
        addButton.addActionListener(e -> {

            if (linkField.getText().isEmpty()) {
                hintLabel.setText("Please fill all fields");
                return;
            } else {

                Job job = new Job(HtmlParser.getJobTitle(linkField.getText()), HtmlParser.getJobCompany(linkField.getText()), HtmlParser.getHtml(linkField.getText()), new Date());
                dbHelper.connect();
                dbHelper.addJob(job);


                ListFrame.listModel.removeAllElements();
                ListFrame.listModel.addAll(dbHelper.getAllJobs());
                dbHelper.close();
            }

            this.dispose();
        });

        panel.add(addButton);

        panel.setBackground(Color.gray);

        panel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));
        return panel;
    }

}
