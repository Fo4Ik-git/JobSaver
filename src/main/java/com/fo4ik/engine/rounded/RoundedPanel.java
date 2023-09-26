package com.fo4ik.engine.rounded;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class RoundedPanel extends JPanel {

    private int cornerRadius;
    private boolean isBorderPainted = false;

    public RoundedPanel(LayoutManager layout, int cornerRadius) {
        super(layout);
        this.cornerRadius = cornerRadius;
        setOpaque(false);
    }

    public RoundedPanel(int cornerRadius) {
        super();
        this.cornerRadius = cornerRadius;
        setOpaque(false);
    }

    public RoundedPanel(LayoutManager layout, int cornerRadius, boolean isBorderPainted) {
        super(layout);
        this.cornerRadius = cornerRadius;
        this.isBorderPainted = isBorderPainted;
        setOpaque(false);
    }

    public RoundedPanel(int cornerRadius, boolean isBorderPainted) {
        this.cornerRadius = cornerRadius;
        this.isBorderPainted = isBorderPainted;
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Dimension arcs = new Dimension(cornerRadius, cornerRadius);
        int width = getWidth();
        int height = getHeight();
        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        graphics.setClip(0, 0, width, height);
        graphics.setColor(getBackground());
        graphics.fillRoundRect(0, 0, width - 1, height - 1, arcs.width, arcs.height);
        graphics.setColor(getForeground());
//
        if(isBorderPainted) {
            graphics.drawRoundRect(0, 0, width - 1, height - 1, arcs.width, arcs.height);
        }
    }

}
