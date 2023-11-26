package pl.edu.pw.ee.gui.gamepanel;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.edu.pw.ee.gui.utils.GuiUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ColorButton extends JButton {

    private final int numberOfColors;
    @Getter
    private Integer colorIndex;

    public ColorButton(int numberOfColors, int size) {
        this.numberOfColors = numberOfColors;
        setPreferredSize(new Dimension(size, size));
        setMinimumSize(new Dimension(size, size));
        setBorder(BorderFactory.createEmptyBorder());
        setContentAreaFilled(false);
        setBackground(Color.GRAY);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    cycleColor(Direction.FORWARD);
                } else if (SwingUtilities.isRightMouseButton(e)) {
                    cycleColor(Direction.BACKWARD);
                }
            }
        });
    }

    public void setColorIndex(Integer colorIndex) {
        this.colorIndex = colorIndex;
        repaint();
    }

    private void cycleColor(Direction direction) {
        if (colorIndex == null) {
            colorIndex = direction == Direction.FORWARD ? 0 : numberOfColors - 1;
        } else {
            colorIndex = colorIndex + direction.getShift();
            colorIndex = colorIndex < 0 ? numberOfColors - 1 : colorIndex % numberOfColors;
        }
        setBackground(GamePanel.COLORS[colorIndex]);
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

    @Override
    public void repaint() {
        setBackground(colorIndex == null ? Color.GRAY : GamePanel.COLORS[colorIndex]);
        super.repaint();
    }

    @Getter
    @RequiredArgsConstructor
    enum Direction {
        FORWARD(1),
        BACKWARD(-1);

        private final int shift;
    }
}
