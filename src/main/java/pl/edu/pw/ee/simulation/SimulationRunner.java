package pl.edu.pw.ee.simulation;

import lombok.RequiredArgsConstructor;
import pl.edu.pw.ee.game.*;
import pl.edu.pw.ee.gui.utils.ProgressListener;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@RequiredArgsConstructor
public abstract class SimulationRunner extends SwingWorker<SimulationStatistics, Void> {

    private final List<ProgressListener> progressListeners = new ArrayList<>();
    private final Integer numberOfSimulations;
    private final AtomicInteger finishedSimulations = new AtomicInteger();

    protected abstract Simulation createSimulation();

    @Override
    protected SimulationStatistics doInBackground() {
        var executorService = new ThreadPoolExecutor(
                Runtime.getRuntime().availableProcessors(),
                Runtime.getRuntime().availableProcessors() * 2,
                100L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>());
        var tasks = new ArrayList<Simulation>(numberOfSimulations);
        for (int i = 0; i < numberOfSimulations; i++) {
            tasks.add(createSimulation());
        }
        var results = new ArrayList<GameResults>(numberOfSimulations);
        try {
            for (var task : executorService.invokeAll(tasks)) {
                results.add(task.get());
            }
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException("Simulation results cannot be obtained", e);
        } finally {
            executorService.shutdown();
        }
        return new SimulationStatistics(results);
    }

    @Override
    protected void done() {
        progressListeners.forEach(ProgressListener::done);
    }

    public void addProgressListener(ProgressListener progressListener) {
        progressListeners.add(progressListener);
    }

    @RequiredArgsConstructor
    class Simulation implements Callable<GameResults> {

        private final CodeBreaker codeBreaker;
        private final SimulationConfig simulationConfig;

        @Override
        public GameResults call() {
            var secretCode = simulationConfig.getSecretCode().orElse(new Code(simulationConfig.getGameVariant()));
            var game = new MastermindGame(new CodeMaker(secretCode), codeBreaker, simulationConfig.getGameVariant());
            var results = game.play();
            progressListeners.forEach(pl -> pl.update((double) finishedSimulations.incrementAndGet() / numberOfSimulations));
            return results;
        }
    }

}
