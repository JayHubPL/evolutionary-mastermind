package pl.edu.pw.ee.evo.operators;

import lombok.Getter;
import pl.edu.pw.ee.evo.Specimen;
import pl.edu.pw.ee.utils.Pair;

import java.util.LinkedList;
import java.util.List;

@FunctionalInterface
public interface Crosser {

    CrossoverResults cross(List<Pair<Specimen, Specimen>> pairs);

    @Getter
    class CrossoverResults {

        private final List<Specimen> offsprings = new LinkedList<>();
        private final List<Specimen> nonCrossedSpecimens = new LinkedList<>();

        public int getSpecimenCount() {
            return offsprings.size() + nonCrossedSpecimens.size();
        }

    }

}
