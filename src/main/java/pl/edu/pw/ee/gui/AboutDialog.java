package pl.edu.pw.ee.gui;

import lombok.EqualsAndHashCode;
import lombok.Value;

import javax.swing.*;

@Value
@EqualsAndHashCode(callSuper = false)
public class AboutDialog extends JDialog {

    JLabel label = new JLabel("O programie");

    public AboutDialog(JFrame parent) {
        super(parent, "O programie", true);
        setSize(300, 200);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(parent);

        label.setHorizontalAlignment(SwingConstants.CENTER);
        add(label);

        setVisible(true);
    }

}
