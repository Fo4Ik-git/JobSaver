package com.fo4ik.panel;

import com.fo4ik.Main;

import javax.swing.*;
import java.awt.*;

public class TopPanel extends Main {

    public void getTopPanel() {
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JTextField searchField = new JTextField();
        searchField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        JButton searchButton = new JButton("Search");

        topPanel.add(searchField, BorderLayout.CENTER);
        topPanel.add(searchButton, BorderLayout.EAST);

        frame.getContentPane().add(topPanel, BorderLayout.NORTH);
    }

}
