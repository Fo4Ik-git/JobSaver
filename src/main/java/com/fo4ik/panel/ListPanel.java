package com.fo4ik.panel;

import com.fo4ik.Main;
import com.fo4ik.actionListners.CustomActionListener;
import com.fo4ik.engine.JobListCellRenderer;
import com.fo4ik.entity.Job;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;

public class ListPanel extends Main {

    public static DefaultListModel<Job> listModel;

    public static JPanel getListFrame(List<Job> jobs) {
        ActionListener ListActionListener = new CustomActionListener();
        listModel = new DefaultListModel<>();
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

        list.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    Job selectedJob = list.getSelectedValue();

                    if (selectedJob != null) {
                        Main.frame.getContentPane().removeAll();
                        Main.frame.getContentPane().add(Main.getTopPanel(),BorderLayout.NORTH);
                        Main.frame.getContentPane().add(WebPanel.getWebFrame(selectedJob));
                        Main.frame.revalidate();
                        Main.frame.repaint();
                    }
                }
            }
        });

        return panel;
    }

   /* private static void createWebFragment(JFrame frame) {

        if (splitPane != null) {
            frame.getContentPane().remove(listFrame);
        }

        webFrame = WebFrame.getWebFrame();
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, listFrame, webFrame);
        splitPane.setDividerLocation(0.3);
        splitPane.setResizeWeight(0.3);

        frame.getContentPane().add(splitPane);

        frame.revalidate();
        frame.repaint();
    }*/
}
