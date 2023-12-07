package pl.edu.pw.ee.gui.utils;

import pl.edu.pw.ee.simulation.SimulationResults;

public interface ProgressListener {

    default void update(double progress) {
    }

    default void done(SimulationResults results) {
    }

}
