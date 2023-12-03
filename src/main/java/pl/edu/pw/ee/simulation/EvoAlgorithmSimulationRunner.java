package pl.edu.pw.ee.simulation;

import pl.edu.pw.ee.evo.EvoAlgorithm;
import pl.edu.pw.ee.evo.EvoAlgorithmConfig;
import pl.edu.pw.ee.evo.PopulationGenerator;
import pl.edu.pw.ee.evo.operators.*;
import pl.edu.pw.ee.game.Code;

public class EvoAlgorithmSimulationRunner extends SimulationRunner {

    private final SimulationConfig simulationConfig;
    private final PopulationGenerator populationGenerator;

    public EvoAlgorithmSimulationRunner(int numberOfSimulations, SimulationConfig simulationConfig) {
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
                .selector(new UnbalancedRouletteSelector())
                .pairMatcher(new ConsecutivePairMatcher())
                .crosser(new StandardCrosser(gameVariant))
                .mutator(new ColorShiftMutator(gameVariant, simulationConfig.getMutationChance()))
                .build();
        return new Simulation(new EvoAlgorithm(evoAlgorithmConfig), simulationConfig);
    }

}
