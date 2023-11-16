package pl.edu.pw.ee.gui.utils;

import javax.swing.*;
import java.awt.*;

public class ColorDot extends JComponent {

    public ColorDot(Color color, Integer size) {
        setBackground(color);
        setPreferredSize(new Dimension(size, size));
    }

    @Override
    protected void paintComponent(Graphics g) {
        GuiUtils.drawColoredDot(g, getBackground(), new Rectangle(2, 2, getWidth() - 4, getHeight() - 4));
        super.paintComponent(g);
    }

    @Override
    public boolean contains(int x, int y) {
        int radius = Math.min(getWidth(), getHeight()) / 2;
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        double distance = Math.sqrt(Math.pow(x - centerX, 2) + Math.pow(y - centerY, 2));
        return distance <= radius;
    }

}
