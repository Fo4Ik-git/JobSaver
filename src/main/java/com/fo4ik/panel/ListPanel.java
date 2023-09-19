package com.fo4ik.panel;

import com.fo4ik.Main;
import com.fo4ik.actionListner.CustomActionListener;
import com.fo4ik.engine.JobListCellRenderer;
import com.fo4ik.entity.Job;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ListPanel extends Main {

    private final DefaultListModel<Job> listModel;
    private final List<Job> jobs;
    public ListPanel() {
        dbHelper.connect();
        this.listModel = new DefaultListModel<>();
        this.jobs = dbHelper.getAllJobs();
        dbHelper.close();
    }
    public ListPanel(List<Job> jobs) {
        this.listModel = new DefaultListModel<>();
        this.jobs = jobs;
    }

    public void getListPanel(){
        listModel.addAll(jobs);

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.black);
        panel.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));

        JButton addJobButton = new JButton("Add job");
        addJobButton.setActionCommand("Add job");
        addJobButton.addActionListener(new CustomActionListener());
        panel.add(addJobButton, BorderLayout.NORTH);

        JList<Job> list = new JList<>(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setCellRenderer(new JobListCellRenderer());

        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        scrollPane.setBackground(Color.white);
        panel.add(scrollPane, BorderLayout.CENTER);

        list.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Job selectedJob = list.getSelectedValue();

                if (selectedJob != null) {
                    WebPanel webPanel = new WebPanel();
                    webPanel.getWebPanel(selectedJob);
                }
            }
        });

        frame.getContentPane().add(panel);
    }
}
