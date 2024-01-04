package pl.edu.pw.ee.gui.gamepanel;

import pl.edu.pw.ee.game.GameVariant;
import pl.edu.pw.ee.game.MastermindGame;
import pl.edu.pw.ee.gui.MainFrame;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GamePanel extends JPanel {

    public static final String NAME_CLASSIC = "GAME_PANEL_CLASSIC";
    public static final String NAME_DELUXE = "GAME_PANEL_DELUXE";

    private final MastermindGame game;
    private final GuessHistoryPanel guessHistoryPanel;
    private final PlayerCodeBreaker playerCodeBreaker;

    public GamePanel(GameVariant gameVariant) {
        playerCodeBreaker = new PlayerCodeBreaker();
        guessHistoryPanel = new GuessHistoryPanel(40, 30);
        game = new MastermindGame(playerCodeBreaker, gameVariant);

        setLayout(new BorderLayout());

        add(new GameInputPanel(this, gameVariant), BorderLayout.NORTH);
        add(guessHistoryPanel, BorderLayout.CENTER);
    }

    public boolean makeGuess(List<Integer> colorIndexSequence) {
        playerCodeBreaker.setCurrentCodeSequence(colorIndexSequence);
        guessHistoryPanel.addGuess(game.makeGuess());
        if (game.getCodeBreakerWon()) {
            JOptionPane.showMessageDialog(this, "Gratulacje! Odgadnięto hasło!", "Wygrana", JOptionPane.INFORMATION_MESSAGE);
            return true;
        }
        if (game.getGuessesLimitReached()) {
            JOptionPane.showMessageDialog(this, "Nie udało się odgadnąć hasła w limicie prób.", "Porażka", JOptionPane.WARNING_MESSAGE);
            return true;
        }
        return false;
    }

    public void finishGame() {
        ((MainFrame.MainPanel) getParent()).showCard(RulesPanel.NAME);
    }
}
