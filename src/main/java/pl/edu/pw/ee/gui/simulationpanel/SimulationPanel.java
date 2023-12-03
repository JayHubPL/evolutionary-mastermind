package pl.edu.pw.ee.gui.simulationpanel;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;

public class SimulationPanel extends JPanel {

    public static final String NAME = "SIMULATION_PANEL";
    @Getter
    private final ConfigurationInputPanel configurationInputPanel;
    private final SimulationResultsPanel simulationResultsPanel;

    public SimulationPanel() {
        setLayout(new BorderLayout());
        setBackground(Color.CYAN); // debug

        simulationResultsPanel = new SimulationResultsPanel(this);
        configurationInputPanel = new ConfigurationInputPanel(simulationResultsPanel);

        add(configurationInputPanel, BorderLayout.WEST);
        add(simulationResultsPanel, BorderLayout.CENTER);
    }

}
