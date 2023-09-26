package com.fo4ik.engine.render;

import com.fo4ik.engine.rounded.RoundedPanel;
import com.fo4ik.entity.Job;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;

public class JobListCellRenderer extends DefaultListCellRenderer {
    private final JLabel jobTitleAndCompanyLabel = new JLabel();
    private final JLabel addDateLabel = new JLabel();

    public JobListCellRenderer() {
        setLayout(new BorderLayout(10, 10));
        add(jobTitleAndCompanyLabel, BorderLayout.WEST);
        add(addDateLabel, BorderLayout.EAST);
        setPreferredSize(new Dimension(getPreferredSize().width, 50));
    }

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        if (!(value instanceof Job)) {
            return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        }

        Job job = (Job) value;
        JPanel panel = new RoundedPanel(new BorderLayout(10, 10), 10, true);
        panel.setPreferredSize(new Dimension(panel.getPreferredSize().width, 50));

        String jobTitleAndCompanyText = job.getJobTitle() + " | " + job.getJobCompany();
        jobTitleAndCompanyLabel.setText(jobTitleAndCompanyText);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String addDateText = "Add Date: " + dateFormat.format(job.getAddDate());
        addDateLabel.setText(addDateText);

        panel.add(jobTitleAndCompanyLabel, BorderLayout.WEST);
        panel.add(addDateLabel, BorderLayout.EAST);

        setBackgroundForElements(panel, job);

        if (isSelected) {
            panel.setForeground(list.getSelectionForeground());
            panel.setBackground(list.getSelectionBackground());
        }
        return panel;
    }

    private void setBackgroundForElements(JPanel panel, Job job) {
        switch (job.getStatus()) {
            case 0 -> panel.setBackground(Color.decode("#00FF00"));
            case 1 -> panel.setBackground(Color.decode("#7C7CF9"));
            case 2 -> panel.setBackground(Color.decode("#FFA500"));
            case 3 -> panel.setBackground(Color.decode("#C102C1"));
            case 4 -> panel.setBackground(Color.decode("#009E00"));
            case 5 -> panel.setBackground(Color.decode("#FF0000"));
            default -> panel.setBackground(Color.WHITE); // ?? ?????????
        }
    }
}