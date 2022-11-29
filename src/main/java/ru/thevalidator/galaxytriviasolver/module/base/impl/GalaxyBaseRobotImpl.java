/*
 * Copyright (C) 2022 thevalidator
 */
package ru.thevalidator.galaxytriviasolver.module.base.impl;

import io.github.bonigarcia.wdm.WebDriverManager;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.thevalidator.galaxytriviasolver.communication.Informer;
import ru.thevalidator.galaxytriviasolver.module.base.GalaxyBaseRobot;
import ru.thevalidator.galaxytriviasolver.module.trivia.State;
import ru.thevalidator.galaxytriviasolver.web.Locale;
import ru.thevalidator.galaxytriviasolver.web.Locator;
import static ru.thevalidator.galaxytriviasolver.web.Locator.*;

public class GalaxyBaseRobotImpl extends Informer implements GalaxyBaseRobot {

    private static final Logger logger = LogManager.getLogger(GalaxyBaseRobotImpl.class);
    private final State state;
    private WebDriver driver;

    public GalaxyBaseRobotImpl(State state) {
        this.state = state;
    }

    private WebDriver createWebDriver() {
        WebDriverManager.chromedriver().setup();

        //System.setProperty("webdriver.chrome.driver","C://softwares//drivers//chromedriver.exe");
        //ChromeOptions options = new ChromeOptions();
        //options.setBinary("/path/to/other/chrome/binary");
        //mobile emulation 1
//        Map<String, String> mobileEmulation = new HashMap<>();
//        mobileEmulation.put("deviceName", "Nexus 5");
//        ChromeOptions options = new ChromeOptions();
//        options.setExperimentalOption("mobileEmulation", mobileEmulation);
        //mobile emulation 2
//        Map<String, Object> deviceMetrics = new HashMap<>();
//        deviceMetrics.put("width", 360);
//        deviceMetrics.put("height", 640);
//        deviceMetrics.put("pixelRatio", 3.0);
//        Map<String, Object> mobileEmulation = new HashMap<>();
//        mobileEmulation.put("deviceMetrics", deviceMetrics);
//        mobileEmulation.put("userAgent", "Mozilla/5.0 (Linux; Android 4.2.1; en-us; Nexus 5 Build/JOP40D) AppleWebKit/535.19 (KHTML, like Gecko) Chrome/18.0.1025.166 Mobile Safari/535.19");
//        ChromeOptions chromeOptions = new ChromeOptions();
//        chromeOptions.setExperimentalOption("mobileEmulation", mobileEmulation);
//        WebDriver driver = new ChromeDriver(chromeOptions);
        ChromeOptions options = new ChromeOptions();

        //BrowserMobProxy proxy;
        //
        //options.setExperimentalOption("mobileEmulation", Map.of("deviceName", "Nexus 5"));
        //Mozilla/5.0 (Linux; Android 12; sdk_gphone64_x86_64 Build/SE1A.211012.001; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/91.0.4472.114 Mobile Safari/537.36
        //String userAgent = "user-agent=Mozilla/5.0 (Linux; Android 12; sdk_gphone64_x86_64 Build/SE1A.211012.001; wv) AppleWebKit/537.36 (KHTML, like Gecko) Version/4.0 Chrome/91.0.4472.114 Mobile Safari/537.36";
        //options.addArguments(userAgent);
        //options.setUseRunningAndroidApp(true);
        if (state.isHeadless()) {
            options.setHeadless(true);
        }
        WebDriver webDriver = new ChromeDriver(options);
        webDriver.manage().window().setSize(new Dimension(1600, 900));
        webDriver.manage().window().setPosition((new Point(-5, 0)));

        return webDriver;
    }

    private WebDriverWait wait(int beforeMillis) {
        return new WebDriverWait(driver, Duration.ofMillis(beforeMillis));
    }
    
    private void saveDataToFile(String pathname, String text) {
        try ( FileOutputStream fos = new FileOutputStream(pathname);
                DataOutputStream outStream = new DataOutputStream(new BufferedOutputStream(fos))) {
            outStream.writeUTF(text);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
    
    private void closePopup(int timeToWait) {
        try {
            while (!wait(timeToWait).until(presenceOfElementLocated(By.xpath(BASE_POPUP_DIV))).isDisplayed()) {
                
                driver.findElement(By.xpath(BASE_POPUP_CLOSE_BUTTON)).click();
                
//                if (!driver.findElements(By.xpath(locator.getQuestionFooter())).isEmpty()) {
//                    driver.findElement(By.xpath(locator.getFooterCancelButton())).click();
//                } else {
//                    driver.findElement(By.xpath(locator.getPopupCloseButton())).click();
//                }
                
            }
        } catch (Exception e) {
            //logger.error("close popup erro: {}", e.getMessage());
        }
    }

    @Override
    public void openURL() {
        try {
            driver = createWebDriver();
            driver.get(Locale.getLocaleURL(state.getLocale()));
            wait(20_000).until(visibilityOfElementLocated(By.xpath(Locator.BASE_COOKIES_CLOSE_BTN))).click();
            driver.findElement(By.xpath(Locator.BASE_HAVE_ACCOUNT_BTN)).isDisplayed();
        } catch (Exception e) {
            logger.error("OPEN URL: {}", e.getMessage());
            informObservers("ERROR: " + e.getMessage());
        }
    }

    @Override
    public void login() {
        //for (int i = 0; i < 10; i++) {
            try {
                driver.findElement(By.xpath(BASE_HAVE_ACCOUNT_BTN)).click();
                driver.findElement(By.xpath(BASE_RECOVERY_CODE_FIELD)).sendKeys(state.getUser().getCode());
                driver.findElement(By.xpath(BASE_FOOTER_ACCEPT_BUTTON)).click();
                TimeUnit.SECONDS.sleep(2);
                if (driver.findElement(By.xpath(BASE_AUTH_USER_CONTENT)).isDisplayed()) {
                    informObservers("logged in successfully");
                }
                closePopup(2_500);
                
            } catch (Exception e) {
                logger.error("LOGIN: {}", e.getMessage());
                informObservers("LOGIN ERROR");
                saveDataToFile("logs/1.log", Arrays.toString(e.getStackTrace()));
            }
        //}

//        if (driver == null) {
//            return;
//        }
//        for (int i = 1; i < 4; i++) {
//            try {
//                driver.findElement(By.xpath(locator.getHaveAccountButton())).click();
//                driver.findElement(By.xpath(locator.getRecoveryCodeField())).sendKeys(recoveryCode);
//                driver.findElement(By.xpath(locator.getSendCodeButton())).click();
//                closePopup(2_000);
//                if (isAuthorized()) {
//                    messageNotify("login success");
//                    break;
//                }
//            } catch (Exception e) {
//                takeScreenshot(getFileNameTimeStamp() + "_login.png");
//                messageNotify("LOGIN ERROR TRY " + i);
//                logger.error("LOGIN ERROR: {}", e.getMessage());
//                if (i == 3) {
//                    throw new LoginErrorException(e.getMessage());
//                }
//            }
//        }
    }

    @Override
    public void openGames() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void selectTriviaGame() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void selectRidesGame() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void terminate() {
        if (driver != null) {
            driver.quit();
        }
    }

}
