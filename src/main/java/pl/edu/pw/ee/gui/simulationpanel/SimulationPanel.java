package pl.edu.pw.ee.gui.simulationpanel;

import lombok.Getter;
import pl.edu.pw.ee.gui.utils.ProgressListener;
import pl.edu.pw.ee.simulation.SimulationRunner;

import javax.swing.*;
import java.awt.*;

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

        public CenterPanel(SimulationPanel parent) {
            progressBar = new JProgressBar();
            progressBar.setStringPainted(true);

            setBackground(Color.PINK);

            var startSimulationButton = new JButton("Rozpocznij symulację");
            startSimulationButton.addActionListener(e -> {
                progressBar.setValue(0);
                new SimulationRunner(this,
                        parent.getConfigurationInputPanel().getSimulationConfig(),
                        parent.getConfigurationInputPanel().getNumberOfSimulations()).execute();
            });

            add(startSimulationButton);
            add(progressBar);
        }

        @Override
        public void update(double progress) {
            SwingUtilities.invokeLater(() -> progressBar.setValue((int) (progress * 100)));
        }
    }

}
