package pl.edu.pw.ee.gui;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pl.edu.pw.ee.codebreakers.PlayerCodeBreaker;
import pl.edu.pw.ee.game.GameVariant;
import pl.edu.pw.ee.game.Guess;
import pl.edu.pw.ee.game.MastermindGame;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class GamePanel extends JPanel {

    public static final Color[] COLORS = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.CYAN, Color.MAGENTA, Color.ORANGE, Color.PINK};


    private JPanel innerPanel;

    private final PlayerCodeBreaker playerCodeBreaker = new PlayerCodeBreaker();

    // Inner panels
    private final RulesPanel rulesPanel = new RulesPanel();

    public GamePanel() {
        setLayout(null);
        setInnerPanel(rulesPanel);
    }

    public void reset() {
        setInnerPanel(rulesPanel);
    }

    private void setInnerPanel(JPanel newPanel) {
        if (innerPanel != null) {
            remove(innerPanel);
        }
        innerPanel = newPanel;
        innerPanel.setBounds(MainFrame.MAIN_PANEL_POSITION);
        add(innerPanel);
        innerPanel.setVisible(true);
        revalidate();
        repaint();
    }

    class RulesPanel extends JPanel {

        public RulesPanel() {
            setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
            setBackground(Color.green);

            var chooseGameVariantLabel = new JLabel("Wybierz typ gry:");
            chooseGameVariantLabel.setFont(new Font(null, Font.PLAIN, 20));
            var classingGameVariantButton = new JButton("Classic");
            var deluxeGameVariantButton = new JButton("Deluxe");
            classingGameVariantButton.setPreferredSize(new Dimension(150, 60));
            classingGameVariantButton.addActionListener(e -> setInnerPanel(createGameInterfacePanel(GameVariant.classic())));
            deluxeGameVariantButton.setPreferredSize(new Dimension(150, 60));
            deluxeGameVariantButton.addActionListener(e -> setInnerPanel(createGameInterfacePanel(GameVariant.deluxe())));

            add(chooseGameVariantLabel);
            add(classingGameVariantButton);
            add(deluxeGameVariantButton);
        }

        private GameInterfacePanel createGameInterfacePanel(GameVariant variant) {
            return new GameInterfacePanel(new MastermindGame(playerCodeBreaker, variant));
        }

    }

    class GameInterfacePanel extends JPanel {

        private final MastermindGame game;
        private final GuessHistoryPanel guessHistoryPanel;

        public GameInterfacePanel(MastermindGame game) {
            setLayout(new BorderLayout());
            this.game = game;
            var inputPanel = new InputPanel();
            var inputPanelHeight = 75;
            inputPanel.setPreferredSize(new Dimension(MainFrame.MAIN_PANEL_POSITION.width, inputPanelHeight));
            guessHistoryPanel = new GuessHistoryPanel(60);
            var guessHistoryScrollPane = new JScrollPane(guessHistoryPanel);
            guessHistoryScrollPane.setPreferredSize(new Dimension(MainFrame.MAIN_PANEL_POSITION.width, MainFrame.MAIN_PANEL_POSITION.height - inputPanelHeight));
            guessHistoryScrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            guessHistoryScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
//            inputPanel.setBounds(0, 0, MainFrame.MAIN_PANEL_POSITION.width, inputPanelHeight);
//            guessHistoryPanel.setBounds(0, inputPanelHeight, MainFrame.MAIN_PANEL_POSITION.width, MainFrame.MAIN_PANEL_POSITION.height - inputPanelHeight);
//            guessHistoryScrollPane.setBounds(0, inputPanelHeight, MainFrame.MAIN_PANEL_POSITION.width, MainFrame.MAIN_PANEL_POSITION.height - inputPanelHeight);
            add(inputPanel, BorderLayout.NORTH);
            add(guessHistoryScrollPane, BorderLayout.CENTER);
        }

        class InputPanel extends JPanel {

            private final List<ColorButton> colorButtons = new ArrayList<>();

            public InputPanel() {
                setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
                setBackground(Color.green);
                for (int i = 0; i < game.getVariant().getCodeLength(); i++) {
                    var colorButton = new ColorButton(game.getVariant().getNumberOfColors());
                    colorButtons.add(colorButton);
                    add(colorButton);
                }
                var submitGuessButton = createInputButton("Sprawdź", e -> {
                    var colorIndexes = colorButtons.stream().map(ColorButton::getColorIndex).toList();
                    if (colorIndexes.contains(null)) {
                        JOptionPane.showMessageDialog(this, "Sekwencja kolorów jest niepełna.", "Nieprawidłowe dane", JOptionPane.ERROR_MESSAGE);
                    } else {
                        playerCodeBreaker.setCurrentCodeSequence(colorIndexes);
                        guessHistoryPanel.addGuess(game.makeGuess());
                        if (game.getCodeBreakerWon()) {
                            JOptionPane.showMessageDialog(this, "Gratulacje! Odgadnięto hasło!", "Wygrana", JOptionPane.INFORMATION_MESSAGE);
                            ((JButton) e.getSource()).setEnabled(false);
                        }
                        if (game.getGuessesLimitReached()) {
                            JOptionPane.showMessageDialog(this, "Nie udało się odgadnąć hasła w limicie prób.", "Porażka", JOptionPane.WARNING_MESSAGE);
                            ((JButton) e.getSource()).setEnabled(false);
                        }
                    }
                });
                add(submitGuessButton);
                var exitButton = createInputButton("Zakończ grę", e -> setInnerPanel(rulesPanel));
                add(exitButton);
            }

            public JButton createInputButton(String text, ActionListener actionListener) {
                var inputButton = new JButton(text);
                inputButton.setMargin(new Insets(5, 0, 5, 0));
                inputButton.setHorizontalTextPosition(AbstractButton.LEFT);
                inputButton.setFont(new Font(null, Font.PLAIN, 12));
                inputButton.setPreferredSize(new Dimension(75, 50));
                inputButton.addActionListener(actionListener);
                return inputButton;
            }

        }

        @Data
        @EqualsAndHashCode(callSuper = true)
        class GuessHistoryPanel extends JPanel {

            private final int historyRecordHeight;

            public GuessHistoryPanel(int historyRecordHeight) {
                this.historyRecordHeight = historyRecordHeight;
                setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
//                setBorder(new TitledBorder("Próby odgadnięcia hasła"));
                setBackground(Color.magenta);
            }

            public void addGuess(Guess guess) {
                var guessHistoryRecordPanel = new GuessHistoryRecordPanel(guess);
//                guessHistoryRecordPanel.setPreferredSize(new Dimension(getWidth(), historyRecordHeight));
                add(guessHistoryRecordPanel);
                revalidate();
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(-1, getComponentCount() * historyRecordHeight);
            }

            class GuessHistoryRecordPanel extends JPanel {

                public GuessHistoryRecordPanel(Guess guess) {
                    setLayout(new FlowLayout(FlowLayout.LEFT, 5, 5));
                    setAlignmentX(Component.LEFT_ALIGNMENT);
                    setBackground(Color.PINK);
                    for (var codePin : guess.getCode()) {
                        add(new ColorDot(COLORS[codePin.getIndex()], (int) (0.8 * historyRecordHeight)));
                    }
                    for (var scorePin : guess.getScore()) {
                        add(new ColorDot(scorePin, (int) (0.5 * historyRecordHeight)));
                    }
                }

                @Override
                public Dimension getMaximumSize() {
                    return new Dimension(getPreferredSize().width, getPreferredSize().height);
                }

            }
        }
    }

}
