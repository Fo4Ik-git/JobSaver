package com.fo4ik.actionListners;

import com.fo4ik.Main;
import com.fo4ik.fragments.AddJobFragment;

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
                AddJobFragment addJobFragment = new AddJobFragment();
                break;
            case "Settings":
                System.out.println("Settings");
                break;
        }
    }
}
