package pl.edu.pw.ee.gui.gamepanel;

import pl.edu.pw.ee.game.GameVariant;
import pl.edu.pw.ee.gui.utils.MarkdownToHTMLConverter;
import pl.edu.pw.ee.utils.FileReader;

import javax.swing.*;
import java.awt.*;

public class RulesPanel extends JPanel {

    private static final String rulesHtmlText = MarkdownToHTMLConverter.convert(FileReader.readResourceFile("markdown/rules.md")
            .replace("$example.jpg$", FileReader.getResourceURL("icons/example.jpg").toString()));

    public RulesPanel(GameCard gameCard) {
        setLayout(new BorderLayout());

        var textPane = new JTextPane();
        textPane.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, true);
        textPane.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textPane.setMargin(new Insets(0, 10, 10, 10));
        textPane.setContentType("text/html");
        textPane.setText(rulesHtmlText);
        textPane.setEditable(false);

        var panelWithInputs = new JPanel(new FlowLayout(FlowLayout.LEFT));

        var chooseVariantLabel = new JLabel("Wybierz wariant gry aby rozpocząć: ");
        chooseVariantLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        var duplicatesCheckBox = new JCheckBox("Dozwolone powtórzenia kolorów w haśle");
        var classicVersionButton = new JButton("Classic");
        classicVersionButton.addActionListener(e -> gameCard.setContents(new GamePanel(GameVariant.classic(duplicatesCheckBox.isSelected()))));
        var deluxeVersionButton = new JButton("Deluxe");
        deluxeVersionButton.addActionListener(e -> gameCard.setContents(new GamePanel(GameVariant.deluxe(duplicatesCheckBox.isSelected()))));

        panelWithInputs.add(chooseVariantLabel);
        panelWithInputs.add(classicVersionButton);
        panelWithInputs.add(deluxeVersionButton);
        panelWithInputs.add(duplicatesCheckBox);

        add(textPane, BorderLayout.CENTER);
        add(panelWithInputs, BorderLayout.SOUTH);
    }

}
