package pl.edu.pw.ee.gui;

import lombok.EqualsAndHashCode;
import lombok.Value;

import javax.swing.*;
import java.awt.*;

@Value
@EqualsAndHashCode(callSuper = false)
public class SimulationPanel extends JPanel {

    public SimulationPanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));
        setBackground(Color.cyan);
    }

}
