package pl.edu.pw.ee.gui.gamepanel;

import pl.edu.pw.ee.game.Guess;
import pl.edu.pw.ee.gui.utils.ColorDot;
import pl.edu.pw.ee.gui.utils.ColorPalette;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class GuessHistoryPanel extends JPanel {

    private final JPanel guessesPanel;
    private final JScrollPane scrollPane;
    private final int codePinSize, scorePinSize;

    public GuessHistoryPanel(int codePinSize, int scorePinSize) {
        this.codePinSize = codePinSize;
        this.scorePinSize = scorePinSize;

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

    class GuessHistoryRecordPanel extends JPanel {

        public GuessHistoryRecordPanel(Guess guess) {
            setLayout(new FlowLayout(FlowLayout.LEFT));
            for (var codePin : guess.getCode()) {
                add(new ColorDot(ColorPalette.fromIndex(codePin.getIndex()), codePinSize));
            }
            for (var scorePin : guess.getScore()) {
                add(new ColorDot(scorePin, scorePinSize));
            }
            setMaximumSize(new Dimension(Integer.MAX_VALUE, getPreferredSize().height));
        }

    }

}
