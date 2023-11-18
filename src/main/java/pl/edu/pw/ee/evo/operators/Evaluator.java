package pl.edu.pw.ee.evo.operators;

import pl.edu.pw.ee.evo.Specimen;
import pl.edu.pw.ee.game.MastermindGame;

import java.util.List;

@FunctionalInterface
public interface Evaluator {

    void evaluate(List<Specimen> population, MastermindGame gameState);

}
