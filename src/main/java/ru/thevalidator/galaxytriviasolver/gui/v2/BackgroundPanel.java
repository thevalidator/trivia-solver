/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.galaxytriviasolver.gui.v2;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class BackgroundPanel extends JPanel {
    
    private final BufferedImage image;

    public BackgroundPanel() {
        this.image = loadImage();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //g.drawImage(image, 0, 0, 750, 422, this);
        g.drawImage(image, 0, 0, this);
    }

    private BufferedImage loadImage() {
        BufferedImage img = null;
        try {
            String s = getClass().getClassLoader().getResource("bkgnd.jpg").getPath();
            File f = new File(s);
            img = ImageIO.read(f);
        } catch (IOException ex) {
            //missmatch exception
        }
        
        return img;
    }

}
