package pl.edu.pw.ee.gui.utils;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class GuiUtils {

    public static void drawColoredDot(Graphics g, Color color, Rectangle rectangle) {
        var g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(color);
        g2d.fillOval(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
        g2d.setColor(Color.BLACK);
        g2d.setStroke(new BasicStroke(2));
        g2d.draw(new Ellipse2D.Double(rectangle.x, rectangle.y, rectangle.width, rectangle.height));
    }

}
