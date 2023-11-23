package pl.edu.pw.ee;

import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;

@Slf4j
public class Experimental extends JFrame {

    public Experimental() {
        setLayout(new GridBagLayout());
        setResizable(true);

        var button = new JButton("Hello");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.PAGE_START;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;

        add(button, gbc);
        gbc.gridy = 1;
        add(Box.createVerticalGlue(), gbc);

        pack();
        setVisible(true);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(Experimental::new);
    }

}
