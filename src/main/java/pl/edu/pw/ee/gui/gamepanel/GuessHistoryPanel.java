package pl.edu.pw.ee.gui.gamepanel;

import pl.edu.pw.ee.game.Guess;
import pl.edu.pw.ee.gui.utils.ColorDot;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class GuessHistoryPanel extends JPanel {

    private final JPanel guessesPanel;
    private final JScrollPane scrollPane;

    public GuessHistoryPanel() {
        setLayout(new BorderLayout());
        setBorder(new TitledBorder("Próby odgadnięcia hasła"));

        guessesPanel = new JPanel();
        guessesPanel.setLayout(new BoxLayout(guessesPanel, BoxLayout.Y_AXIS));

        scrollPane = new JScrollPane(guessesPanel);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.getHorizontalScrollBar().setUnitIncrement(16);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        add(scrollPane, BorderLayout.CENTER);
    }

    public void addGuess(Guess guess) {
        guessesPanel.add(new GuessHistoryRecordPanel(guess));
        scrollPane.setPreferredSize(guessesPanel.getPreferredSize());
        revalidate();
    }

    public void clear() {
        guessesPanel.removeAll();
    }

    static class GuessHistoryRecordPanel extends JPanel {

        public GuessHistoryRecordPanel(Guess guess) {
            setLayout(new FlowLayout(FlowLayout.LEFT));
            for (var codePin : guess.getCode()) {
                add(new ColorDot(GamePanel.COLORS[codePin.getIndex()], 40));
            }
            for (var scorePin : guess.getScore()) {
                add(new ColorDot(scorePin, 30));
            }
            setMaximumSize(new Dimension(Integer.MAX_VALUE, getPreferredSize().height));
        }

    }

}
