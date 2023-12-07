package pl.edu.pw.ee.gui.simulationpanel.shared;

import lombok.Getter;
import pl.edu.pw.ee.game.GameVariant;
import pl.edu.pw.ee.gui.utils.GuiUtils;
import pl.edu.pw.ee.simulation.SimulationConfig;
import pl.edu.pw.ee.simulation.SimulationRunnerFactory;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public abstract class ConfigurationInputAbstractPanel extends JPanel {

    public static final GameVariant DEFAULT_GAME_VARIANT = GameVariant.classic(true);
    protected final SecretCodeConfigurationPanel secretCodeConfigPanel;
    protected final FirstGuessConfigurationPanel firstGuessConfigPanel;
    @Getter
    protected GameVariant gameVariant;

    public ConfigurationInputAbstractPanel(SimulationResultsPanel simulationResultsPanel, SimulationRunnerFactory simulationRunnerFactory) {
        gameVariant = DEFAULT_GAME_VARIANT;

        setBorder(new TitledBorder("Konfiguracja"));
        setLayout(new GridBagLayout());
        setPreferredSize(new Dimension(280, Integer.MAX_VALUE));

        var gameConfigPanel = new GameConfigurationPanel(this);
        secretCodeConfigPanel = new SecretCodeConfigurationPanel();
        firstGuessConfigPanel = new FirstGuessConfigurationPanel();
        var simulatorConfigPanel = new SimulatorConfigurationPanel(this, simulationRunnerFactory, simulationResultsPanel);

        GridBagConstraints gbc = GuiUtils.getListConstraints();
        add(gameConfigPanel, gbc);
        add(secretCodeConfigPanel, gbc);
        add(firstGuessConfigPanel, gbc);
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

    public abstract SimulationConfig getSimulationConfig();
}
