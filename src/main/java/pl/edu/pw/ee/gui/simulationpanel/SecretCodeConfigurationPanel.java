package pl.edu.pw.ee.gui.simulationpanel;

import pl.edu.pw.ee.game.Code;
import pl.edu.pw.ee.game.GameVariant;
import pl.edu.pw.ee.gui.utils.GuiUtils;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class SecretCodeConfigurationPanel extends JPanel {

    private final InputPanel secretCodeInputPanel;
    private final JRadioButton fixedSecretCodeRadioButton;

    public SecretCodeConfigurationPanel() {
        secretCodeInputPanel = new InputPanel(ConfigurationInputPanel.DEFAULT_GAME_VARIANT, true);

        setLayout(new GridBagLayout());
        setBorder(new TitledBorder("Tajne hasło"));

        var secretCodeRandomnessRadioButtonsPanel = new JPanel();
        secretCodeRandomnessRadioButtonsPanel.setLayout(new BoxLayout(secretCodeRandomnessRadioButtonsPanel, BoxLayout.Y_AXIS));

        var secretCodeRandomnessButtonGroup = new ButtonGroup();
        var randomSecretCodeRadioButton = new JRadioButton("Używaj losowego w każdej iteracji");
        randomSecretCodeRadioButton.addActionListener(e -> secretCodeInputPanel.setVisible(false));
        fixedSecretCodeRadioButton = new JRadioButton("Używaj tego samego w każdej iteracji", true);
        fixedSecretCodeRadioButton.addActionListener(e -> secretCodeInputPanel.setVisible(true));
        secretCodeRandomnessButtonGroup.add(randomSecretCodeRadioButton);
        secretCodeRandomnessButtonGroup.add(fixedSecretCodeRadioButton);

        GridBagConstraints gbc = GuiUtils.getListConstraints();
        secretCodeRandomnessRadioButtonsPanel.add(randomSecretCodeRadioButton);
        secretCodeRandomnessRadioButtonsPanel.add(fixedSecretCodeRadioButton);

        add(secretCodeRandomnessRadioButtonsPanel, gbc);
        add(secretCodeInputPanel, gbc);
    }

    public void update(GameVariant gameVariant) {
        secretCodeInputPanel.update(gameVariant);
    }

    public boolean isSecretCodeValid() {
        if (fixedSecretCodeRadioButton.isSelected()) {
            return secretCodeInputPanel.isInputValid();
        }
        return true;
    }

    public Code getSecretCode() {
        if (fixedSecretCodeRadioButton.isSelected() && secretCodeInputPanel.isInputValid()) {
            return secretCodeInputPanel.getInputAsCode();
        }
        return null;
    }
}
