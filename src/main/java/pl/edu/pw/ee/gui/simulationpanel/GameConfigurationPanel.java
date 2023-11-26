package pl.edu.pw.ee.gui.simulationpanel;

import pl.edu.pw.ee.game.GameVariant;

import javax.swing.*;
import javax.swing.border.TitledBorder;

public class GameConfigurationPanel extends JPanel {

    public GameConfigurationPanel(ConfigurationInputPanel parent) {

        setBorder(new TitledBorder("Wariant gry"));
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        var gameVariantRadioButtonsPanel = new JPanel();
        var gameVariantButtonGroup = new ButtonGroup();
        var classicGameVariantButton = new JRadioButton("Classic", true);
        var deluxeGameVariantButton = new JRadioButton("Deluxe");
        var duplicateColorsAllowedCheckBoxWithLabel = new CheckBoxWithLabel("Powtarzanie kolorów w kodzie", true);

        classicGameVariantButton.addActionListener(e -> parent.updateGameVariant(GameVariant.classic(duplicateColorsAllowedCheckBoxWithLabel.isSelected())));
        deluxeGameVariantButton.addActionListener(e -> parent.updateGameVariant(GameVariant.deluxe(duplicateColorsAllowedCheckBoxWithLabel.isSelected())));
        duplicateColorsAllowedCheckBoxWithLabel.addActionListener(e -> parent.updateGameVariant(parent.getGameVariant().withDuplicateColorsAllowed(duplicateColorsAllowedCheckBoxWithLabel.isSelected())));
        gameVariantButtonGroup.add(classicGameVariantButton);
        gameVariantButtonGroup.add(deluxeGameVariantButton);
        gameVariantRadioButtonsPanel.add(classicGameVariantButton);
        gameVariantRadioButtonsPanel.add(deluxeGameVariantButton);

        add(gameVariantRadioButtonsPanel);
        add(duplicateColorsAllowedCheckBoxWithLabel);
    }

}
