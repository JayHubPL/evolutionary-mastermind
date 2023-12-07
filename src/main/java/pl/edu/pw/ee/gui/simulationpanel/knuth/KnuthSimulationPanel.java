package pl.edu.pw.ee.gui.simulationpanel.knuth;

import pl.edu.pw.ee.gui.simulationpanel.shared.SimulationResultsPanel;

import javax.swing.*;
import java.awt.*;

public class KnuthSimulationPanel extends JPanel {

    public static final String NAME = "KNUTH_SIMULATION_PANEL";

    public KnuthSimulationPanel() {
        setLayout(new BorderLayout());

        var simulationResultsPanel = new SimulationResultsPanel();
        var configurationInputPanel = new KnuthAlgorithmConfigurationInputPanel(simulationResultsPanel);

        add(configurationInputPanel, BorderLayout.WEST);
        add(simulationResultsPanel, BorderLayout.CENTER);
    }

}
