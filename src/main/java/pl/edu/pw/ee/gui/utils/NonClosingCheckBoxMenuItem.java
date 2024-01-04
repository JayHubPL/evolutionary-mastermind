package pl.edu.pw.ee.gui.utils;

import javax.swing.*;
import java.awt.event.MouseEvent;

public class NonClosingCheckBoxMenuItem extends JCheckBoxMenuItem {

    public NonClosingCheckBoxMenuItem(String text, boolean initialState) {
        super(text, initialState);
    }

    @Override
    protected void processMouseEvent(MouseEvent e) {
        if (e.getID() == MouseEvent.MOUSE_RELEASED && contains(e.getPoint())) {
            doClick();
            setArmed(true);
        } else {
            super.processMouseEvent(e);
        }
    }

}
