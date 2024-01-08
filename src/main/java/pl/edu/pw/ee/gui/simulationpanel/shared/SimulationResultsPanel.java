package pl.edu.pw.ee.gui.simulationpanel.shared;

import lombok.Getter;
import pl.edu.pw.ee.gui.gamepanel.GuessHistoryPanel;
import pl.edu.pw.ee.gui.utils.GuiUtils;
import pl.edu.pw.ee.gui.utils.ProgressListener;
import pl.edu.pw.ee.gui.utils.TimeFormatter;
import pl.edu.pw.ee.gui.utils.ValueWithLabel;
import pl.edu.pw.ee.simulation.SimulationResults;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.text.NumberFormat;

public class SimulationResultsPanel extends JPanel implements ProgressListener, ItemListener {

    private final ValueWithLabel averageGuessCountValueWithLabel;
    private final ValueWithLabel winPercentageValueWithLabel;
    private final ValueWithLabel numberOfWinsValueWithLabel;
    private final ValueWithLabel numberOfFailsValueWithLabel;
    private final ValueWithLabel simulationTimeValueWithLabel;
    private final JComboBox<String> simulationsComboBox;
    private final SecretCodePanel secretCodePanel;
    private final GuessHistoryPanel guessHistoryPanel;
    private final Component verticalGlue = Box.createVerticalGlue();
    @Getter
    private SimulationResults lastSimulationResults = null;

    public SimulationResultsPanel() {
        setLayout(new GridBagLayout());
        setBorder(new TitledBorder("Wyniki symulacji"));

        averageGuessCountValueWithLabel = new ValueWithLabel("Średnia liczba prób odgadnięcia hasła");
        var percentFormatter = NumberFormat.getPercentInstance();
        percentFormatter.setMaximumFractionDigits(2);
        winPercentageValueWithLabel = new ValueWithLabel("Procent wygranych gier", percentFormatter);
        numberOfWinsValueWithLabel = new ValueWithLabel("Liczba wygranych gier");
        numberOfFailsValueWithLabel = new ValueWithLabel("Liczba przegranych gier");
        simulationTimeValueWithLabel = new ValueWithLabel("Czas trwania symulacji", new TimeFormatter());

        simulationsComboBox = new JComboBox<>();
        simulationsComboBox.addItemListener(this);
        simulationsComboBox.setVisible(false);

        secretCodePanel = new SecretCodePanel();
        secretCodePanel.setVisible(false);

        guessHistoryPanel = new GuessHistoryPanel(30, 20);
        guessHistoryPanel.setVisible(false);

        var gbc = GuiUtils.getListConstraints();
        add(averageGuessCountValueWithLabel, gbc);
        add(winPercentageValueWithLabel, gbc);
        add(numberOfWinsValueWithLabel, gbc);
        add(numberOfFailsValueWithLabel, gbc);
        add(simulationTimeValueWithLabel, gbc);
        add(Box.createVerticalStrut(5), gbc);
        add(simulationsComboBox, gbc);
        add(Box.createVerticalStrut(5), gbc);
        add(secretCodePanel, gbc);
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weighty = 1.0;
        add(guessHistoryPanel, gbc);
        add(verticalGlue, gbc);
    }

    @Override
    public void done(SimulationResults results) {
        lastSimulationResults = results;
        var numberOfSimulations = lastSimulationResults.getIndividualGameResults().size();

        averageGuessCountValueWithLabel.setValue(lastSimulationResults.getAverageGuessCount());
        winPercentageValueWithLabel.setValue((double) lastSimulationResults.getNumberOfWins() / numberOfSimulations);
        numberOfWinsValueWithLabel.setValue(lastSimulationResults.getNumberOfWins());
        numberOfFailsValueWithLabel.setValue(lastSimulationResults.getNumberOfFails());
        simulationTimeValueWithLabel.setValue(lastSimulationResults.getTime());

        simulationsComboBox.removeAllItems();
        for (int i = 0; i < numberOfSimulations; i++) {
            var gameResults = lastSimulationResults.getIndividualGameResults().get(i);
            var attemptDeclinationString = switch (gameResults.getNumberOfAttempts()) {
                case 1 -> "próba";
                case 2 | 3 | 4 -> "próby";
                default -> "prób";
            };
            simulationsComboBox.addItem(String.format("Symulacja #%d: %d %s", i + 1, gameResults.getNumberOfAttempts(), attemptDeclinationString));
        }
        simulationsComboBox.setVisible(true);

        GuiUtils.revalidateAndRepaintLater(this);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void itemStateChanged(ItemEvent e) {
        if (lastSimulationResults == null || e.getStateChange() != ItemEvent.SELECTED) {
            return;
        }

        var simulationIndex = ((JComboBox<String>) e.getSource()).getSelectedIndex();
        var gameResults = lastSimulationResults.getIndividualGameResults().get(simulationIndex);
        guessHistoryPanel.clear();
        for (var guess : gameResults.getAttemptHistory()) {
            guessHistoryPanel.addGuess(guess);
        }
        guessHistoryPanel.setVisible(true);
        secretCodePanel.setSecretCode(gameResults.getSecretCode());
        secretCodePanel.setVisible(true);
        remove(verticalGlue);

        GuiUtils.revalidateAndRepaintLater(this);
    }

}
