package pl.edu.pw.ee.gui;

import lombok.EqualsAndHashCode;
import lombok.Value;
import pl.edu.pw.ee.gui.utils.MarkdownToHTMLConverter;
import pl.edu.pw.ee.utils.FileReader;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import java.awt.*;

@Value
@EqualsAndHashCode(callSuper = false)
public class AboutDialog extends JDialog implements HyperlinkListener {

    private static final String infoHtmlText = MarkdownToHTMLConverter.convert(FileReader.readResourceFile("markdown/about.md"));

    public AboutDialog(JFrame parent) {
        super(parent, "O programie", true);
        setLayout(new BorderLayout());
        setResizable(false);
        setSize(550, 420);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(parent);

        var textPane = new JTextPane();
        textPane.putClientProperty(JEditorPane.HONOR_DISPLAY_PROPERTIES, true);
        textPane.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textPane.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        textPane.setContentType("text/html");
        textPane.setText(infoHtmlText);
        textPane.setEditable(false);
        textPane.addHyperlinkListener(this);

        add(textPane, BorderLayout.CENTER);

        setVisible(true);
    }

    @Override
    public void hyperlinkUpdate(HyperlinkEvent e) {
        if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
            if (Desktop.isDesktopSupported()) {
                try {
                    Desktop.getDesktop().browse(e.getURL().toURI());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

}
