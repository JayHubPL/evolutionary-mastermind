package pl.edu.pw.ee.evo.operators;

import pl.edu.pw.ee.evo.Specimen;

import java.util.List;

public interface PopulationGenerator {

    List<Specimen> generatePopulation(int size);

}
