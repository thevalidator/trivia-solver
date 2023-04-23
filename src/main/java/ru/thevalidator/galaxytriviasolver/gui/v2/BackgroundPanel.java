/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.galaxytriviasolver.gui.v2;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.thevalidator.galaxytriviasolver.exception.ExceptionUtil;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class BackgroundPanel extends JPanel {
    
    private static final Logger logger = LogManager.getLogger(BackgroundPanel.class);
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
            img = ImageIO.read(getClass().getClassLoader().getResource("bkgnd.jpg"));
        } catch (IOException ex) {
            logger.error(ExceptionUtil.getFormattedDescription(ex));
        }
        
        return img;
    }

}
