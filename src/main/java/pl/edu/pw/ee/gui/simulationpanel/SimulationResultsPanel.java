package pl.edu.pw.ee.gui.simulationpanel;

import lombok.SneakyThrows;
import pl.edu.pw.ee.gui.gamepanel.GuessHistoryPanel;
import pl.edu.pw.ee.gui.utils.GuiUtils;
import pl.edu.pw.ee.gui.utils.ProgressListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

public class SimulationResultsPanel extends JPanel implements ProgressListener, ActionListener {

    private final SimulationPanel parent;
    private GuessHistoryPanel guessHistoryPanel = null;

    public SimulationResultsPanel(SimulationPanel parent) {
        setLayout(new GridBagLayout());
        this.parent = parent;
    }

    @Override
    @SneakyThrows
    public void done() {
        removeAll();

        var simulationStatistics = parent.getConfigurationInputPanel().getSimulatorConfigPanel().getSimulationRunner().get();
        var numberOfSimulations = simulationStatistics.getIndividualSimulationResults().size();
        var averageGuessCountLabel = new JLabel(String.format("Średnia liczba prób odgadnięcia hasła: %.2f", simulationStatistics.getAverageGuessCount()));
        var winPercentageLabel = new JLabel(String.format("Procent wygranych gier: %.2f%%", (double) simulationStatistics.getNumberOfWins() * 100 / numberOfSimulations));
        var numberOfWinsLabel = new JLabel(String.format("Liczba wygranych gier: %d", simulationStatistics.getNumberOfWins()));
        var numberOfFailsLabel = new JLabel(String.format("Liczba przegranych gier: %d", simulationStatistics.getNumberOfFails()));

        var simulationComboBoxStrings = new Vector<String>();
        for (int i = 0; i < numberOfSimulations; i++) {
            var gameResults = simulationStatistics.getIndividualSimulationResults().get(i);
            var attemptDeclinationString = switch (gameResults.getNumberOfAttempts()) {
                case 1 -> "próba";
                case 2 | 3 | 4 -> "próby";
                default -> "prób";
            };
            simulationComboBoxStrings.add(String.format("Symulacja #%d: %d %s", i + 1, gameResults.getNumberOfAttempts(), attemptDeclinationString));
        }

        var simulationsComboBox = new JComboBox<>(simulationComboBoxStrings);
        simulationsComboBox.addActionListener(this);

        add(averageGuessCountLabel, GuiUtils.getListConstraints());
        add(winPercentageLabel, GuiUtils.getListConstraints());
        add(numberOfWinsLabel, GuiUtils.getListConstraints());
        add(numberOfFailsLabel, GuiUtils.getListConstraints());
        add(simulationsComboBox, GuiUtils.getListConstraints());

        GuiUtils.revalidateAndRepaintLater(this);
    }

    @Override
    @SneakyThrows
    @SuppressWarnings("unchecked")
    public void actionPerformed(ActionEvent e) {
        if (guessHistoryPanel != null) {
            remove(guessHistoryPanel);
        }

        var simulationIndex = ((JComboBox<String>) e.getSource()).getSelectedIndex();
        var gameResults = parent.getConfigurationInputPanel().getSimulatorConfigPanel().getSimulationRunner().get()
                .getIndividualSimulationResults().get(simulationIndex);

        guessHistoryPanel = new GuessHistoryPanel();
        for (var guess : gameResults.getAttemptHistory()) {
            guessHistoryPanel.addGuess(guess);
        }

        add(guessHistoryPanel, GuiUtils.getListConstraints());
        GuiUtils.revalidateAndRepaintLater(this);
    }

}
