package com.fo4ik.panel.settings;

import com.fo4ik.config.Config;
import com.fo4ik.engine.render.LanguageListCellRenderer;

import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;

public class LanguagePanel extends SettingsPanel {

    public void getLanguage() {
        ResourceBundle resourceBundle = Config.getResourceBundle();
        JPanel languagePanel = new JPanel(new BorderLayout());
//Top panel
        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel languageLabel = new JLabel(resourceBundle.getString("settings.chooseLanguage"));
        JTextField languageField = new JTextField(10);
        topPanel.add(languageLabel, BorderLayout.WEST);
        topPanel.add(languageField, BorderLayout.CENTER);
        languagePanel.add(topPanel, BorderLayout.NORTH);

        //Center panel
        DefaultListModel<String> languageListModel = new DefaultListModel<>();
        for (String languageName : Config.SUPPORTED_LANGUAGES_MAP.values()) {
            languageListModel.addElement(languageName);
        }
        JList<String> languageList = new JList<>(languageListModel);
        languageList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        languageList.setCellRenderer(new LanguageListCellRenderer());
        JScrollPane scrollPane = new JScrollPane(languageList);
        languagePanel.add(scrollPane, BorderLayout.CENTER);

        //Bottom panel
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton returnButton = new JButton(resourceBundle.getString("button.back"));
        JButton applyButton = new JButton(resourceBundle.getString("button.apply"));
        bottomPanel.add(returnButton);
        bottomPanel.add(applyButton);
        languagePanel.add(bottomPanel, BorderLayout.SOUTH);

        frame.getContentPane().removeAll();
        frame.getContentPane().add(languagePanel);
        frame.getContentPane().repaint();
        frame.getContentPane().revalidate();
    }

}
