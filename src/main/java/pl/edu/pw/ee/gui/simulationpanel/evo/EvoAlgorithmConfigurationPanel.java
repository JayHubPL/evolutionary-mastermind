package pl.edu.pw.ee.gui.simulationpanel.evo;

import pl.edu.pw.ee.evo.operators.PairMatcher;
import pl.edu.pw.ee.evo.operators.Scaler;
import pl.edu.pw.ee.evo.operators.Selector;
import pl.edu.pw.ee.evo.operators.impl.ConsecutivePairMatcher;
import pl.edu.pw.ee.evo.operators.impl.LinearScaler;
import pl.edu.pw.ee.evo.operators.impl.NoScaler;
import pl.edu.pw.ee.evo.operators.impl.RandomPairMatcher;
import pl.edu.pw.ee.evo.operators.impl.RandomSelector;
import pl.edu.pw.ee.evo.operators.impl.RankBasedSelector;
import pl.edu.pw.ee.evo.operators.impl.TournamentSelector;
import pl.edu.pw.ee.evo.operators.impl.UnbalancedRouletteSelector;
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
    private final ComboBoxWithLabel<String> scalerComboBoxWithLabel;
    private final SpinnerWithLabel mutationChanceSpinnerWithLabel;
    private final CheckBoxWithLabel shouldMutateSurvivorsCheckBoxWithLabel;
    private final SpinnerWithLabel crossingProbabilitySpinnerWithLabel;

    // Optional parameter inputs
    private final SpinnerWithLabel multiplicationFactorSpinnerWithLabel;
    private final SpinnerWithLabel tournamentSizeSpinnerWithLabel;
    private final CheckBoxWithLabel pairMatcherRepetitionsAllowedCheckBoxWithLabel;

    public EvoAlgorithmConfigurationPanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new TitledBorder("Parametry algorytmu"));

        uniqueInitialPopulationCheckBoxWithLabel = new CheckBoxWithLabel("Unikalne osobniki początkowe", false);
        populationSizeSpinnerWithLabel = new SpinnerWithLabel("Rozmiar populacji", new SpinnerNumberModel(30, 2, null, 1));
        populationSizeSpinnerWithLabel.getSpinner().addChangeListener(this);
        scalerComboBoxWithLabel = new ComboBoxWithLabel<>("Metoda skalowania", new Vector<>(List.of(
                "Brak", "Liniowa"
        )));
        scalerComboBoxWithLabel.addItemListener(this);
        selectorComboBoxWithLabel = new ComboBoxWithLabel<>("Metoda selekcji", new Vector<>(List.of(
                "Ruletka", "Turniej", "Rangi", "Losowa"
        )));
        selectorComboBoxWithLabel.addItemListener(this);
        crossingProbabilitySpinnerWithLabel = new SpinnerWithLabel("Prawdopodobieństwo krzyżowania", new SpinnerNumberModel(.98, 0., 1., 0.01));
        pairMatcherComboBoxWithLabel = new ComboBoxWithLabel<>("Metoda doboru partnerów", new Vector<>(List.of(
                "Po kolei", "Losowa"
        )));
        pairMatcherComboBoxWithLabel.addItemListener(this);
        mutationChanceSpinnerWithLabel = new SpinnerWithLabel("Prawdopodobieństwo mutacji", new SpinnerNumberModel(0.01, 0.0, 1.0, 0.001));
        shouldMutateSurvivorsCheckBoxWithLabel = new CheckBoxWithLabel("Mutacja starszych osobników", false);

        multiplicationFactorSpinnerWithLabel = new SpinnerWithLabel("Współczynnik zwielokrotnienia", new SpinnerNumberModel(2., 0., 3., 0.01));
        multiplicationFactorSpinnerWithLabel.setVisible(false);
        tournamentSizeSpinnerWithLabel = new SpinnerWithLabel("Rozmiar turnieju", new SpinnerNumberModel(5, 2, getPopulationSize(), 1));
        tournamentSizeSpinnerWithLabel.setVisible(false);
        pairMatcherRepetitionsAllowedCheckBoxWithLabel = new CheckBoxWithLabel("Powtórzenia przy doborze partnerów", false);
        pairMatcherRepetitionsAllowedCheckBoxWithLabel.setVisible(false);

        add(uniqueInitialPopulationCheckBoxWithLabel);
        add(populationSizeSpinnerWithLabel);
        add(scalerComboBoxWithLabel);
        add(multiplicationFactorSpinnerWithLabel);
        add(selectorComboBoxWithLabel);
        add(tournamentSizeSpinnerWithLabel);
        add(crossingProbabilitySpinnerWithLabel);
        add(pairMatcherComboBoxWithLabel);
        add(pairMatcherRepetitionsAllowedCheckBoxWithLabel);
        add(mutationChanceSpinnerWithLabel);
        add(shouldMutateSurvivorsCheckBoxWithLabel);
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource().equals(selectorComboBoxWithLabel.getComboBox())) {
            tournamentSizeSpinnerWithLabel.setVisible(selectorComboBoxWithLabel.getSelectedItem().equals("Turniej"));
        } else if (e.getSource().equals(pairMatcherComboBoxWithLabel.getComboBox())) {
            pairMatcherRepetitionsAllowedCheckBoxWithLabel.setVisible(pairMatcherComboBoxWithLabel.getSelectedItem().equals("Losowa"));
        } else if (e.getSource().equals(scalerComboBoxWithLabel.getComboBox())) {
            multiplicationFactorSpinnerWithLabel.setVisible(scalerComboBoxWithLabel.getSelectedItem().equals("Liniowa"));
        }
    }

    @Override
    public void stateChanged(ChangeEvent e) {
        if (e.getSource().equals(populationSizeSpinnerWithLabel.getSpinner())) {
            var currentTournamentSize = (int) tournamentSizeSpinnerWithLabel.getValue();
            currentTournamentSize = Math.min(getPopulationSize(), currentTournamentSize);
            tournamentSizeSpinnerWithLabel.getSpinner().setModel(new SpinnerNumberModel(currentTournamentSize, 2, getPopulationSize(), 1));
        }
    }

    public boolean getUniqueInitialPopulation() {
        return uniqueInitialPopulationCheckBoxWithLabel.isSelected();
    }

    public int getPopulationSize() {
        return (int) populationSizeSpinnerWithLabel.getValue();
    }

    public double getMutationChance() {
        return (double) mutationChanceSpinnerWithLabel.getValue();
    }

    public boolean getShouldMutateSurvivors() {
        return shouldMutateSurvivorsCheckBoxWithLabel.isSelected();
    }

    public double getCrossingProbability() {
        return (double) crossingProbabilitySpinnerWithLabel.getValue();
    }

    public Scaler getScaler() {
        return switch (scalerComboBoxWithLabel.getSelectedItem()) {
            case "Liniowa" -> new LinearScaler((double) multiplicationFactorSpinnerWithLabel.getValue());
            default -> new NoScaler();
        };
    }

    public Selector getSelector() {
        return switch (selectorComboBoxWithLabel.getSelectedItem()) {
            case "Turniej" -> new TournamentSelector((int) tournamentSizeSpinnerWithLabel.getValue());
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
