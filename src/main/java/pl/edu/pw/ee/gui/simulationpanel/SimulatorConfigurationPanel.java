package pl.edu.pw.ee.gui.simulationpanel;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import pl.edu.pw.ee.gui.utils.GuiUtils;
import pl.edu.pw.ee.gui.utils.ProgressListener;
import pl.edu.pw.ee.simulation.EvoAlgorithmSimulationRunner;
import pl.edu.pw.ee.simulation.SimulationRunner;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

@Slf4j
public class SimulatorConfigurationPanel extends JPanel implements ProgressListener {

    private final SpinnerWithLabel numberOfSimulationsSpinnerWithLabel;
    private SimulationRunner simulationRunner = null;

    public SimulatorConfigurationPanel(ConfigurationInputPanel parent) {
        setLayout(new GridBagLayout());
        setBorder(new TitledBorder("Symulacje"));

        numberOfSimulationsSpinnerWithLabel = new SpinnerWithLabel("Liczba symulacji", new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));
        var startSimulationButton = new JButton("Rozpocznij symulację");
        startSimulationButton.addActionListener(e -> {
            var simulationProgressDialog = new SimulationProgressDialog(this);
            simulationRunner = new EvoAlgorithmSimulationRunner(getNumberOfSimulations(), parent.getSimulationConfig());
            simulationRunner.addProgressListener(simulationProgressDialog);
            simulationRunner.addProgressListener(this);
            simulationRunner.execute();
        });

        add(startSimulationButton);

        GridBagConstraints gbc = GuiUtils.getListConstraints();
        add(numberOfSimulationsSpinnerWithLabel, gbc);
        add(startSimulationButton, gbc);
    }

    public int getNumberOfSimulations() {
        return (int) numberOfSimulationsSpinnerWithLabel.getSpinner().getValue();
    }

    public void terminateSimulation() {
        if (simulationRunner != null) {
            simulationRunner.cancel(true);
        }
    }

    @Override
    @SneakyThrows
    public void done() {
        log.info("{}", simulationRunner.get());
    }
}
