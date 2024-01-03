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
        textPane.setFocusable(false);
        textPane.setCaretPosition(0); // align scroll pane to the top

        var panelWithInputs = new JPanel();
        panelWithInputs.setLayout(new BoxLayout(panelWithInputs, BoxLayout.X_AXIS));
        panelWithInputs.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        var font = new Font("Segoe UI", Font.BOLD, 20);
        var buttonFont = font.deriveFont(Font.PLAIN);
        var chooseVariantLabel = new JLabel("Wybierz wariant gry aby rozpocząć: ");
        chooseVariantLabel.setFont(font);
        var duplicatesCheckBox = new JCheckBox("Dozwolone powtórzenia kolorów w haśle");
        duplicatesCheckBox.setFont(buttonFont);
        var classicVersionButton = new JButton("Classic");
        classicVersionButton.setFont(buttonFont);
        classicVersionButton.addActionListener(e -> gameCard.setContents(new GamePanel(GameVariant.classic(duplicatesCheckBox.isSelected()))));
        var deluxeVersionButton = new JButton("Deluxe");
        deluxeVersionButton.setFont(buttonFont);
        deluxeVersionButton.addActionListener(e -> gameCard.setContents(new GamePanel(GameVariant.deluxe(duplicatesCheckBox.isSelected()))));

        panelWithInputs.add(chooseVariantLabel);
        panelWithInputs.add(Box.createHorizontalStrut(5));
        panelWithInputs.add(classicVersionButton);
        panelWithInputs.add(Box.createHorizontalStrut(5));
        panelWithInputs.add(deluxeVersionButton);
        panelWithInputs.add(Box.createHorizontalStrut(5));
        panelWithInputs.add(duplicatesCheckBox);

        panelWithInputs.setPreferredSize(new Dimension(Integer.MAX_VALUE, 60));

        add(new JScrollPane(textPane), BorderLayout.CENTER);
        add(panelWithInputs, BorderLayout.SOUTH);
    }

}
