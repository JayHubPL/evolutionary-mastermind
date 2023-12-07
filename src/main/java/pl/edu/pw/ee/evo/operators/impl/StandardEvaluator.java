package pl.edu.pw.ee.evo.operators.impl;

import pl.edu.pw.ee.evo.Specimen;
import pl.edu.pw.ee.evo.operators.Evaluator;
import pl.edu.pw.ee.game.MastermindGame;

import java.util.List;

public class StandardEvaluator implements Evaluator {

    @Override
    public void evaluate(List<Specimen> population, MastermindGame gameState) {
        for (var specimen : population) {
            specimen.setFitness(gameState.getPreviousAttempts().stream()
                    .map(guess -> -specimen.compareTo(guess.getCode()).distanceTo(guess.getScore()))
                    .mapToInt(Integer::intValue)
                    .sum());
        }
        var minFitness = population.stream()
                .mapToInt(Specimen::getFitness)
                .min().orElseThrow();
        population.forEach(specimen -> specimen.setFitness(specimen.getFitness() - minFitness));
    }

}
