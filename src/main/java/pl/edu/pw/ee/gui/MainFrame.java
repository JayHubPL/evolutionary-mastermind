package pl.edu.pw.ee.gui;

import com.formdev.flatlaf.FlatDarkLaf;
import com.formdev.flatlaf.extras.components.FlatButton;
import pl.edu.pw.ee.game.GameVariant;
import pl.edu.pw.ee.gui.gamepanel.GamePanel;
import pl.edu.pw.ee.gui.gamepanel.RulesPanel;
import pl.edu.pw.ee.gui.simulationpanel.evo.EvoSimulationPanel;
import pl.edu.pw.ee.gui.simulationpanel.knuth.KnuthSimulationPanel;
import pl.edu.pw.ee.gui.utils.NonClosingCheckBoxMenuItem;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private final MainPanel mainPanel;
    private final NonClosingCheckBoxMenuItem duplicatesAllowedCheckboxMenuItem;

    public MainFrame() {
        mainPanel = new MainPanel();
        duplicatesAllowedCheckboxMenuItem = new NonClosingCheckBoxMenuItem("Dozwolone powtórzenia w haśle", true);

        setName("Evolutionary Mastermind");
        setTitle("Evolutionary Mastermind");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());
        setSize(710, 800);

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
        JMenuItem rulesMenuItem = new JMenuItem("Zasady");
        gameMenu.add(rulesMenuItem);
        rulesMenuItem.addActionListener(e -> mainPanel.showCard(RulesPanel.NAME));
        gameMenu.add(new JSeparator());
        var classicGameMenuItem = new JMenuItem("Classic");
        classicGameMenuItem.addActionListener(e -> mainPanel.showCard(GamePanel.NAME_CLASSIC));
        gameMenu.add(classicGameMenuItem);
        var deluxeGameMenuItem = new JMenuItem("Deluxe");
        deluxeGameMenuItem.addActionListener(e -> mainPanel.showCard(GamePanel.NAME_DELUXE));
        gameMenu.add(deluxeGameMenuItem);
        gameMenu.add(new JSeparator());
        gameMenu.add(duplicatesAllowedCheckboxMenuItem);
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

    public class MainPanel extends JPanel {

        private GamePanel gamePanel;

        MainPanel() {
            setLayout(new CardLayout());

            add(new RulesPanel(this), RulesPanel.NAME);
            add(new EvoSimulationPanel(), EvoSimulationPanel.NAME);
            add(new KnuthSimulationPanel(), KnuthSimulationPanel.NAME);
        }

        public void showCard(String cardName, boolean duplicatesAllowed) {
            if (cardName.equals(GamePanel.NAME_CLASSIC) || cardName.equals(GamePanel.NAME_DELUXE)) {
                if (gamePanel != null) {
                    remove(gamePanel);
                }
                gamePanel = new GamePanel(cardName.equals(GamePanel.NAME_CLASSIC) ? GameVariant.classic(duplicatesAllowed) : GameVariant.deluxe(duplicatesAllowed));
                add(gamePanel, cardName);
            }
            ((CardLayout) getLayout()).show(this, cardName);
        }

        public void showCard(String cardName) {
            showCard(cardName, duplicatesAllowedCheckboxMenuItem.isSelected());
        }

    }

}
