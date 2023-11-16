package pl.edu.pw.ee.evo.operators;

import pl.edu.pw.ee.evo.Specimen;

import java.util.List;

@FunctionalInterface
public interface Evaluator {

    void evaluate(List<Specimen> population);

}
