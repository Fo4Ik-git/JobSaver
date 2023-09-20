package com.fo4ik.engine;

import com.fo4ik.entity.Job;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;

public class JobListCellRenderer extends JPanel implements ListCellRenderer<Job> {
    private final JLabel jobTitleAndCompanyLabel = new JLabel();
    private final JLabel addDateLabel = new JLabel();

    public JobListCellRenderer() {
        setLayout(new BorderLayout());
        add(jobTitleAndCompanyLabel, BorderLayout.WEST);
        add(addDateLabel, BorderLayout.EAST);
        setPreferredSize(new Dimension(getPreferredSize().width, 50));
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Job> list, Job job, int index, boolean isSelected, boolean cellHasFocus) {
        String jobTitleAndCompanyText = job.getJobTitle() + " | " + job.getJobCompany();
        jobTitleAndCompanyLabel.setText(jobTitleAndCompanyText);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String addDateText = "Add Date: " + dateFormat.format(job.getAddDate());
        addDateLabel.setText(addDateText);

        System.out.println(job.getStatus());

        switch (job.getStatus()) {
            case 0 -> setBackground(Color.orange);
            case 1 -> setBackground(Color.yellow);
            case 2 -> setBackground(Color.green);
            case 3 -> setBackground(Color.darkGray);
            case 4 -> setBackground(Color.red);
        }

        if (isSelected) {
           /* setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());*/
            setForeground(list.getSelectionForeground());
        } else {
            setForeground(list.getForeground());
//            setBackground(list.getBackground());
        }

        return this;
    }
}