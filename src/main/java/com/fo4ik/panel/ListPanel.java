package com.fo4ik.panel;

import com.fo4ik.Main;
import com.fo4ik.actionListner.CustomActionListener;
import com.fo4ik.config.ColorsConfig;
import com.fo4ik.engine.render.JobListCellRenderer;
import com.fo4ik.entity.Job;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Date;
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

    public void getListPanel() {
        listModel.addAll(jobs);
        listModel.add(0, new Job("Job ??????", "????????", "Job Location", new Date(), 0));

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(10, 5, 10, 5));

        JButton addJobButton = new JButton("Добавить работу");
        addJobButton.setFont(new Font("Arial", Font.PLAIN, 16));
        addJobButton.setActionCommand("Add job");
        addJobButton.addActionListener(new CustomActionListener());
        panel.add(addJobButton, BorderLayout.NORTH);

        JList<Job> list = new JList<>(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.setCellRenderer(new JobListCellRenderer());

        JScrollPane scrollPane = new JScrollPane(list);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        scrollPane.setBackground(ColorsConfig.BACKGROUND_LIGHT);
        panel.add(scrollPane, BorderLayout.CENTER);

        list.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int selectedIndex = list.locationToIndex(e.getPoint());
                if (selectedIndex >= 0 && selectedIndex < list.getModel().getSize()) {
                    Rectangle bounds = list.getCellBounds(selectedIndex, selectedIndex);
                    if (bounds != null && bounds.contains(e.getPoint())) {
                        Job selectedJob = list.getModel().getElementAt(selectedIndex);
                        if (selectedJob != null) {
                            WebPanel webPanel = new WebPanel();
                            webPanel.getWebPanel(selectedJob);
                        }
                    }
                }
            }
        });


        frame.getContentPane().add(panel);
        frame.getContentPane().revalidate();
        frame.getContentPane().repaint();
    }
}
