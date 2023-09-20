package com.fo4ik.engine;

import com.fo4ik.config.ColorsConfig;
import com.fo4ik.entity.Job;
import com.fo4ik.panel.ListPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.text.SimpleDateFormat;

public class JobListCellRenderer extends JPanel implements ListCellRenderer<Job> {
    private final JLabel jobTitleAndCompanyLabel = new JLabel();
    private final JLabel addDateLabel = new JLabel();

    public JobListCellRenderer() {
        setLayout(new BorderLayout(10, 10));
        add(jobTitleAndCompanyLabel, BorderLayout.WEST);
        add(addDateLabel, BorderLayout.EAST);
        setPreferredSize(new Dimension(getPreferredSize().width, 50));

    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Job> list, Job job, int index, boolean isSelected, boolean cellHasFocus) {
        String jobTitleAndCompanyText = job.getJobTitle() + " | " + job.getJobCompany();
        jobTitleAndCompanyLabel.setText(jobTitleAndCompanyText);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String addDateText = "Add Date: " + dateFormat.format(job.getAddDate());
        addDateLabel.setText(addDateText);

        setBackgroundForElements(job);
        setBorder(BorderFactory.createMatteBorder(0, 0, 5, 0, ColorsConfig.BACKGROUND_LIGHT));

        if (isSelected) {
            setForeground(list.getSelectionForeground());
        } else {
            setForeground(list.getForeground());
        }

        if (cellHasFocus) setBackground(Color.decode("#000000"));

        return this;
    }

    private void setBackgroundForElements(Job job) {
        switch (job.getStatus()) {
            case 0 -> setBackground(Color.decode("#00FF00"));
            case 1 -> setBackground(Color.decode("#7C7CF9"));
            case 2 -> setBackground(Color.decode("#FFA500"));
            case 3 -> setBackground(Color.decode("#C102C1"));
            case 4 -> setBackground(Color.decode("#009E00"));
            case 5 -> setBackground(Color.decode("#FF0000"));
        }
    }
}