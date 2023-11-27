package pl.edu.pw.ee.evo.operators;

import pl.edu.pw.ee.evo.Specimen;
import pl.edu.pw.ee.utils.Pair;

import java.util.List;

@FunctionalInterface
public interface Crosser {

    List<Specimen> cross(Pair<Specimen, Specimen> pair);

}
