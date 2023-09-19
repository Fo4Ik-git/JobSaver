package com.fo4ik.actionListner;

import com.fo4ik.Main;
import com.fo4ik.window.AddJobWindow;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CustomActionListener extends Main implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "Add job":
                AddJobWindow addJobWindow = new AddJobWindow();
                break;
        }
    }
}
