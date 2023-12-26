package pl.edu.pw.ee.gui.simulationpanel.shared;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import pl.edu.pw.ee.gui.utils.GuiUtils;
import pl.edu.pw.ee.gui.utils.SpinnerWithLabel;
import pl.edu.pw.ee.simulation.SimulationRunner;
import pl.edu.pw.ee.simulation.SimulationRunnerFactory;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.concurrent.CancellationException;

@Slf4j
public class SimulatorConfigurationPanel extends JPanel {

    private final SpinnerWithLabel numberOfSimulationsSpinnerWithLabel;
    @Getter
    private SimulationRunner simulationRunner = null;

    public SimulatorConfigurationPanel(ConfigurationInputAbstractPanel parent, SimulationRunnerFactory simulationRunnerFactory, SimulationResultsPanel simulationResultsPanel) {
        setLayout(new GridBagLayout());
        setBorder(new TitledBorder("Symulacje"));

        numberOfSimulationsSpinnerWithLabel = new SpinnerWithLabel("Liczba symulacji", new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));
        var startSimulationButton = new JButton("Rozpocznij symulację");
        startSimulationButton.addActionListener(e -> {
            if (!parent.areColorInputsValid()) {
                JOptionPane.showMessageDialog(this, """
                        Wprowadzona kombinacja początkowa lub ukryty kod nie są prawidłowe.
                        Wybierz kolor dla każdego pola lub ustaw losowe wartości.
                        """, "Błędne dane", JOptionPane.ERROR_MESSAGE
                );
                return;
            }
            var simulationProgressDialog = new SimulationProgressDialog(this);
            simulationRunner = simulationRunnerFactory.createSimulationRunner(getNumberOfSimulations(), parent.getSimulationConfig());
            simulationRunner.addProgressListener(simulationProgressDialog);
            simulationRunner.addProgressListener(simulationResultsPanel);
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
            try {
                simulationRunner.cancel(true);
            } catch (CancellationException ignored) {
            }
        }
    }
}
