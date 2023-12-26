package pl.edu.pw.ee.gui.simulationpanel.shared;

import pl.edu.pw.ee.gui.utils.ProgressListener;
import pl.edu.pw.ee.simulation.SimulationResults;

import javax.swing.*;
import java.awt.*;

public class SimulationProgressDialog extends JDialog implements ProgressListener {

    private final JProgressBar progressBar;

    public SimulationProgressDialog(SimulatorConfigurationPanel parent) {
        super();
        setLocationRelativeTo(parent);
        setTitle("Symulacja w toku...");
        setLayout(new FlowLayout());
        setResizable(false);
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        setAlwaysOnTop(true);

        progressBar = new JProgressBar();
        progressBar.setStringPainted(true);

        var cancelButton = new JButton("Anuluj");
        cancelButton.addActionListener(e -> {
            parent.terminateSimulation();
            dispose();
        });

        add(progressBar);
        add(cancelButton);

        pack();
        setVisible(true);
    }

    @Override
    public void update(double progress) {
        SwingUtilities.invokeLater(() -> progressBar.setValue((int) (progress * 100)));
    }

    @Override
    public void done(SimulationResults results) {
        dispose();
    }
}
