package pl.edu.pw.ee.simulation;

import pl.edu.pw.ee.evo.EvoAlgorithm;
import pl.edu.pw.ee.evo.EvoAlgorithmConfig;
import pl.edu.pw.ee.evo.PopulationGenerator;
import pl.edu.pw.ee.evo.operators.impl.ConsecutivePairMatcher;
import pl.edu.pw.ee.evo.operators.impl.OnePointSplitCrosser;
import pl.edu.pw.ee.evo.operators.impl.StandardEvaluator;
import pl.edu.pw.ee.evo.operators.impl.ValueShiftMutator;
import pl.edu.pw.ee.game.Code;

public class EvoAlgorithmSimulationRunner extends SimulationRunner {

    private final SimulationConfig simulationConfig;
    private final PopulationGenerator populationGenerator;

    private EvoAlgorithmSimulationRunner(int numberOfSimulations, SimulationConfig simulationConfig) {
        super(numberOfSimulations);
        this.simulationConfig = simulationConfig;
        populationGenerator = new PopulationGenerator(simulationConfig.getGameVariant());
    }

    @Override
    protected Simulation createSimulation() {
        var gameVariant = simulationConfig.getGameVariant();
        var evoAlgorithmConfig = EvoAlgorithmConfig.builder()
                .gameVariant(gameVariant)
                .populationSize(simulationConfig.getPopulationSize())
                .initialGuess(simulationConfig.getInitialGuess().orElse(new Code(gameVariant)))
                .initialPopulationDuplicatesAllowed(simulationConfig.isInitialPopulationDuplicatesAllowed())
                .populationGenerator(populationGenerator)
                .evaluator(new StandardEvaluator())
                .scaler(simulationConfig.getScaler())
                .selector(simulationConfig.getSelector())
                .pairMatcher(new ConsecutivePairMatcher())
                .crosser(new OnePointSplitCrosser(gameVariant, simulationConfig.getCrossingProbability()))
                .mutator(new ValueShiftMutator(gameVariant, simulationConfig.getMutationChance()))
                .build();
        return new Simulation(gameVariant, new EvoAlgorithm(evoAlgorithmConfig), simulationConfig.getSecretCode().orElse(new Code(gameVariant)));
    }

    public static class Factory implements SimulationRunnerFactory {

        @Override
        public SimulationRunner createSimulationRunner(int numberOfSimulations, SimulationConfig simulationConfig) {
            return new EvoAlgorithmSimulationRunner(numberOfSimulations, simulationConfig);
        }

    }

}
