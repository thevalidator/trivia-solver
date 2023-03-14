/*
 * Copyright (C) 2022 thevalidator
 */
package ru.thevalidator.galaxytriviasolver;

import com.formdev.flatlaf.FlatDarkLaf;
import com.yworks.util.annotation.Obfuscation;
import javax.swing.UIManager;
import ru.thevalidator.galaxytriviasolver.gui.TriviaMainWindow;

/**
 *
 * @author thevalidator <the.validator@yandex.ru>
 */
@Obfuscation(exclude = true, applyToMembers = true)
public class GalaxyTriviaSolver {

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            UIManager.put("Button.arc", 30);
            //UIManager.put("Component.arc", 999);
            //UIManager.put("ProgressBar.arc", 999);
            //UIManager.put("TextComponent.arc", 999);
            FlatDarkLaf.setup();
            
            new TriviaMainWindow().setVisible(true);
        });
    }
}
