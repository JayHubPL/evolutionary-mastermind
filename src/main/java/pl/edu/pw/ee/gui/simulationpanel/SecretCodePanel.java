package pl.edu.pw.ee.gui.simulationpanel;

import pl.edu.pw.ee.game.Code;
import pl.edu.pw.ee.gui.gamepanel.GamePanel;
import pl.edu.pw.ee.gui.utils.ColorDot;
import pl.edu.pw.ee.gui.utils.GuiUtils;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class SecretCodePanel extends JPanel {

    private final JPanel wrapper;

    public SecretCodePanel() {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new TitledBorder("Tajne hasło"));

        wrapper = new JPanel();
        wrapper.setLayout(new FlowLayout(FlowLayout.LEFT));

        add(wrapper);
    }

    public void setSecretCode(Code secretCode) {
        wrapper.removeAll();
        for (var codePin : secretCode) {
            wrapper.add(new ColorDot(GamePanel.COLORS[codePin.getIndex()], 40));
        }
        setMinimumSize(new Dimension(Integer.MAX_VALUE, getPreferredSize().height));
        GuiUtils.revalidateAndRepaintLater(this);
    }

}
