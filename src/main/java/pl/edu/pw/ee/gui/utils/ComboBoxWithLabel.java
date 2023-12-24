package pl.edu.pw.ee.gui.utils;

import lombok.Getter;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ItemListener;
import java.util.Vector;

public class ComboBoxWithLabel<T> extends JPanel {

    @Getter
    private final JComboBox<T> comboBox;

    public ComboBoxWithLabel(String text, Vector<T> items) {
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        setBorder(new EmptyBorder(0, 5, 5, 5));

        comboBox = new JComboBox<>(items);

        var comboBoxSize = new Dimension(80, comboBox.getEditor().getEditorComponent().getPreferredSize().height);
        comboBox.setPreferredSize(comboBoxSize);
        comboBox.setMaximumSize(comboBoxSize);

        add(new JLabel(text));
        add(Box.createHorizontalGlue());
        add(comboBox);
    }

    @SuppressWarnings("unchecked")
    public T getSelectedItem() {
        return (T) comboBox.getSelectedItem();
    }

    public void addItemListener(ItemListener itemListener) {
        comboBox.addItemListener(itemListener);
    }

}
