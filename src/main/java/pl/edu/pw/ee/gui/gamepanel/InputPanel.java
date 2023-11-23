package pl.edu.pw.ee.gui.gamepanel;

import pl.edu.pw.ee.game.GameVariant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class InputPanel extends JPanel {

    private final List<ColorButton> colorButtons = new ArrayList<>();

    public InputPanel(GamePanel gamePanel, GameVariant gameVariant) {
        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        setBackground(Color.green); // debug

        for (int i = 0; i < gameVariant.getCodeLength(); i++) {
            var colorButton = new ColorButton(gameVariant.getNumberOfColors(), 50);
            colorButtons.add(colorButton);
            add(colorButton);
        }

        var submitGuessButton = createInputButton("Sprawdź", e -> {
            var colorIndexSequence = colorButtons.stream().map(ColorButton::getColorIndex).toList();
            if (colorIndexSequence.contains(null)) {
                JOptionPane.showMessageDialog(this, "Sekwencja kolorów jest niepełna.", "Nieprawidłowe dane", JOptionPane.ERROR_MESSAGE);
            } else {
                var gameFinished = gamePanel.makeGuess(colorIndexSequence);
                ((JButton) e.getSource()).setEnabled(!gameFinished);
            }
        });
        add(submitGuessButton);
        var exitButton = createInputButton("Zakończ grę", e -> gamePanel.finishGame());
        add(exitButton);
    }

    public JButton createInputButton(String text, ActionListener actionListener) {
        var inputButton = new JButton(text);
        inputButton.setMargin(new Insets(5, 0, 5, 0));
        inputButton.setHorizontalTextPosition(AbstractButton.LEFT);
        inputButton.setFont(new Font(null, Font.PLAIN, 12));
        inputButton.addActionListener(actionListener);
        return inputButton;
    }

}
