package pl.edu.pw.ee.gui;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import pl.edu.pw.ee.game.GameVariant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;

public class GamePanel extends JPanel {

    private JPanel innerPanel;

    // Inner panels
    private final RulesPanel rulesPanel = new RulesPanel();

    public GamePanel() {
        setLayout(null);
        setInnerPanel(rulesPanel);
    }

    private void setInnerPanel(JPanel newPanel) {
        if (innerPanel != null) {
            remove(innerPanel);
        }
        innerPanel = newPanel;
        innerPanel.setBounds(MainFrame.MAIN_PANEL_POSITION);
        add(innerPanel);
        innerPanel.setVisible(true);
        revalidate();
        repaint();
    }

    public class RulesPanel extends JPanel {

        public RulesPanel() {
            setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
            setBackground(Color.green);

            var chooseGameVariantLabel = new JLabel("Wybierz typ gry:");
            chooseGameVariantLabel.setFont(new Font(null, Font.PLAIN, 20));
            var classingGameVariantButton = new JButton("Classic");
            var deluxeGameVariantButton = new JButton("Deluxe");
            classingGameVariantButton.setPreferredSize(new Dimension(150, 60));
            classingGameVariantButton.addActionListener(e -> setInnerPanel(new GameInterfacePanel(GameVariant.classic())));
            deluxeGameVariantButton.setPreferredSize(new Dimension(150, 60));
            deluxeGameVariantButton.addActionListener(e -> setInnerPanel(new GameInterfacePanel(GameVariant.deluxe())));

            add(chooseGameVariantLabel);
            add(classingGameVariantButton);
            add(deluxeGameVariantButton);
        }

    }

    class GameInterfacePanel extends JPanel {

        private final GameVariant gameVariant;

        public GameInterfacePanel(GameVariant gameVariant) {
            this.gameVariant = gameVariant;
            setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
            setBackground(Color.green);
            for (int i = 0; i < gameVariant.getCodeLength(); i++) {
                add(new ColorButton());
            }
            var exitButton = new JButton("Zakończ grę");
            exitButton.setPreferredSize(new Dimension(70, 50));
            exitButton.addActionListener(e -> setInnerPanel(rulesPanel));
            add(exitButton);
        }

        class ColorButton extends JButton {

            public static final Color[] colors = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.CYAN, Color.MAGENTA, Color.ORANGE, Color.PINK};
            private Integer colorIndex;

            public ColorButton() {
                setPreferredSize(new Dimension(50, 50));
                setBorder(BorderFactory.createEmptyBorder());
                setContentAreaFilled(false);
                setBackground(Color.GRAY);
                addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (SwingUtilities.isLeftMouseButton(e)) {
                            cycleColor(Direction.FORWARD);
                        } else if (SwingUtilities.isRightMouseButton(e)) {
                            cycleColor(Direction.BACKWARD);
                        }
                    }
                });
            }

            private void cycleColor(Direction direction) {
                if (colorIndex == null) {
                    colorIndex = direction == Direction.FORWARD ? 0 : gameVariant.getNumberOfColors() - 1;
                } else {
                    colorIndex = colorIndex + direction.getShift();
                    colorIndex = colorIndex < 0 ? gameVariant.getNumberOfColors() - 1 : colorIndex % gameVariant.getNumberOfColors();
                }
                setBackground(colors[colorIndex]);
            }

            @Override
            protected void paintComponent(Graphics g) {
                var g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(getBackground());
                g2d.fillOval(2, 2, getWidth() - 4, getHeight() - 4);
                g2d.setColor(Color.BLACK);
                g2d.setStroke(new BasicStroke(2));
                g2d.draw(new Ellipse2D.Double(2, 2, getWidth() - 4, getHeight() - 4));
                super.paintComponent(g);
            }

            @Override
            public boolean contains(Point p) {
                int radius = Math.min(getWidth(), getHeight()) / 2;
                int centerX = getWidth() / 2;
                int centerY = getHeight() / 2;
                double distance = Math.sqrt(Math.pow(p.x - centerX, 2) + Math.pow(p.y - centerY, 2));
                return distance <= radius;
            }

            @Getter
            @RequiredArgsConstructor
            enum Direction {
                FORWARD(1),
                BACKWARD(-1);

                private final int shift;
            }
        }

    }

}
