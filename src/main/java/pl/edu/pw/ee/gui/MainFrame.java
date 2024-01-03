package pl.edu.pw.ee.gui;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.extras.components.FlatButton;
import pl.edu.pw.ee.gui.gamepanel.GameCard;
import pl.edu.pw.ee.gui.simulationpanel.evo.EvoSimulationPanel;
import pl.edu.pw.ee.gui.simulationpanel.knuth.KnuthSimulationPanel;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private final MainPanel mainPanel;

    public MainFrame() {
        mainPanel = new MainPanel();

        setName("Evolutionary Mastermind");
        setTitle("Evolutionary Mastermind");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setLayout(new BorderLayout());
        setSize(1000, 800);

        initializeMenuBar();

        add(mainPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    public static void main(String[] args) {
        FlatDarkLaf.setup();
        SwingUtilities.invokeLater(MainFrame::new);
    }

    private void initializeMenuBar() {
        JMenuBar mainMenuBar = new JMenuBar();

        JMenu gameMenu = new JMenu("Gra");
        JMenuItem gameMenuItem = new JMenuItem("Zasady");
        gameMenu.add(gameMenuItem);
        gameMenuItem.addActionListener(e -> mainPanel.showCard(GameCard.NAME));
        mainMenuBar.add(gameMenu);

        JMenu simulatorManu = new JMenu("Symulator");
        JMenuItem evoAlgorithmMenuItem = new JMenuItem("Algorytm ewolucyjny");
        simulatorManu.add(evoAlgorithmMenuItem);
        evoAlgorithmMenuItem.addActionListener(e -> mainPanel.showCard(EvoSimulationPanel.NAME));
        JMenuItem knuthAlgorithmMenuItem = new JMenuItem("Algorytm Knutha");
        simulatorManu.add(knuthAlgorithmMenuItem);
        knuthAlgorithmMenuItem.addActionListener(e -> mainPanel.showCard(KnuthSimulationPanel.NAME));
        mainMenuBar.add(simulatorManu);

        JMenu helpMenu = new JMenu("Pomoc");
        JMenuItem aboutManuItem = new JMenuItem("O programie");
        helpMenu.add(aboutManuItem);
        aboutManuItem.addActionListener(e -> new AboutDialog(this));
        mainMenuBar.add(helpMenu);

        mainMenuBar.add(Box.createHorizontalGlue());

        var helpButton = new FlatButton();

        helpButton.setButtonType(FlatButton.ButtonType.help);
        helpButton.setFocusable(false);
        helpButton.addActionListener(e -> new AboutDialog(this));
        mainMenuBar.add(helpButton);

        setJMenuBar(mainMenuBar);
    }

    static class MainPanel extends JPanel {

        MainPanel() {
            setLayout(new CardLayout());

            add(new GameCard(), GameCard.NAME);
            add(new EvoSimulationPanel(), EvoSimulationPanel.NAME);
            add(new KnuthSimulationPanel(), KnuthSimulationPanel.NAME);
        }

        void showCard(String cardName) {
            ((CardLayout) getLayout()).show(this, cardName);
        }

    }

}
