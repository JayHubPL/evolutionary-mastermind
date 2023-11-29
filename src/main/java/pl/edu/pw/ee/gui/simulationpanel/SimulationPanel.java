package pl.edu.pw.ee.gui.simulationpanel;

import lombok.Getter;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import pl.edu.pw.ee.gui.utils.ProgressListener;
import pl.edu.pw.ee.simulation.EvoAlgorithmSimulationRunner;
import pl.edu.pw.ee.simulation.SimulationRunner;

import javax.swing.*;
import java.awt.*;

@Slf4j
public class SimulationPanel extends JPanel {

    public static final String NAME = "SIMULATION_PANEL";
    @Getter
    private final ConfigurationInputPanel configurationInputPanel;

    public SimulationPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.CYAN); // debug

        configurationInputPanel = new ConfigurationInputPanel();
        var centerPanel = new CenterPanel(this); // debug

        add(configurationInputPanel, BorderLayout.WEST);
        add(centerPanel, BorderLayout.CENTER);
    }

    // debug
    public static class CenterPanel extends JPanel implements ProgressListener {

        private final JProgressBar progressBar;
        private SimulationRunner simulationRunner;

        public CenterPanel(SimulationPanel parent) {
            progressBar = new JProgressBar();
            progressBar.setStringPainted(true);

            setBackground(Color.PINK);

            var startSimulationButton = new JButton("Rozpocznij symulację");
            startSimulationButton.addActionListener(e -> {
                startSimulationButton.setEnabled(false);
                progressBar.setValue(0);
                simulationRunner = new EvoAlgorithmSimulationRunner(this,
                        parent.getConfigurationInputPanel().getNumberOfSimulations(),
                        parent.getConfigurationInputPanel().getSimulationConfig());
                simulationRunner.execute();
                startSimulationButton.setEnabled(true);
            });

            add(startSimulationButton);
            add(progressBar);
        }

        @Override
        public void update(double progress) {
            SwingUtilities.invokeLater(() -> progressBar.setValue((int) (progress * 100)));
        }

        @Override
        @SneakyThrows
        public void notifyFinished() {
            var results = simulationRunner.get();
            log.info("{}", results);
        }
    }

}
