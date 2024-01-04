package pl.edu.pw.ee.gui.gamepanel;

import pl.edu.pw.ee.game.GameVariant;
import pl.edu.pw.ee.utils.ResourceUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class GameInputPanel extends JPanel {

    private static final String cancelIconResourceName = "icons/cancel.png";
    private static final String checkIconResourceName = "icons/check.png";
    private final List<ColorButton> colorButtons = new ArrayList<>();

    public GameInputPanel(GamePanel gamePanel, GameVariant gameVariant) {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBorder(BorderFactory.createTitledBorder("Wprowadzanie propozycji rozwiązania"));

        var colorButtonsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        for (int i = 0; i < gameVariant.getCodeLength(); i++) {
            var colorButton = new ColorButton(gameVariant.getNumberOfColors(), 50);
            colorButtons.add(colorButton);
            colorButtonsPanel.add(colorButton);
        }

        var submitAndExitButtonsPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 10));
        submitAndExitButtonsPanel.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 0));
        var submitGuessButton = createInputButton("Sprawdź", e -> {
            var colorIndexSequence = colorButtons.stream().map(ColorButton::getColorIndex).toList();
            if (colorIndexSequence.contains(null)) {
                JOptionPane.showMessageDialog(this, "Sekwencja kolorów jest niepełna.", "Nieprawidłowe dane", JOptionPane.ERROR_MESSAGE);
            } else {
                var gameFinished = gamePanel.makeGuess(colorIndexSequence);
                ((JButton) e.getSource()).setEnabled(!gameFinished);
            }
        });
        submitGuessButton.setIcon(ResourceUtils.getIcon(checkIconResourceName, 26));
        submitGuessButton.setHorizontalTextPosition(JButton.RIGHT);
        submitGuessButton.setVerticalTextPosition(JButton.CENTER);
        var exitButton = createInputButton("Zakończ", e -> gamePanel.finishGame());
        exitButton.setIcon(ResourceUtils.getIcon(cancelIconResourceName, 24));
        exitButton.setHorizontalTextPosition(JButton.RIGHT);
        exitButton.setVerticalTextPosition(JButton.CENTER);
        submitAndExitButtonsPanel.add(submitGuessButton);
        submitAndExitButtonsPanel.add(exitButton);
        submitGuessButton.setPreferredSize(new Dimension(150, submitGuessButton.getPreferredSize().height));
        exitButton.setPreferredSize(new Dimension(150, exitButton.getPreferredSize().height));

        add(colorButtonsPanel);
        add(submitAndExitButtonsPanel);
    }

    public JButton createInputButton(String text, ActionListener actionListener) {
        var inputButton = new JButton(text);
        inputButton.setMargin(new Insets(5, 0, 5, 0));
        inputButton.setHorizontalTextPosition(AbstractButton.LEFT);
        inputButton.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        inputButton.addActionListener(actionListener);
        return inputButton;
    }

}
