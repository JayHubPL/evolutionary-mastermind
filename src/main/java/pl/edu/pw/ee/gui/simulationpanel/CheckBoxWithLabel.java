package pl.edu.pw.ee.gui.simulationpanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;

public class CheckBoxWithLabel extends JPanel {

    private final JCheckBox checkBox;

    public CheckBoxWithLabel(String text, boolean selected) {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBorder(new EmptyBorder(5, 5, 5, 5));

        checkBox = new JCheckBox();
        checkBox.setSelected(selected);

        add(new JLabel(text));
        add(Box.createHorizontalGlue());
        add(checkBox);
    }

    public boolean isSelected() {
        return checkBox.isSelected();
    }

    public void addActionListener(ActionListener l) {
        checkBox.addActionListener(l);
    }

}
