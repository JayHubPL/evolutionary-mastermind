package pl.edu.pw.ee.gui.simulationpanel.evo;

import pl.edu.pw.ee.gui.simulationpanel.shared.ConfigurationInputAbstractPanel;
import pl.edu.pw.ee.gui.simulationpanel.shared.SimulationResultsPanel;
import pl.edu.pw.ee.gui.utils.GuiUtils;
import pl.edu.pw.ee.simulation.EvoAlgorithmSimulationRunner;
import pl.edu.pw.ee.simulation.SimulationConfig;

public class EvoAlgorithmConfigurationInputPanel extends ConfigurationInputAbstractPanel {

    private final EvoAlgorithmConfigurationPanel evoAlgorithmConfigPanel;

    public EvoAlgorithmConfigurationInputPanel(SimulationResultsPanel simulationResultsPanel) {
        super(simulationResultsPanel, new EvoAlgorithmSimulationRunner.Factory());
        evoAlgorithmConfigPanel = new EvoAlgorithmConfigurationPanel();
        add(evoAlgorithmConfigPanel, GuiUtils.getListConstraints(), getComponentCount() - 2);
    }

    @Override
    public SimulationConfig getSimulationConfig() {
        return SimulationConfig.builder()
                .gameVariant(gameVariant)
                .populationSize(evoAlgorithmConfigPanel.getPopulationSize())
                .initialGuess(firstGuessConfigPanel.getInitialGuess())
                .secretCode(secretCodeConfigPanel.getSecretCode())
                .initialPopulationDuplicatesAllowed(evoAlgorithmConfigPanel.getUniqueInitialPopulation())
                .mutationChance(evoAlgorithmConfigPanel.getMutationChance())
                .crossingProbability(evoAlgorithmConfigPanel.getCrossingProbability())
                .scaler(evoAlgorithmConfigPanel.getScaler())
                .selector(evoAlgorithmConfigPanel.getSelector())
                .pairMatcher(evoAlgorithmConfigPanel.getPairMatcher())
                .build();
    }
}
