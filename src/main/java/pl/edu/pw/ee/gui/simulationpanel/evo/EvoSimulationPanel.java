package pl.edu.pw.ee.gui.simulationpanel.evo;

import pl.edu.pw.ee.gui.simulationpanel.shared.SimulationResultsPanel;

import javax.swing.*;
import java.awt.*;

public class EvoSimulationPanel extends JPanel {

    public static final String NAME = "EVO_SIMULATION_PANEL";

    public EvoSimulationPanel() {
        setLayout(new BorderLayout());

        var simulationResultsPanel = new SimulationResultsPanel();
        var configurationInputPanel = new EvoAlgorithmConfigurationInputPanel(simulationResultsPanel);

        add(configurationInputPanel, BorderLayout.WEST);
        add(simulationResultsPanel, BorderLayout.CENTER);
    }

}
