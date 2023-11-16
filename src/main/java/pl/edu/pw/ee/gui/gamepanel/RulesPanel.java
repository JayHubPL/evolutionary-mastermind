package pl.edu.pw.ee.gui.gamepanel;

import pl.edu.pw.ee.game.GameVariant;

import javax.swing.*;
import java.awt.*;

public class RulesPanel extends JPanel {

    public RulesPanel(GameCard gameCard) {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setBackground(Color.PINK); // debug

        var duplicatesCheckBox = new JCheckBox("Dozwolone powtórzenia");
        var classicVersionButton = new JButton("Classic");
        classicVersionButton.addActionListener(e -> gameCard.setContents(new GamePanel(GameVariant.classic(duplicatesCheckBox.isSelected()))));
        var deluxeVersionButton = new JButton("Deluxe");
        deluxeVersionButton.addActionListener(e -> gameCard.setContents(new GamePanel(GameVariant.deluxe(duplicatesCheckBox.isSelected()))));

        add(duplicatesCheckBox);
        add(classicVersionButton);
        add(deluxeVersionButton);
    }

}
