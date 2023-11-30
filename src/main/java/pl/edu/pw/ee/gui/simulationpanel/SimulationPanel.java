package pl.edu.pw.ee.gui.simulationpanel;

import lombok.Getter;

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

        add(configurationInputPanel, BorderLayout.WEST);
    }

}
