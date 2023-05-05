/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.galaxytriviasolver.util.webdriver.impl;

import java.time.Duration;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import ru.thevalidator.galaxytriviasolver.exception.CanNotCreateWebdriverException;
import ru.thevalidator.galaxytriviasolver.exception.ExceptionUtil;
import ru.thevalidator.galaxytriviasolver.options.ChromeDriverArgument;
import ru.thevalidator.galaxytriviasolver.options.webdriver.WebDriverArgument;
import ru.thevalidator.galaxytriviasolver.util.webdriver.WebDriverUtil;

public class ChromeWebDriverUtilImpl implements WebDriverUtil {
    
    private static final Logger logger = LogManager.getLogger(ChromeWebDriverUtilImpl.class);

    @Override
    public WebDriver createWebDriver(WebDriverArgument args) throws CanNotCreateWebdriverException {
        ChromeDriverArgument chromeDriverArgs = (ChromeDriverArgument) args;
        WebDriver webDriver = null;
        try {
            ChromeOptions options = new ChromeOptions();
            if (chromeDriverArgs.isHeadlessMode()) {
                options.addArguments("--headless=new");
            }
            if (chromeDriverArgs.hasRemoteAllowOriginsOption()) {
                options.addArguments("--remote-allow-origins=*");
            }
            
            webDriver = new ChromeDriver(options);
            if (chromeDriverArgs.isHeadlessMode()) {
                webDriver.manage().window().setSize(new Dimension(1600, 845));
            } else {
                webDriver.manage().window().maximize();
            }
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
