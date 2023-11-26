package pl.edu.pw.ee.gui.simulationpanel;

import pl.edu.pw.ee.gui.utils.GuiUtils;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class SimulatorConfigurationPanel extends JPanel {

    private final SpinnerWithLabel numberOfSimulationsSpinnerWithLabel;

    public SimulatorConfigurationPanel() {
        setLayout(new GridBagLayout());
        setBorder(new TitledBorder("Symulacje"));

        numberOfSimulationsSpinnerWithLabel = new SpinnerWithLabel("Liczba symulacji", new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));

        GridBagConstraints gbc = GuiUtils.getListConstraints();
        add(numberOfSimulationsSpinnerWithLabel, gbc);
    }

    public int getNumberOfSimulations() {
        return (int) numberOfSimulationsSpinnerWithLabel.getSpinner().getValue();
    }
}
