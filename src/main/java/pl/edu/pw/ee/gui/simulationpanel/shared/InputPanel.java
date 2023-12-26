package pl.edu.pw.ee.gui.simulationpanel.shared;

import pl.edu.pw.ee.game.Code;
import pl.edu.pw.ee.game.Color;
import pl.edu.pw.ee.game.GameVariant;
import pl.edu.pw.ee.game.RandomUtils;
import pl.edu.pw.ee.gui.gamepanel.ColorButton;
import pl.edu.pw.ee.gui.utils.GuiUtils;
import pl.edu.pw.ee.utils.FileReader;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class InputPanel extends JPanel {

    private static final String randomizeIconResourceName = "icons/reload.png";
    private final JPanel inputPanel;
    private final List<ColorButton> colorButtons = new ArrayList<>();
    private GameVariant gameVariant;

    public InputPanel(GameVariant gameVariant, boolean withGenerateButton) {
        this.gameVariant = gameVariant;

        setLayout(new GridBagLayout());

        inputPanel = new JPanel();
        var generateButtonPanel = new JPanel();
        if (withGenerateButton) {
            var generateButton = createGenerateButton();
            generateButtonPanel.add(generateButton);
        }
        GridBagConstraints gbc = GuiUtils.getListConstraints();
        add(inputPanel, gbc);
        add(generateButtonPanel, gbc);

        update(gameVariant);
    }

    public void update(GameVariant gameVariant) {
        this.gameVariant = gameVariant;
        if (!gameVariant.getCodeLength().equals(colorButtons.size())) {
            for (var button : colorButtons) {
                inputPanel.remove(button);
            }
            colorButtons.clear();
            for (int i = 0; i < gameVariant.getCodeLength(); i++) {
                var colorButton = new ColorButton(gameVariant.getNumberOfColors(), 40);
                colorButtons.add(colorButton);
                inputPanel.add(colorButton);
            }
            randomizeInput();
        } else if (!gameVariant.getDuplicateColorsAllowed() && !areButtonColorsUnique()) {
            randomizeInput();
        }
        GuiUtils.revalidateAndRepaintLater(this);
    }

    public boolean isInputValid() {
        return colorButtons.stream().map(ColorButton::getColorIndex).allMatch(Objects::nonNull) && (gameVariant.getDuplicateColorsAllowed() || areButtonColorsUnique());
    }

    public Code getInputAsCode() {
        return new Code(gameVariant, colorButtons.stream().map(ColorButton::getColorIndex).map(Color::of).collect(Collectors.toList()));
    }

    private JButton createGenerateButton() {
        var randomizeIcon = new ImageIcon(FileReader.getResourceURL(randomizeIconResourceName));
        randomizeIcon = new ImageIcon(randomizeIcon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH));
        var generateButton = new JButton("Losuj", randomizeIcon);
        generateButton.addActionListener(e -> randomizeInput());
        return generateButton;
    }

    private void randomizeInput() {
        var randomColorSequence = RandomUtils.randomColors(colorButtons.size(), gameVariant.getNumberOfColors(), gameVariant.getDuplicateColorsAllowed());
        for (int i = 0; i < colorButtons.size(); i++) {
            colorButtons.get(i).setColorIndex(randomColorSequence.get(i).getIndex());
        }
    }

    public boolean areButtonColorsUnique() {
        return colorButtons.stream().map(ColorButton::getColorIndex).distinct().count() == colorButtons.size();
    }

}