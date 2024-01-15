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
        var pairs = config.getPairMatcher().pair(selected);
        var crossoverResults = config.getCrosser().cross(pairs);
        var offsprings = crossoverResults.getOffsprings();
        var survivors = crossoverResults.getNonCrossedSpecimens();
        // mutate
        if (config.isShouldMutateSurvivors()) {
            survivors.forEach(specimen -> config.getMutator().mutate(specimen));
        }
        offsprings.forEach(offspring -> config.getMutator().mutate(offspring));
        // if population size should be odd, add first specimen from selected
        if (crossoverResults.getSpecimenCount() != population.size()) {
            survivors.add(selected.get(0));
        }
        population.clear();
        population.addAll(offsprings);
        population.addAll(survivors);
        // evaluate
        config.getEvaluator().evaluate(population, gameState);
        population.sort(Comparator.comparing(Specimen::getFitness).reversed());
        return new Code(population.get(0));
    }

}
