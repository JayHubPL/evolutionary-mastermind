package pl.edu.pw.ee.evo.operators;

import pl.edu.pw.ee.evo.Specimen;
import pl.edu.pw.ee.evo.utils.Pair;

import java.util.List;

@FunctionalInterface
public interface PairMatcher {

    List<Pair<Specimen, Specimen>> pair(List<Specimen> specimens);

}
