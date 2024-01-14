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
        if (gameState.getPreviousAttempts().isEmpty()) {
            return config.getInitialGuess();
        }
        if (gameState.getPreviousAttempts().size() == 1) {
            population = config.getPopulationGenerator().generatePopulation(config.getPopulationSize(), config.isInitialPopulationDuplicatesAllowed());
            config.getEvaluator().evaluate(population, gameState);
        }
        // scale
        config.getScaler().scale(population);
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
        // if population size should be odd, add first specimen from selected
        if (offsprings.size() != population.size()) {
            offsprings.add(selected.get(0));
        }
        population = offsprings;
        // evaluate
        config.getEvaluator().evaluate(population, gameState);
        population.sort(Comparator.comparing(Specimen::getFitness).reversed());
        return new Code(population.get(0));
    }

}
