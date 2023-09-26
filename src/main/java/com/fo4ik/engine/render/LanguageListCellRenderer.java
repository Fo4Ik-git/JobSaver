package com.fo4ik.engine.render;

import javax.swing.*;
import java.awt.*;

public class LanguageListCellRenderer extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        Component renderer = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if (renderer instanceof JLabel) {
            JLabel label = (JLabel) renderer;
            label.setFont(new Font("Arial", Font.PLAIN, 16));
            label.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        }
        return renderer;
    }

}
