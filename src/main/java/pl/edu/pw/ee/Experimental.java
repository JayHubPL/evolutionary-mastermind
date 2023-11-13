package pl.edu.pw.ee;

import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;

@Slf4j
public class Experimental extends JFrame {

    public Experimental() {
        setLayout(new BorderLayout());
        setResizable(true);

        var topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        topPanel.setBackground(Color.CYAN);
        topPanel.setPreferredSize(new Dimension(-1, 50));

        var scrollPanel = new JPanel();
        scrollPanel.setLayout(new BoxLayout(scrollPanel, BoxLayout.Y_AXIS));
        scrollPanel.setBackground(Color.YELLOW);

        var scrollPane = new JScrollPane(scrollPanel);
        scrollPane.setBackground(Color.MAGENTA);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setPreferredSize(new Dimension(400, 400)); // set larger preferred size

        var addComponentButton = new JButton("Add component");
        addComponentButton.addActionListener(e -> {
            scrollPanel.add(new JButton(String.valueOf(scrollPanel.getComponentCount())));
            scrollPanel.revalidate();
            log.debug("scrollPanel preferredSize {} {}", scrollPanel.getPreferredSize().width, scrollPanel.getPreferredSize().height);
        });
        topPanel.add(addComponentButton);

        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
        pack();
        setVisible(true);
        log.debug("frame {} {}", getWidth(), getHeight());
        log.debug("topPanel {} {}", topPanel.getWidth(), topPanel.getHeight());
        log.debug("scrollPane {} {}", scrollPane.getWidth(), scrollPane.getHeight());
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(Experimental::new);
    }

}
