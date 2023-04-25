/*
 * Copyright (C) 2023 thevalidator
 */

package ru.thevalidator.galaxytriviasolver.util.webdriver.impl;

import java.time.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import ru.thevalidator.galaxytriviasolver.exception.CanNotCreateWebdriverException;
import ru.thevalidator.galaxytriviasolver.exception.ExceptionUtil;
import ru.thevalidator.galaxytriviasolver.module.trivia.State;
import ru.thevalidator.galaxytriviasolver.util.webdriver.WebDriverUtil;


public class ChromeWebDriverUtilImpl implements WebDriverUtil {
    
    private static final Logger logger = LogManager.getLogger(ChromeWebDriverUtilImpl.class);

    @Override
    public WebDriver createWebDriver(State state) throws CanNotCreateWebdriverException {
        WebDriver webDriver = null;
        try {
            ChromeOptions options = new ChromeOptions();
            //options.addArguments("--remote-allow-origins=*");
            if (state.isHeadless()) {
                options.addArguments("--headless=new");
            }
            webDriver = new ChromeDriver(options);
            if (state.isHeadless()) {
                webDriver.manage().window().maximize();
            } else {
                webDriver.manage().window().setSize(new Dimension(1600, 845));
            }
            //webDriver.manage().window().setPosition((new Point(-5, 0)));
            webDriver.manage().window().setPosition((new Point(-6, 0)));
            webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));

        } catch (Exception e) {
            logger.error(ExceptionUtil.getFormattedDescription(e));
            if (webDriver != null) {
                webDriver.quit();
            }
            throw new CanNotCreateWebdriverException("Can't create webdriver");
        }
        return webDriver;
    }

}
