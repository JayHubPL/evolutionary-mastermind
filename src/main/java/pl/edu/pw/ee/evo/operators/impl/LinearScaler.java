package pl.edu.pw.ee.evo.operators.impl;

import lombok.RequiredArgsConstructor;
import pl.edu.pw.ee.evo.Specimen;
import pl.edu.pw.ee.evo.operators.Scaler;

import java.util.List;

@RequiredArgsConstructor
public class LinearScaler implements Scaler {

    private final double multiplicationFactor;

    @Override
    public void scale(List<Specimen> population) {
        var mean = population.stream().mapToDouble(Specimen::getFitness).average().orElseThrow();
        var maxFitness = population.stream().mapToDouble(Specimen::getFitness).max().orElseThrow();
        var minFitness = population.stream().mapToDouble(Specimen::getFitness).min().orElseThrow();
        double a, b;
        if (minFitness > (multiplicationFactor * mean - maxFitness) / (multiplicationFactor - 1.)) {
            a = ((multiplicationFactor - 1.) * mean) / (maxFitness - mean + Double.MIN_VALUE);
            b = mean * ((maxFitness - multiplicationFactor * mean) / (maxFitness - mean + Double.MIN_VALUE));
        } else {
            a = mean / (mean - minFitness + Double.MIN_VALUE);
            b = -minFitness * mean / (mean - minFitness + Double.MIN_VALUE);
        }
        population.forEach(specimen -> specimen.setFitness(Math.max(a * specimen.getFitness() + b, .0)));
    }

}
