/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.galaxytriviasolver.util.webdriver;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.time.Duration;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.thevalidator.galaxytriviasolver.exception.CanNotCreateWebdriverException;
import ru.thevalidator.galaxytriviasolver.options.webdriver.WebDriverArgument;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public interface WebDriverUtil {

    WebDriver createWebDriver(WebDriverArgument args) throws CanNotCreateWebdriverException;

    static WebDriverWait wait(WebDriver driver, int millis) {
        return new WebDriverWait(driver, Duration.ofMillis(millis));
    }

    static void takeScreenshot(WebDriver driver, String path) {
        if (driver != null) {
            try {
                File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                FileUtils.copyFile(src, new File(path));
            } catch (IOException ignoredException) {
            }
        }
    }

    static void savePageSourceToFile(WebDriver driver, String path) {
        if (driver != null) {
            try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path, Charset.forName("UTF-8")))) {
                bufferedWriter.write(driver.getPageSource());
            } catch (IOException ignoredException) {
            }
        }
    }
    
    static boolean isTerminated(RemoteWebDriver driver) {
        return driver.getSessionId() == null;
    }

}
