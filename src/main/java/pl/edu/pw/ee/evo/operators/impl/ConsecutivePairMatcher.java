package pl.edu.pw.ee.evo.operators.impl;

import pl.edu.pw.ee.evo.Specimen;
import pl.edu.pw.ee.evo.operators.PairMatcher;
import pl.edu.pw.ee.utils.Pair;

import java.util.LinkedList;
import java.util.List;

public class ConsecutivePairMatcher implements PairMatcher {

    @Override
    public List<Pair<Specimen, Specimen>> pair(List<Specimen> specimens) {
        var pairs = new LinkedList<Pair<Specimen, Specimen>>();
        for (int i = 1; i < specimens.size(); i += 2) {
            pairs.add(Pair.of(specimens.get(i - 1), specimens.get(i)));
        }
        return pairs;
    }

}
