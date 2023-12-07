package pl.edu.pw.ee.gui.simulationpanel.knuth;

import pl.edu.pw.ee.gui.simulationpanel.shared.ConfigurationInputAbstractPanel;
import pl.edu.pw.ee.gui.simulationpanel.shared.SimulationResultsPanel;
import pl.edu.pw.ee.simulation.KnuthAlgorithmSimulationRunner;
import pl.edu.pw.ee.simulation.SimulationConfig;

public class KnuthAlgorithmConfigurationInputPanel extends ConfigurationInputAbstractPanel {

    public KnuthAlgorithmConfigurationInputPanel(SimulationResultsPanel simulationResultsPanel) {
        super(simulationResultsPanel, new KnuthAlgorithmSimulationRunner.Factory());
    }

    @Override
    public SimulationConfig getSimulationConfig() {
        return SimulationConfig.builder()
                .gameVariant(gameVariant)
                .initialGuess(firstGuessConfigPanel.getInitialGuess())
                .secretCode(secretCodeConfigPanel.getSecretCode())
                .build();
    }
}
