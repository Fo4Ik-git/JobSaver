package com.fo4ik.panel;

import com.fo4ik.Main;
import com.fo4ik.actionListners.CustomActionListener;
import com.fo4ik.config.Config;

import javax.swing.*;
import java.awt.event.ActionListener;

public class MenuPanel extends Main {

    public static JMenuBar getMenu() {

        ActionListener menuListener = new CustomActionListener();


        JMenuBar menuBar = new JMenuBar();
        JMenu settingsMenu = new JMenu(Config.getResourceBundle().getString("menuButtonFile"));
        JMenuItem settingsItem = new JMenuItem(Config.getResourceBundle().getString("menuItemButtonSettings"));
        settingsItem.setActionCommand("Settings");
        settingsItem.addActionListener(menuListener);

        settingsMenu.add(settingsItem);
        menuBar.add(settingsMenu);
        return menuBar;
    }


}
