package com.fo4ik.panel.settings;

import com.fo4ik.Main;
import com.fo4ik.config.Config;
import com.fo4ik.engine.rounded.RoundedPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ResourceBundle;

public class SettingsPanel extends Main {

    protected JPanel settingsPanel;
    protected int width, height;

    public void getSettings() {

        width = (int) (frame.getWidth() * 0.5);
        height = (int) (frame.getHeight() * 0.6);

        settingsPanel = new RoundedPanel(20, true);
        settingsPanel.setLayout(new BoxLayout(settingsPanel, BoxLayout.Y_AXIS));

        settingsPanel.setPreferredSize(new Dimension(width, height));
        settingsPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(settingsPanel);
        scrollPane.setOpaque(false);
        scrollPane.setBorder(null);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setViewportView(settingsPanel);

        settingsPanel.add(getAccount());
        settingsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        settingsPanel.add(getGeneral());
        settingsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        settingsPanel.add(getLanguage());

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);

        int settingPanelX = (frame.getWidth() - width) / 2;
        int settingPanelY = (frame.getHeight() - height) / 2;
        centerPanel.setBounds(settingPanelX, settingPanelY, width, height);

        centerPanel.add(scrollPane);

        frame.getContentPane().add(centerPanel);
        frame.getContentPane().setComponentZOrder(centerPanel, 0);
        frame.getContentPane().revalidate();
        frame.getContentPane().repaint();
    }

    private JPanel getAccount() {
        JPanel accountPanel = new RoundedPanel(new BorderLayout(),15);
        accountPanel.setBackground(Color.red);

        JLabel accountLabel = new JLabel("Account");
        accountLabel.setForeground(Color.white);
        accountLabel.setFont(new Font("Arial", Font.BOLD, 20));

        JPanel labelPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        labelPanel.setOpaque(false);
        labelPanel.add(accountLabel);

        accountPanel.add(labelPanel );

        accountPanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        /*accountPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                AccountPanel accountPanel = new AccountPanel();
                accountPanel.getAccount();

                //Messag
            }
        });*/


        return accountPanel;
    }

    private JPanel getGeneral() {
        JPanel generalPanel = new RoundedPanel(new BorderLayout(),15);
        generalPanel.setBackground(Color.yellow);

        JLabel generalLabel = new JLabel("General");
        generalLabel.setForeground(Color.white);
        generalLabel.setFont(new Font("Arial", Font.BOLD, 20));
        generalPanel.add(generalLabel);

        return generalPanel;
    }

    private JPanel getLanguage() {
        JPanel languagePanel = new RoundedPanel(new BorderLayout(),15);
        languagePanel.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        ResourceBundle resourceBundle = Config.RESOURCE_BUNDLE;
        JLabel languageLabel = new JLabel(resourceBundle.getString("settings.languageText"));
        languageLabel.setForeground(Color.black);
        languageLabel.setFont(new Font("Arial", Font.BOLD, 20));

        JLabel userLanguageLabel = new JLabel(resourceBundle.getString("settings.languageDisplay"));
        userLanguageLabel.setForeground(Color.black);

        languagePanel.add(languageLabel, BorderLayout.WEST);
        languagePanel.add(userLanguageLabel, BorderLayout.EAST);

        languagePanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                LanguagePanel languagePanel = new LanguagePanel();
                languagePanel.getLanguage();
            }
        });
        return languagePanel;
    }
}
