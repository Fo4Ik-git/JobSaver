package com.fo4ik.panel;

import com.fo4ik.Main;
import com.fo4ik.actionListner.CustomActionListener;

import javax.swing.*;

public class MenuPanel extends Main {

    public void getMenu() {
        JMenuBar menuBar = new JMenuBar();
        JMenu menu = new JMenu("Menu");
        menuBar.add(menu);

        JMenuItem settings = new JMenuItem("Settings");
        settings.setActionCommand("Settings");
        settings.addActionListener(new CustomActionListener());
        menu.add(settings);

        frame.setJMenuBar(menuBar);
    }

}
