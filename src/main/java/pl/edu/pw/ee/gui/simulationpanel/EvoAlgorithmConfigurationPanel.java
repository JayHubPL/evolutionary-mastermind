package pl.edu.pw.ee.gui.simulationpanel;

import javax.swing.*;
import javax.swing.border.TitledBorder;

public class EvoAlgorithmConfigurationPanel extends JPanel {

    private final CheckBoxWithLabel uniqueInitialPopulationCheckBoxWithLabel;
    private final SpinnerWithLabel populationSizeSpinnerWithLabel;
    private final SpinnerWithLabel mutationChanceSpinnerWithLabel;

    public EvoAlgorithmConfigurationPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new TitledBorder("Parametry algorytmu"));

        uniqueInitialPopulationCheckBoxWithLabel = new CheckBoxWithLabel("Unikalne osobniki początkowe", false);
        populationSizeSpinnerWithLabel = new SpinnerWithLabel("Rozmiar populacji", new SpinnerNumberModel(30, 2, null, 1));
        mutationChanceSpinnerWithLabel = new SpinnerWithLabel("Szansa na mutację", new SpinnerNumberModel(0.01, 0.0, 1.0, 0.001));

        add(uniqueInitialPopulationCheckBoxWithLabel);
        add(populationSizeSpinnerWithLabel);
        add(mutationChanceSpinnerWithLabel);
    }

    public boolean getUniqueInitialPopulation() {
        return uniqueInitialPopulationCheckBoxWithLabel.isSelected();
    }

    public int getPopulationSize() {
        return (int) populationSizeSpinnerWithLabel.getSpinner().getValue();
    }

    public double getMutationChance() {
        return (double) mutationChanceSpinnerWithLabel.getSpinner().getValue();
    }

}
