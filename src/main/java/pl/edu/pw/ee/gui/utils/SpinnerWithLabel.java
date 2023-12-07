package pl.edu.pw.ee.gui.utils;

import lombok.Getter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class SpinnerWithLabel extends JPanel {

    @Getter
    private final JSpinner spinner;

    public SpinnerWithLabel(String text, SpinnerNumberModel model) {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBorder(new EmptyBorder(5, 5, 5, 5));

        spinner = new JSpinner(model);
        var spinnerSize = new Dimension(60, spinner.getEditor().getPreferredSize().height);
        spinner.setPreferredSize(spinnerSize);
        spinner.setMaximumSize(spinnerSize);

        add(new JLabel(text));
        add(Box.createHorizontalGlue());
        add(spinner);
    }

}
