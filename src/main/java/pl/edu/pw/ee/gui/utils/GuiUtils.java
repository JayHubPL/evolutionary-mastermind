package pl.edu.pw.ee.gui.utils;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class GuiUtils {

    private static final GridBagConstraints listConstraints;

    static {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.anchor = GridBagConstraints.NORTHWEST;
        gbc.weightx = 1.0;
        gbc.weighty = 0.0;
        listConstraints = gbc;
    }

    public static void drawColoredDot(Graphics g, Color color, Rectangle rectangle) {
        var g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(color);
        g2d.fillOval(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(2));
        g2d.draw(new Ellipse2D.Double(rectangle.x, rectangle.y, rectangle.width, rectangle.height));
    }

    public static GridBagConstraints getListConstraints() {
        return (GridBagConstraints) listConstraints.clone();
    }

    public static void revalidateAndRepaintLater(Component component) {
        SwingUtilities.invokeLater(() -> {
            component.revalidate();
            component.repaint();
        });
    }

}
