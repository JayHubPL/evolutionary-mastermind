package pl.edu.pw.ee.evo.operators;

import pl.edu.pw.ee.evo.Specimen;

@FunctionalInterface
public interface Mutator {

    void mutate(Specimen specimen);

}
