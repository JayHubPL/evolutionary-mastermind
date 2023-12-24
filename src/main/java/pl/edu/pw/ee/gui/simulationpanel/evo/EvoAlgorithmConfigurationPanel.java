package pl.edu.pw.ee.gui.simulationpanel.evo;

import pl.edu.pw.ee.evo.operators.PairMatcher;
import pl.edu.pw.ee.evo.operators.Selector;
import pl.edu.pw.ee.evo.operators.impl.*;
import pl.edu.pw.ee.gui.utils.CheckBoxWithLabel;
import pl.edu.pw.ee.gui.utils.ComboBoxWithLabel;
import pl.edu.pw.ee.gui.utils.SpinnerWithLabel;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;
import java.util.Vector;

public class EvoAlgorithmConfigurationPanel extends JPanel implements ItemListener, ChangeListener {

    private final CheckBoxWithLabel uniqueInitialPopulationCheckBoxWithLabel;
    private final SpinnerWithLabel populationSizeSpinnerWithLabel;
    private final ComboBoxWithLabel<String> selectorComboBoxWithLabel;
    private final ComboBoxWithLabel<String> pairMatcherComboBoxWithLabel;
    private final SpinnerWithLabel mutationChanceSpinnerWithLabel;

    // Optional parameter inputs
    private final SpinnerWithLabel tournamentSizeSpinnerWithLabel;
    private final CheckBoxWithLabel pairMatcherRepetitionsAllowedCheckBoxWithLabel;

    public EvoAlgorithmConfigurationPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new TitledBorder("Parametry algorytmu"));

        uniqueInitialPopulationCheckBoxWithLabel = new CheckBoxWithLabel("Unikalne osobniki początkowe", false);
        populationSizeSpinnerWithLabel = new SpinnerWithLabel("Rozmiar populacji", new SpinnerNumberModel(30, 2, null, 1));
        populationSizeSpinnerWithLabel.getSpinner().addChangeListener(this);
        selectorComboBoxWithLabel = new ComboBoxWithLabel<>("Metoda selekcji", new Vector<>(List.of(
                "Ruletka", "Turniej", "Rangi", "Losowa"
        )));
        selectorComboBoxWithLabel.addItemListener(this);
        pairMatcherComboBoxWithLabel = new ComboBoxWithLabel<>("Metoda doboru partnerów", new Vector<>(List.of(
                "Po kolei", "Losowa"
        )));
        pairMatcherComboBoxWithLabel.addItemListener(this);
        mutationChanceSpinnerWithLabel = new SpinnerWithLabel("Szansa na mutację", new SpinnerNumberModel(0.01, 0.0, 1.0, 0.001));

        tournamentSizeSpinnerWithLabel = new SpinnerWithLabel("Rozmiar turnieju", new SpinnerNumberModel(5, 2, getPopulationSize(), 1));
        tournamentSizeSpinnerWithLabel.setVisible(false);
        pairMatcherRepetitionsAllowedCheckBoxWithLabel = new CheckBoxWithLabel("Powtórzenia przy doborze partnerów", false);
        pairMatcherRepetitionsAllowedCheckBoxWithLabel.setVisible(false);

        add(uniqueInitialPopulationCheckBoxWithLabel);
        add(populationSizeSpinnerWithLabel);
        add(selectorComboBoxWithLabel);
        add(tournamentSizeSpinnerWithLabel);
        add(pairMatcherComboBoxWithLabel);
        add(pairMatcherRepetitionsAllowedCheckBoxWithLabel);
        add(mutationChanceSpinnerWithLabel);
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource().equals(selectorComboBoxWithLabel.getComboBox())) {
            tournamentSizeSpinnerWithLabel.setVisible(selectorComboBoxWithLabel.getSelectedItem().equals("Turniej"));
        } else if (e.getSource().equals(pairMatcherComboBoxWithLabel.getComboBox())) {
            pairMatcherRepetitionsAllowedCheckBoxWithLabel.setVisible(pairMatcherComboBoxWithLabel.getSelectedItem().equals("Losowa"));
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (e.getSource().equals(populationSizeSpinnerWithLabel.getSpinner())) {
            var currentTournamentSize = (int) tournamentSizeSpinnerWithLabel.getSpinner().getValue();
            currentTournamentSize = Math.min(getPopulationSize(), currentTournamentSize);
            tournamentSizeSpinnerWithLabel.getSpinner().setModel(new SpinnerNumberModel(currentTournamentSize, 2, getPopulationSize(), 1));
        }
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

    public Selector getSelector() {
        return switch (selectorComboBoxWithLabel.getSelectedItem()) {
            case "Turniej" -> new TournamentSelector((int) tournamentSizeSpinnerWithLabel.getSpinner().getValue());
            case "Ruletka" -> new UnbalancedRouletteSelector();
            case "Rangi" -> new RankBasedSelector(getPopulationSize());
            default -> new RandomSelector();
        };
    }

    public PairMatcher getPairMatcher() {
        return switch (pairMatcherComboBoxWithLabel.getSelectedItem()) {
            case "Po kolei" -> new ConsecutivePairMatcher();
            default -> new RandomPairMatcher(pairMatcherRepetitionsAllowedCheckBoxWithLabel.isSelected());
        };
    }
}
