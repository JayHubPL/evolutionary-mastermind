package pl.edu.pw.ee.evo;

import pl.edu.pw.ee.game.Code;
import pl.edu.pw.ee.game.CodeBreaker;
import pl.edu.pw.ee.game.MastermindGame;

import java.util.Comparator;
import java.util.List;

public class EvoAlgorithm implements CodeBreaker {

    private final EvoAlgorithmConfig config;

    private List<Specimen> population;

    public EvoAlgorithm(EvoAlgorithmConfig config) {
        this.config = config;
    }

    @Override
    public Code makeGuess(MastermindGame gameState) {
        if (population == null) {
            population = config.getPopulationGenerator().generatePopulation(config.getPopulationSize());
            return evaluateAndPickBest();
        }
        // select
        var selected = config.getSelector().select(population);
        // crossover
        var parents = config.getPairMatcher().pair(selected);
        var offsprings = parents.stream()
                .map(pair -> config.getCrosser().cross(pair))
                .flatMap(List::stream)
                .toList();
        // mutate
        offsprings.forEach(offspring -> config.getMutator().mutate(offspring));
        population = offsprings;
        // evaluate
        return evaluateAndPickBest();
    }

    private Code evaluateAndPickBest() {
        config.getEvaluator().evaluate(population);
        population.sort(Comparator.comparing(Specimen::getFitness).reversed());
        return population.get(0);
    }


}
