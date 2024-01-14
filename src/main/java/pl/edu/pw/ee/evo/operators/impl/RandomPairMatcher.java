package pl.edu.pw.ee.evo.operators.impl;

import lombok.RequiredArgsConstructor;
import pl.edu.pw.ee.evo.Specimen;
import pl.edu.pw.ee.evo.operators.PairMatcher;
import pl.edu.pw.ee.utils.Pair;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

@RequiredArgsConstructor
public class RandomPairMatcher implements PairMatcher {

    private static final Random random = new Random();
    private final boolean repetitionsAllowed;

    @Override
    public List<Pair<Specimen, Specimen>> pair(List<Specimen> specimens) {
        if (repetitionsAllowed) {
            var pairs = new LinkedList<Pair<Specimen, Specimen>>();
            while (pairs.size() < specimens.size() >> 1) {
                var firstParentIndex = random.nextInt(specimens.size());
                var secondParentIndex = random.nextInt(specimens.size());
                while (firstParentIndex == secondParentIndex) {
                    secondParentIndex = random.nextInt(specimens.size());
                }
                pairs.add(Pair.of(new Specimen(specimens.get(firstParentIndex)), new Specimen(specimens.get(secondParentIndex))));
            }
            return pairs;
        }
        Collections.shuffle(specimens, random);
        return new ConsecutivePairMatcher().pair(specimens);
    }
}
