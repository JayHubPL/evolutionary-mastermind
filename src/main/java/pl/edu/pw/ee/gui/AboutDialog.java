package pl.edu.pw.ee.gui;

import lombok.EqualsAndHashCode;
import lombok.Value;
import org.pegdown.PegDownProcessor;

import javax.swing.*;
import javax.swing.event.HyperlinkEvent;
import javax.swing.event.HyperlinkListener;
import java.awt.*;

@Value
@EqualsAndHashCode(callSuper = false)
public class AboutDialog extends JDialog implements HyperlinkListener {

    private static final String infoHtmlText = new PegDownProcessor().markdownToHtml("""
            ## Symulator do algorytmów ewolucyjnych dla problemu gry Mastermind
            ### Autor: Hubert Mazur
            #### Promotor: dr hab. inż. Paweł Piotrowski

            Projekt realizowany w ramach pracy inżynierskiej w semestrze 2023/24
            na kierunku **Informatyka Stosowana** na **Politechnice Warszawskiej** zatytułowanej:

            *Implementacja i analiza wariantów algorytmów ewolucyjnych z wykorzystaniem
            indywidualnego symulatora do rozwiązywania problemu gry Mastermind*

            Kod źródłowy: [GitHub](https://github.com/JayHubPL)
            """
    );

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
