package pl.edu.pw.ee.gui.gamepanel;

import pl.edu.pw.ee.game.Guess;
import pl.edu.pw.ee.gui.utils.ColorDot;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class GuessHistoryPanel extends JPanel {

    public GuessHistoryPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new TitledBorder("Próby odgadnięcia hasła"));
    }

    public void addGuess(Guess guess) {
        add(new GuessHistoryRecordPanel(guess));
        revalidate();
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
