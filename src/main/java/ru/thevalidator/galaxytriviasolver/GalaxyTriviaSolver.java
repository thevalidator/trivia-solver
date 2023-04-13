/*
 * Copyright (C) 2022 thevalidator
 */
package ru.thevalidator.galaxytriviasolver;

import com.formdev.flatlaf.FlatDarkLaf;
import javax.swing.UIManager;
import ru.thevalidator.galaxytriviasolver.gui.TriviaMainWindow;

/**
 *
 * @author thevalidator <the.validator@yandex.ru>
 */
public class GalaxyTriviaSolver {

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            UIManager.put("Button.arc", 30);
            FlatDarkLaf.setup();
            
            new TriviaMainWindow().setVisible(true);
        });
    }
}
