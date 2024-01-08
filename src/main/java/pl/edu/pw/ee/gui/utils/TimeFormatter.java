package pl.edu.pw.ee.gui.utils;

import javax.swing.*;

public class TimeFormatter extends JFormattedTextField.AbstractFormatter {

    @Override
    public Object stringToValue(String text) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String valueToString(Object value) {
        if (value == null) {
            return "";
        }
        long val = (long) value;
        if (val >= 1000) {
            return String.format("%.2f s", val / 1000.);
        }
        return String.format("%d ms", val);
    }

}
