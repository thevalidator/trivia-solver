/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.galaxytriviasolver.gui.v2;

import java.awt.Desktop;
import java.awt.Dimension;
import java.io.IOException;
import java.net.URI;
import javax.swing.JEditorPane;
import javax.swing.event.HyperlinkEvent;
import ru.thevalidator.galaxytriviasolver.util.identity.os.OSValidator;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class AboutPanel extends JEditorPane {

    private static final URI link = URI.create("https://cloud.mail.ru/public/zNk6/pHo8pz6Lf");

    public AboutPanel() {
        setContentType("text/html");
        setEditable(false);
        setPreferredSize(new Dimension(320, 270));
        setText("<p>Galaxy Trivia solver helps you to win in the <br />"
                + "Trivia game and get into the daily top 10 list.<br /><br />"
                + "For use select person (add new if no persons), <br />"
                + "choose server and topic you want to play <br />"
                + "then click start button.<br /><br />"
                + "<a href=\"\">"
                + "Actual version and instructions"
                + "</a><br/><br/>"
                + "v1.0.5.0-GCS<br/>[thevalidator]<br/>2023, May"
                + "</p>"
                + "<p>Running on " + OSValidator.OS_NAME + "<br/>"
                + "Powered by Java</p>");
        addHyperlinkListener((e) -> {
            if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
                try {
                    Desktop.getDesktop().browse(link);
                } catch (IOException ignored) {
                }
            }

        });
    }

}
