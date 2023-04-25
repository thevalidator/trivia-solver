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
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.thevalidator.galaxytriviasolver.exception.CanNotCreateWebdriverException;
import static ru.thevalidator.galaxytriviasolver.gui.v2.TriviaMainWindow.driver;
import ru.thevalidator.galaxytriviasolver.module.trivia.State;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public interface WebDriverUtil {

    WebDriver createWebDriver(State state) throws CanNotCreateWebdriverException;

    static WebDriverWait wait(WebDriver webDriver, int millis) {
        return new WebDriverWait(driver, Duration.ofMillis(millis));
    }

    static void takeScreenshot(WebDriver webDriver, String path) {
        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(src, new File(path));
        } catch (IOException ignoredException) {
        }
    }

    static void savePageSourceToFile(WebDriver webDriver, String path) {
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(path + "_src", Charset.forName("UTF-8")))) {
            bufferedWriter.write(driver.getPageSource());
        } catch (IOException ignoredException) {
        }
    }

}
