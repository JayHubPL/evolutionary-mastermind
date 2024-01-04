package pl.edu.pw.ee.gui.gamepanel;

import pl.edu.pw.ee.gui.MainFrame;
import pl.edu.pw.ee.gui.utils.MarkdownToHTMLConverter;
import pl.edu.pw.ee.utils.ResourceUtils;

import javax.swing.*;
import java.awt.*;

public class RulesPanel extends JPanel {

    public static final String NAME = "RULES";

    private static final String rulesHtmlText = MarkdownToHTMLConverter.convert(ResourceUtils.readResourceFile("markdown/rules.md")
            .replace("$example_secret.png$", ResourceUtils.getResourceURL("images/example_secret.png").toString())
            .replace("$example_guess.png$", ResourceUtils.getResourceURL("images/example_guess.png").toString()));

    public RulesPanel(MainFrame.MainPanel parent) {
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
        var chooseVariantLabel = new JLabel("Rozpocznij grę:");
        chooseVariantLabel.setFont(font);
        var duplicatesCheckBox = new JCheckBox("Dozwolone powtórzenia w haśle", true);
        duplicatesCheckBox.setFont(buttonFont);
        var classicVersionButton = new JButton("Classic");
        classicVersionButton.setFont(buttonFont);
        classicVersionButton.addActionListener(e -> parent.showCard(GamePanel.NAME_CLASSIC, duplicatesCheckBox.isSelected()));
        var deluxeVersionButton = new JButton("Deluxe");
        deluxeVersionButton.setFont(buttonFont);
        deluxeVersionButton.addActionListener(e -> parent.showCard(GamePanel.NAME_DELUXE, duplicatesCheckBox.isSelected()));

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
