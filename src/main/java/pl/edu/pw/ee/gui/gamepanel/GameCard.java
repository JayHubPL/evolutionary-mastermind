package pl.edu.pw.ee.gui.gamepanel;

import pl.edu.pw.ee.gui.utils.ContainerPanel;

import java.awt.*;

public class GameCard extends ContainerPanel {

    public static final String NAME = "GAME_PANEL";

    private final RulesPanel rulesPanel;

    public GameCard() {
        rulesPanel = new RulesPanel(this);

        setLayout(new BorderLayout());
        setBackground(Color.CYAN); // debug

        setContents(rulesPanel);
    }

    public void reset() {
        setContents(rulesPanel);
    }

}
