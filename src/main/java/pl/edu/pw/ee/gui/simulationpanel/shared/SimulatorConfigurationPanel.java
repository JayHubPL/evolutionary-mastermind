package pl.edu.pw.ee.gui.simulationpanel.shared;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import pl.edu.pw.ee.gui.utils.GuiUtils;
import pl.edu.pw.ee.gui.utils.ProgressListener;
import pl.edu.pw.ee.gui.utils.SpinnerWithLabel;
import pl.edu.pw.ee.simulation.SimulationResults;
import pl.edu.pw.ee.simulation.SimulationRunner;
import pl.edu.pw.ee.simulation.SimulationRunnerFactory;
import pl.edu.pw.ee.utils.ResourceUtils;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.util.concurrent.CancellationException;

@Slf4j
public class SimulatorConfigurationPanel extends JPanel implements ProgressListener {

    private static final String startIconResourceName = "icons/start.png";
    private static final String saveIconResourceName = "icons/save.png";
    private final SpinnerWithLabel numberOfSimulationsSpinnerWithLabel;
    private final JButton saveResultsButton;
    @Getter
    private SimulationRunner simulationRunner = null;

    public SimulatorConfigurationPanel(ConfigurationInputAbstractPanel parent,
                                       SimulationRunnerFactory simulationRunnerFactory,
                                       SimulationResultsPanel simulationResultsPanel) {
        setLayout(new GridBagLayout());
        setBorder(new TitledBorder("Symulacje"));

        numberOfSimulationsSpinnerWithLabel = new SpinnerWithLabel("Liczba symulacji", new SpinnerNumberModel(1, 1, Integer.MAX_VALUE, 1));
        var startSimulationButton = new JButton("Rozpocznij symulację");
        startSimulationButton.setIcon(ResourceUtils.getIcon(startIconResourceName, 20));
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
            simulationRunner.addProgressListener(this);
            simulationRunner.execute();
        });

        saveResultsButton = new JButton("Zapisz wyniki symulacji");
        saveResultsButton.setIcon(ResourceUtils.getIcon(saveIconResourceName, 20));
        saveResultsButton.addActionListener(e -> {
            var fileChooser = new JFileChooser();
            fileChooser.setDialogType(JFileChooser.SAVE_DIALOG);
            var filter = new FileNameExtensionFilter("CSV Files", "csv");
            fileChooser.setFileFilter(filter);
            int result = fileChooser.showSaveDialog(this);
            if (result == JFileChooser.APPROVE_OPTION && simulationResultsPanel.getLastSimulationResults() != null) {
                ResourceUtils.saveResultsToCsv(fileChooser.getSelectedFile(), simulationResultsPanel.getLastSimulationResults().toCsvData());
            }
        });
        saveResultsButton.setEnabled(false);

        GridBagConstraints gbc = GuiUtils.getListConstraints();
        add(numberOfSimulationsSpinnerWithLabel, gbc);
        add(startSimulationButton, gbc);
        add(Box.createVerticalStrut(5), gbc);
        add(saveResultsButton, gbc);
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

    @Override
    public void done(SimulationResults results) {
        saveResultsButton.setEnabled(true);
    }
}
