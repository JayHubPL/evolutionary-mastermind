package pl.edu.pw.ee.evo;

import pl.edu.pw.ee.game.Code;
import pl.edu.pw.ee.game.CodeBreaker;
import pl.edu.pw.ee.game.MastermindGame;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class EvoAlgorithm implements CodeBreaker {

    private final EvoAlgorithmConfig config;

    private List<Specimen> population;

    public EvoAlgorithm(EvoAlgorithmConfig config) {
        this.config = config;
    }

    @Override
    public Code makeGuess(MastermindGame gameState) {
        if (population == null) {
            population = config.getPopulationGenerator().generatePopulation(config.getPopulationSize(), config.isInitialPopulationDuplicatesAllowed());
            return config.getInitialGuess();
        }
        if (gameState.getPreviousAttempts().size() == 1) {
            config.getEvaluator().evaluate(population, gameState);
        }
        // select
        var selected = config.getSelector().select(population);
        // crossover
        var parents = config.getPairMatcher().pair(selected);
        var offsprings = parents.stream()
                .map(pair -> config.getCrosser().cross(pair))
                .flatMap(List::stream)
                .collect(Collectors.toCollection(LinkedList::new));
        // mutate
        offsprings.forEach(offspring -> config.getMutator().mutate(offspring));
        population = offsprings;
        // evaluate
        config.getEvaluator().evaluate(population, gameState);
        population.sort(Comparator.comparing(Specimen::getFitness).reversed());
        return population.get(0);
    }

}
