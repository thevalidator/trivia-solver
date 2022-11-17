/*
 * Copyright (C) 2022 thevalidator
 */
package ru.thevalidator.galaxytriviasolver;

import com.formdev.flatlaf.FlatDarkLaf;
//import com.formdev.flatlaf.FlatLightLaf;
import ru.thevalidator.galaxytriviasolver.gui.AppWindow;

/**
 *
 * @author thevalidator <the.validator@yandex.ru>
 */
public class GalaxyTriviaSolver {

    public static void main(String[] args) {
        java.awt.EventQueue.invokeLater(() -> {
            FlatDarkLaf.setup();
            new AppWindow().setVisible(true);
        });
    }
}
