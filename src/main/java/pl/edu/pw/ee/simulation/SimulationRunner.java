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
public abstract class SimulationRunner extends SwingWorker<SimulationResults, Void> {

    private final List<ProgressListener> progressListeners = new ArrayList<>();
    private final Integer numberOfSimulations;
    private final AtomicInteger finishedSimulations = new AtomicInteger();

    protected abstract Simulation createSimulation();

    @Override
    protected SimulationResults doInBackground() {
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
        return new SimulationResults(results);
    }

    @Override
    protected void done() {
        progressListeners.forEach(progressListener -> {
            try {
                progressListener.done(get());
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException("Simulation results cannot be obtained", e);
            }
        });
    }

    public void addProgressListener(ProgressListener progressListener) {
        progressListeners.add(progressListener);
    }

    @RequiredArgsConstructor
    class Simulation implements Callable<GameResults> {

        private final GameVariant gameVariant;
        private final CodeBreaker codeBreaker;
        private final Code secretCode;

        @Override
        public GameResults call() {
            var game = new MastermindGame(new CodeMaker(secretCode), codeBreaker, gameVariant);
            var results = game.play();
            progressListeners.forEach(pl -> pl.update((double) finishedSimulations.incrementAndGet() / numberOfSimulations));
            return results;
        }
    }

}
