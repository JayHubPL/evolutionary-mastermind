package pl.edu.pw.ee.gui.simulationpanel;

import pl.edu.pw.ee.game.Code;
import pl.edu.pw.ee.game.GameVariant;
import pl.edu.pw.ee.gui.utils.GuiUtils;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class FirstGuessConfigurationPanel extends JPanel {

    private final InputPanel firstGuessInputPanel;
    private final JRadioButton fixedFirstGuessRadioButton;

    public FirstGuessConfigurationPanel() {
        firstGuessInputPanel = new InputPanel(ConfigurationInputPanel.DEFAULT_GAME_VARIANT, false);

        setBorder(new TitledBorder("Kombinacja początkowa"));
        setLayout(new GridBagLayout());

        var firstGuessRandomnessRadioButtonsPanel = new JPanel();
        firstGuessRandomnessRadioButtonsPanel.setLayout(new BoxLayout(firstGuessRandomnessRadioButtonsPanel, BoxLayout.Y_AXIS));

        var firstGuessRandomnessButtonGroup = new ButtonGroup();
        var randomFirstGuessRadioButton = new JRadioButton("Używaj losowej");
        randomFirstGuessRadioButton.addActionListener(e -> firstGuessInputPanel.setVisible(false));
        fixedFirstGuessRadioButton = new JRadioButton("Używaj predefiniowanej", true);
        fixedFirstGuessRadioButton.addActionListener(e -> firstGuessInputPanel.setVisible(true));
        firstGuessRandomnessButtonGroup.add(randomFirstGuessRadioButton);
        firstGuessRandomnessButtonGroup.add(fixedFirstGuessRadioButton);

        GridBagConstraints gbc = GuiUtils.getListConstraints();
        firstGuessRandomnessRadioButtonsPanel.add(randomFirstGuessRadioButton);
        firstGuessRandomnessRadioButtonsPanel.add(fixedFirstGuessRadioButton);

        add(firstGuessRandomnessRadioButtonsPanel, gbc);
        add(firstGuessInputPanel, gbc);
    }

    public void update(GameVariant gameVariant) {
        firstGuessInputPanel.update(gameVariant);
    }

    public Code getInitialGuess() {
        if (fixedFirstGuessRadioButton.isSelected()) {
            return firstGuessInputPanel.getInputAsCode();
        }
        return null;
    }
}
