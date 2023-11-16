package pl.edu.pw.ee.gui.utils;

import lombok.Getter;

import javax.swing.*;

public abstract class ContainerPanel extends JPanel {

    @Getter
    protected JPanel contents;

    public void setContents(JPanel contents) {
        if (this.contents != null) {
            remove(this.contents);
        }
        this.contents = contents;
        add(this.contents);
        revalidate();
        repaint();
    }

}
