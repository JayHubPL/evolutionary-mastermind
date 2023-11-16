package pl.edu.pw.ee.gui;

import pl.edu.pw.ee.gui.gamepanel.GameCard;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private final MainPanel mainPanel;

    public MainFrame() {
        mainPanel = new MainPanel();

        setName("Mastermind");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setLayout(new BorderLayout());
        setSize(500, 500);
        setBackground(Color.YELLOW); // debug

        initializeMenuBar();

        add(mainPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainFrame::new);
    }

    private void initializeMenuBar() {
        JMenuBar mainMenuBar = new JMenuBar();

        JMenu gameMenu = new JMenu("Gra");
        JMenuItem gameMenuItem = new JMenuItem("Gra własna");
        gameMenu.add(gameMenuItem);
        gameMenuItem.addActionListener(e -> mainPanel.showCard(GameCard.NAME));
        mainMenuBar.add(gameMenu);

        JMenu helpMenu = new JMenu("Pomoc");
        JMenuItem aboutManuItem = new JMenuItem("O programie");
        helpMenu.add(aboutManuItem);
        aboutManuItem.addActionListener(e -> new AboutDialog(this));
        mainMenuBar.add(helpMenu);

        setJMenuBar(mainMenuBar);
    }

    static class MainPanel extends JPanel {

        MainPanel() {
            setLayout(new CardLayout());
            setBackground(Color.MAGENTA); // debug

            add(new GameCard(), GameCard.NAME);
        }

        void showCard(String cardName) {
            ((CardLayout) getLayout()).show(this, cardName);
        }

    }

}
