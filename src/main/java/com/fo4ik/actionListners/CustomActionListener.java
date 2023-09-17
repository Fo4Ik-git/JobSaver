package com.fo4ik.actionListners;

import com.fo4ik.Main;
import com.fo4ik.window.AddJobWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomActionListener implements ActionListener {

    private JFrame mainFrame;

    public CustomActionListener() {
        mainFrame = Main.frame;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Add job":
                AddJobWindow addJobFragment = new AddJobWindow();
                break;
            case "Settings":
                System.out.println("Settings");
                break;
        }
    }
}
