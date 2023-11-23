package pl.edu.pw.ee.gui.simulationpanel;

import pl.edu.pw.ee.game.GameVariant;
import pl.edu.pw.ee.game.RandomUtils;
import pl.edu.pw.ee.gui.gamepanel.ColorButton;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class SimulationPanel extends JPanel {

    public static final String NAME = "SIMULATION_PANEL";

    private final InputPanel hiddenCodeInputPanel;
    private final InputPanel firstGuessInputPanel;
    private GameVariant gameVariant;

    public SimulationPanel() {
        gameVariant = GameVariant.classic(true);
        hiddenCodeInputPanel = new InputPanel(true);
        firstGuessInputPanel = new InputPanel(false);

        setLayout(new BorderLayout());
        setBackground(Color.CYAN); // debug

        var configurationInputPanel = createConfigurationInputPanel();
        var centerPanel = new JPanel(); // debug
        centerPanel.setBackground(Color.PINK);

        add(configurationInputPanel, BorderLayout.WEST);
        add(centerPanel, BorderLayout.CENTER);
    }

    private JPanel createConfigurationInputPanel() {
        var configurationInputPanel = new JPanel();
        configurationInputPanel.setBorder(new TitledBorder("Konfiguracja"));
        configurationInputPanel.setLayout(new GridBagLayout());
        configurationInputPanel.setPreferredSize(new Dimension(250, Integer.MAX_VALUE));

        var hiddenCodePanel = createHiddenCodePanel();
        var firstGuessPanel = createFirstGuessPanel();
        var gameConfigPanel = createGameConfigPanel();
        var evoAlgorithmConfigPanel = createEvoAlgorithmConfigPanel();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.weighty = 0.0;

        configurationInputPanel.add(hiddenCodePanel, gbc);
        configurationInputPanel.add(firstGuessPanel, gbc);
        configurationInputPanel.add(gameConfigPanel, gbc);
        configurationInputPanel.add(evoAlgorithmConfigPanel, gbc);
        gbc.weighty = 1.0;
        configurationInputPanel.add(Box.createVerticalGlue(), gbc);

        return configurationInputPanel;
    }

    private JPanel createHiddenCodePanel() {
        var hiddenCodePanel = new JPanel();
        hiddenCodePanel.setBorder(new TitledBorder("Tajne hasło"));
        hiddenCodePanel.setLayout(new GridBagLayout());

        var hiddenCodeRandomnessRadioButtonsPanel = new JPanel();
        hiddenCodeRandomnessRadioButtonsPanel.setLayout(new BoxLayout(hiddenCodeRandomnessRadioButtonsPanel, BoxLayout.Y_AXIS));

        var hiddenCodeRandomnessButtonGroup = new ButtonGroup();
        var randomHiddenCodeRadioButton = new JRadioButton("Używaj losowego w każdej iteracji", true);
        randomHiddenCodeRadioButton.addActionListener(e -> hiddenCodeInputPanel.setVisible(false));
        var fixedHiddenCodeRadioButton = new JRadioButton("Używaj tego samego w każdej iteracji");
        fixedHiddenCodeRadioButton.addActionListener(e -> hiddenCodeInputPanel.setVisible(true));
        hiddenCodeRandomnessButtonGroup.add(randomHiddenCodeRadioButton);
        hiddenCodeRandomnessButtonGroup.add(fixedHiddenCodeRadioButton);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.weighty = 0.0;

        hiddenCodeRandomnessRadioButtonsPanel.add(randomHiddenCodeRadioButton);
        hiddenCodeRandomnessRadioButtonsPanel.add(fixedHiddenCodeRadioButton);

        hiddenCodePanel.add(hiddenCodeRandomnessRadioButtonsPanel, gbc);
        hiddenCodePanel.add(hiddenCodeInputPanel, gbc);

        return hiddenCodePanel;
    }

    private JPanel createFirstGuessPanel() {
        var firstGuessPanel = new JPanel();
        firstGuessPanel.setBorder(new TitledBorder("Kombinacja początkowa"));
        firstGuessPanel.setLayout(new GridBagLayout());

        var firstGuessRandomnessRadioButtonsPanel = new JPanel();
        firstGuessRandomnessRadioButtonsPanel.setLayout(new BoxLayout(firstGuessRandomnessRadioButtonsPanel, BoxLayout.Y_AXIS));

        var firstGuessRandomnessButtonGroup = new ButtonGroup();
        var randomFirstGuessRadioButton = new JRadioButton("Używaj losowej");
        randomFirstGuessRadioButton.addActionListener(e -> firstGuessInputPanel.setVisible(false));
        var fixedFirstGuessRadioButton = new JRadioButton("Używaj predefiniowanej", true);
        fixedFirstGuessRadioButton.addActionListener(e -> firstGuessInputPanel.setVisible(true));
        firstGuessRandomnessButtonGroup.add(randomFirstGuessRadioButton);
        firstGuessRandomnessButtonGroup.add(fixedFirstGuessRadioButton);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.weighty = 0.0;

        firstGuessRandomnessRadioButtonsPanel.add(randomFirstGuessRadioButton);
        firstGuessRandomnessRadioButtonsPanel.add(fixedFirstGuessRadioButton);

        firstGuessPanel.add(firstGuessRandomnessRadioButtonsPanel, gbc);
        firstGuessPanel.add(firstGuessInputPanel, gbc);

        return firstGuessPanel;
    }

    private JPanel createGameConfigPanel() {
        var gameConfigPanel = new JPanel();
        gameConfigPanel.setBorder(new TitledBorder("Wariant gry"));
        gameConfigPanel.setLayout(new BoxLayout(gameConfigPanel, BoxLayout.Y_AXIS));

        var gameVariantRadioButtonsPanel = new JPanel();
        var gameVariantButtonGroup = new ButtonGroup();
        var classicGameVariantButton = new JRadioButton("Classic", true);
        var deluxeGameVariantButton = new JRadioButton("Deluxe");
        gameVariantButtonGroup.add(classicGameVariantButton);
        gameVariantButtonGroup.add(deluxeGameVariantButton);
        gameVariantRadioButtonsPanel.add(classicGameVariantButton);
        gameVariantRadioButtonsPanel.add(deluxeGameVariantButton);

        var duplicateColorsAllowedCheckBoxWithLabel = new CheckBoxWithLabel("Powtarzanie kolorów w kodzie", true);

        gameConfigPanel.add(gameVariantRadioButtonsPanel);
        gameConfigPanel.add(duplicateColorsAllowedCheckBoxWithLabel);

        classicGameVariantButton.addActionListener(e -> updateGameVariant(GameVariant.classic(duplicateColorsAllowedCheckBoxWithLabel.isSelected())));
        deluxeGameVariantButton.addActionListener(e -> updateGameVariant(GameVariant.deluxe(duplicateColorsAllowedCheckBoxWithLabel.isSelected())));

        return gameConfigPanel;
    }

    private void updateGameVariant(GameVariant gameVariant) {
        if (this.gameVariant.equals(gameVariant)) {
            return;
        }
        this.gameVariant = gameVariant;
        hiddenCodeInputPanel.update();
        firstGuessInputPanel.update();
    }

    private JPanel createEvoAlgorithmConfigPanel() {
        var evoAlgorithmConfigPanel = new JPanel();
        evoAlgorithmConfigPanel.setLayout(new BoxLayout(evoAlgorithmConfigPanel, BoxLayout.Y_AXIS));
        evoAlgorithmConfigPanel.setBorder(new TitledBorder("Parametry algorytmu"));

        var uniqueInitialPopulationCheckBoxWithLabel = new CheckBoxWithLabel("Unikalne osobniki początkowe", false);
        var populationSizeSpinnerWithLabel = new SpinnerWithLabel("Rozmiar populacji", new SpinnerNumberModel(30, 2, null, 1));
        var mutationChanceSpinnerWithLabel = new SpinnerWithLabel("Szansa na mutację", new SpinnerNumberModel(0.05, 0.0, 1.0, 0.01));

        evoAlgorithmConfigPanel.add(uniqueInitialPopulationCheckBoxWithLabel);
        evoAlgorithmConfigPanel.add(populationSizeSpinnerWithLabel);
        evoAlgorithmConfigPanel.add(mutationChanceSpinnerWithLabel);

        return evoAlgorithmConfigPanel;
    }

    public class InputPanel extends JPanel {

        private final JButton generateButton;
        private final List<ColorButton> colorButtons = new ArrayList<>();

        public InputPanel(boolean withGenerateButton) {
            if (withGenerateButton) {
                generateButton = new JButton("Losuj");
                generateButton.addActionListener(e -> {
                    for (var button : colorButtons) {
                        button.setColorIndex(RandomUtils.randomColor(gameVariant.getNumberOfColors()).getIndex());
                    }
                });
            } else {
                generateButton = null;
            }
            update();
        }

        public void update() {
            if (generateButton != null) {
                remove(generateButton);
            }
            for (var button : colorButtons) {
                remove(button);
            }
            for (int i = 0; i < gameVariant.getCodeLength(); i++) {
                var colorButton = new ColorButton(gameVariant.getNumberOfColors(), 40);
                colorButtons.add(colorButton);
                add(colorButton);
            }
            if (generateButton != null) {
                add(generateButton);
            }
            SwingUtilities.invokeLater(() -> {
                firstGuessInputPanel.revalidate();
                firstGuessInputPanel.repaint();
            });
        }

    }

}
