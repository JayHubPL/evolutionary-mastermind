package pl.edu.pw.ee.simulation;

import pl.edu.pw.ee.game.Code;
import pl.edu.pw.ee.knuth.KnuthAlgorithm;

public class KnuthAlgorithmSimulationRunner extends SimulationRunner {

    private final SimulationConfig simulationConfig;

    private KnuthAlgorithmSimulationRunner(int numberOfSimulations, SimulationConfig simulationConfig) {
        super(numberOfSimulations);
        this.simulationConfig = simulationConfig;
    }

    @Override
    protected Simulation createSimulation() {
        var gameVariant = simulationConfig.getGameVariant();
        var initialGuess = simulationConfig.getInitialGuess().orElse(new Code(gameVariant));
        var secretCode = simulationConfig.getSecretCode().orElse(new Code(gameVariant));
        return new Simulation(gameVariant, new KnuthAlgorithm(gameVariant, initialGuess), secretCode);
    }

    public static class Factory implements SimulationRunnerFactory {
        @Override
        public SimulationRunner createSimulationRunner(int numberOfSimulations, SimulationConfig simulationConfig) {
            return new KnuthAlgorithmSimulationRunner(numberOfSimulations, simulationConfig);
        }
    }
}
