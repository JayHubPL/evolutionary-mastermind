package pl.edu.pw.ee.simulation;

public interface SimulationRunnerFactory {

    SimulationRunner createSimulationRunner(int numberOfSimulations, SimulationConfig simulationConfig);

}
