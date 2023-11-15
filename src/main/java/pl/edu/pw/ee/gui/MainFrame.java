package pl.edu.pw.ee.gui;

import lombok.Data;
import lombok.EqualsAndHashCode;
import pl.edu.pw.ee.gui.gamepanel.GamePanel;
import pl.edu.pw.ee.gui.simulationpanel.SimulationPanel;

import javax.swing.*;
import java.awt.*;

@Data
@EqualsAndHashCode(callSuper = false)
public class MainFrame extends JFrame {

    private JPanel mainPanel;
    private final JMenuBar mainMenuBar = new JMenuBar();
    public static final Rectangle MAIN_PANEL_POSITION = new Rectangle(0, 0, 500, 500);

    // Main panels
    private final SimulationPanel simulationPanel = new SimulationPanel();
    private final GamePanel gamePanel = new GamePanel();

    public MainFrame() {
        setName("Mastermind");
        setLayout(null);
        setSize(500, 500);
        setLocationRelativeTo(null);
        setResizable(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        setMainPanel(simulationPanel);
        setupMainMenu();

        add(mainPanel);
        setVisible(true);
    }

    private void setupMainMenu() {
        JMenu gameMenu = new JMenu("Gra");
        JMenuItem gameMenuItem = new JMenuItem("Gra własna");
        gameMenu.add(gameMenuItem);
        gameMenuItem.addActionListener(e -> {
            setMainPanel(gamePanel);
            gamePanel.reset();
        });
        mainMenuBar.add(gameMenu);

        JMenu helpMenu = new JMenu("Pomoc");
        JMenuItem aboutManuItem = new JMenuItem("O programie");
        helpMenu.add(aboutManuItem);
        aboutManuItem.addActionListener(e -> new AboutDialog(this));
        mainMenuBar.add(helpMenu);

        setJMenuBar(mainMenuBar);
    }

    public void setMainPanel(JPanel newPanel) {
        if (mainPanel != null) {
            remove(mainPanel);
        }
        mainPanel = newPanel;
        mainPanel.setBounds(MAIN_PANEL_POSITION);
        add(mainPanel);
        mainPanel.setVisible(true);
        revalidate();
        repaint();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainFrame::new);
    }

}
