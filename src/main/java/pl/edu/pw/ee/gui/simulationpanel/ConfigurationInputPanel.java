package pl.edu.pw.ee.gui.simulationpanel;

import lombok.Getter;
import pl.edu.pw.ee.game.GameVariant;
import pl.edu.pw.ee.gui.utils.GuiUtils;
import pl.edu.pw.ee.simulation.SimulationConfig;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class ConfigurationInputPanel extends JPanel {

    public static final GameVariant DEFAULT_GAME_VARIANT = GameVariant.classic(true);
    private final SecretCodeConfigurationPanel secretCodeConfigPanel;
    private final FirstGuessConfigurationPanel firstGuessConfigPanel;
    private final EvoAlgorithmConfigurationPanel evoAlgorithmConfigPanel;
    @Getter
    private final SimulatorConfigurationPanel simulatorConfigPanel;
    @Getter
    private GameVariant gameVariant;

    public ConfigurationInputPanel(SimulationResultsPanel simulationResultsPanel) {
        gameVariant = DEFAULT_GAME_VARIANT;

        setBorder(new TitledBorder("Konfiguracja"));
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(280, Integer.MAX_VALUE));

        var gameConfigPanel = new GameConfigurationPanel(this);
        secretCodeConfigPanel = new SecretCodeConfigurationPanel();
        firstGuessConfigPanel = new FirstGuessConfigurationPanel();
        evoAlgorithmConfigPanel = new EvoAlgorithmConfigurationPanel();
        simulatorConfigPanel = new SimulatorConfigurationPanel(this, simulationResultsPanel);

        GridBagConstraints gbc = GuiUtils.getListConstraints();
        add(gameConfigPanel, gbc);
        add(secretCodeConfigPanel, gbc);
        add(firstGuessConfigPanel, gbc);
        add(evoAlgorithmConfigPanel, gbc);
        add(simulatorConfigPanel, gbc);
        gbc.weighty = 1.0;
        add(Box.createVerticalGlue(), gbc);
    }

    public void updateGameVariant(GameVariant gameVariant) {
        if (this.gameVariant.equals(gameVariant)) {
            return;
        }
        this.gameVariant = gameVariant;
        secretCodeConfigPanel.update(gameVariant);
        firstGuessConfigPanel.update(gameVariant);
    }

    public boolean areColorInputsValid() {
        return firstGuessConfigPanel.isInitialGuessValid() && secretCodeConfigPanel.isSecretCodeValid();
    }

    public SimulationConfig getSimulationConfig() {
        return SimulationConfig.builder()
                .gameVariant(gameVariant)
                .populationSize(evoAlgorithmConfigPanel.getPopulationSize())
                .initialGuess(firstGuessConfigPanel.getInitialGuess())
                .secretCode(secretCodeConfigPanel.getSecretCode())
                .initialPopulationDuplicatesAllowed(evoAlgorithmConfigPanel.getUniqueInitialPopulation())
                .mutationChance(evoAlgorithmConfigPanel.getMutationChance())
                .build();
    }
}
