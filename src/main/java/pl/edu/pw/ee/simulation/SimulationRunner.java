package pl.edu.pw.ee.simulation;

import lombok.RequiredArgsConstructor;
import pl.edu.pw.ee.game.*;
import pl.edu.pw.ee.gui.utils.ProgressListener;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

@RequiredArgsConstructor
public abstract class SimulationRunner extends SwingWorker<SimulationResults, Void> {

    private final List<ProgressListener> progressListeners = new ArrayList<>();
    private final Integer numberOfSimulations;

    protected abstract Simulation createSimulation();

    @Override
    protected SimulationResults doInBackground() {
        var executorService = new ThreadPoolExecutor(
                Runtime.getRuntime().availableProcessors(),
                Runtime.getRuntime().availableProcessors() * 2,
                100L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>());
        var completionService = new ExecutorCompletionService<GameResults>(executorService);
        var startTime = System.currentTimeMillis();
        for (int i = 0; i < numberOfSimulations; i++) {
            completionService.submit(createSimulation());
        }
        var results = new ArrayList<GameResults>(numberOfSimulations);
        for (int i = 0; i < numberOfSimulations; i++) {
            try {
                results.add(completionService.take().get());
                progressListeners.forEach(pl -> pl.update((double) results.size() / numberOfSimulations));
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException("Simulation results cannot be obtained", e);
            }
        }
        var finishTime = System.currentTimeMillis();
        executorService.shutdown();
        return new SimulationResults(results, finishTime - startTime);
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
    public static class Simulation implements Callable<GameResults> {

        private final GameVariant gameVariant;
        private final CodeBreaker codeBreaker;
        private final Code secretCode;

        @Override
        public GameResults call() {
            return new MastermindGame(new CodeMaker(secretCode), codeBreaker, gameVariant).play();
        }
    }

}
