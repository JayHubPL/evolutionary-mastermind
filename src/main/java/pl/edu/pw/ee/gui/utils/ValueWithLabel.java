package pl.edu.pw.ee.gui.utils;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.text.Format;
import java.text.NumberFormat;

public class ValueWithLabel extends JPanel {

    private final JFormattedTextField formattedTextField;

    public ValueWithLabel(String text, JFormattedTextField.AbstractFormatter formatter) {
        this(text, new JFormattedTextField(formatter), null);
    }

    private ValueWithLabel(String text, Format format, Object initialValue) {
        this(text, new JFormattedTextField(format), initialValue);
    }

    private ValueWithLabel(String text, JFormattedTextField formattedTextField, Object initialValue) {
        var layout = new GroupLayout(this);
        setLayout(layout);
        setBorder(new EmptyBorder(5, 5, 0, 5));
        setFocusable(false);

        var label = new JLabel(text);
        this.formattedTextField = formattedTextField;
        this.formattedTextField.setFocusable(false);
        formattedTextField.setHorizontalAlignment(JFormattedTextField.CENTER);
        formattedTextField.setEditable(false);
        if (initialValue != null) {
            formattedTextField.setValue(initialValue);
        }
        var textFieldSize = new Dimension(80, formattedTextField.getPreferredSize().height);
        formattedTextField.setMinimumSize(textFieldSize);
        formattedTextField.setPreferredSize(textFieldSize);
        formattedTextField.setMaximumSize(textFieldSize);

        var hGroup = layout.createSequentialGroup();
        var vGroup = layout.createParallelGroup(GroupLayout.Alignment.BASELINE);

        hGroup.addComponent(label);
        var gapSize = Math.max(0, 250 - label.getPreferredSize().width);
        hGroup.addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, gapSize, gapSize);
        hGroup.addComponent(formattedTextField);

        vGroup.addComponent(label);
        vGroup.addComponent(formattedTextField);

        layout.setHorizontalGroup(hGroup);
        layout.setVerticalGroup(vGroup);
    }

    @Override
    public Dimension getMaximumSize() {
        return new Dimension(500, super.getMaximumSize().height);
    }

    public ValueWithLabel(String text, NumberFormat format) {
        this(text, format, null);
    }

    public ValueWithLabel(String text) {
        this(text, NumberFormat.getNumberInstance());
    }

    public void setValue(Object value) {
        formattedTextField.setValue(value);
    }

}
