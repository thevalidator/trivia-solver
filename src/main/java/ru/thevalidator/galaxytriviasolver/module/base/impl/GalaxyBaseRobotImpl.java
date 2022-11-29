/*
 * Copyright (C) 2022 thevalidator
 */
package ru.thevalidator.galaxytriviasolver.module.base.impl;

import io.github.bonigarcia.wdm.WebDriverManager;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.thevalidator.galaxytriviasolver.communication.Informer;
import ru.thevalidator.galaxytriviasolver.exception.LoginErrorException;
import ru.thevalidator.galaxytriviasolver.module.base.GalaxyBaseRobot;
import ru.thevalidator.galaxytriviasolver.module.trivia.State;
import ru.thevalidator.galaxytriviasolver.module.trivia.Unlim;
import ru.thevalidator.galaxytriviasolver.web.Locale;
import ru.thevalidator.galaxytriviasolver.web.Locator;
import static ru.thevalidator.galaxytriviasolver.web.Locator.*;

public class GalaxyBaseRobotImpl extends Informer implements GalaxyBaseRobot {
    
    class TriviaUserStats {
        
        private int userPoints;
        private double userCoins;
        private int tenthPlacePoints;
        private int firstPlacePoints;
        

        public TriviaUserStats() {
            this.userCoins = 0;
            this.userPoints = 0;
            this.tenthPlacePoints = 0;
            this.firstPlacePoints = 0;
        }
        
        public boolean isUnlimAvailable(Unlim type, int times) {
            return (userCoins - (type.getPrice() * times)) > 0;
        }

        public int getUserPoints() {
            return userPoints;
        }

        public void setUserPoints(int userPoints) {
            this.userPoints = userPoints;
        }

        public double getUserCoins() {
            return userCoins;
        }

        public void setUserCoins(double userCoins) {
            this.userCoins = userCoins;
        }

        public int getTenthPlacePoints() {
            return tenthPlacePoints;
        }

        public void setTenthPlacePoints(int tenthPlacePoints) {
            this.tenthPlacePoints = tenthPlacePoints;
        }

        public int getFirstPlacePoints() {
            return firstPlacePoints;
        }

        public void setFirstPlacePoints(int firstPlacePoints) {
            this.firstPlacePoints = firstPlacePoints;
        }
        
    }

    private static final Logger logger = LogManager.getLogger(GalaxyBaseRobotImpl.class);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy_HHmmss");

    private final State state;
    private WebDriver driver;
    private TriviaUserStats userStats;

    public GalaxyBaseRobotImpl(State state) {
        this.state = state;
        this.userStats = new TriviaUserStats();
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
        webDriver.manage().window().setSize(new Dimension(1600, 845));
        webDriver.manage().window().setPosition((new Point(-5, 0)));

        return webDriver;
    }

    private WebDriverWait wait(int beforeMillis) {
        return new WebDriverWait(driver, Duration.ofMillis(beforeMillis));
    }

    private void takeScreenshot(String pathname) {
        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(src, new File(pathname));
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void saveDataToFile(String pathname, String text) {
        try ( FileOutputStream fos = new FileOutputStream(pathname);  DataOutputStream outStream = new DataOutputStream(new BufferedOutputStream(fos))) {
            outStream.writeUTF(text);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private String getFileNameTimeStamp() {
        String path = "logs" + File.separator;
        return path + LocalDateTime.now().format(formatter);
    }

    private void closePopup(int timeToWait) {
        try {
            while (!wait(timeToWait).until(presenceOfElementLocated(By.xpath(getBasePopupDiv()))).isDisplayed()) {
                if (!driver.findElements(By.xpath(getBasePopupCloseBtn())).isEmpty()) {
                    driver.findElement(By.xpath(getBasePopupCloseBtn())).click();
                } else {
                    driver.findElement(By.xpath(getBaseFooterCancelBtn())).click();
                }
            }
        } catch (Exception e) {
            //no popup found, do nothing
        }
    }

    private void openURL() {
        driver = createWebDriver();
        driver.get(Locale.getLocaleURL(state.getLocale()));
        wait(20_000).until(visibilityOfElementLocated(By.xpath(Locator.getBaseCookiesCloseBtn()))).click();
        driver.findElement(By.xpath(Locator.getBaseHaveAccountBtn())).isDisplayed();
    }

    @Override
    public void login() throws LoginErrorException {
        for (int i = 1; i < 6; i++) {
            try {
                openURL();
                driver.findElement(By.xpath(getBaseHaveAccountBtn())).click();
                driver.findElement(By.xpath(getBaseRecoveryCodeField())).sendKeys(state.getUser().getCode());
                driver.findElement(By.xpath(getBaseFooterAcceptBtn())).click();
                TimeUnit.SECONDS.sleep(2);
                if (wait(6_000).until(presenceOfElementLocated(By.xpath(getBaseAuthUserContent()))).isDisplayed()) {
                    informObservers("logged in successfully");
                    break;
                }
            } catch (Exception e) {
                String fileName = getFileNameTimeStamp() + "_login";
                takeScreenshot(fileName + ".png");
                saveDataToFile(fileName + ".log", Arrays.toString(e.getStackTrace()));
                if (i == 5) {
                    informObservers("LOGIN ERROR: couldn't log in 5 times in a row, task stopped");
                    throw new LoginErrorException(e.getMessage());
                } else {
                    int timeToWait = (2 * i) + ((i - 1) * 5);
                    informObservers("LOGIN ERROR: try " + i + " was unsuccessfull, next try in " + timeToWait);
                    driver.quit();
                    try {
                        TimeUnit.MINUTES.sleep(timeToWait);
                    } catch (InterruptedException ex) {
                    }
                }
            }
        }

    }

    @Override
    public void openGames() {
        closePopup(2_500);
        wait(15_000).until(elementToBeClickable(By.xpath(getBaseMenuGamesBtn(state.getLocale())))).click();
    }

    @Override
    public void selectTriviaGame() {
        closePopup(2_500);
        wait(15_000).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.xpath(Locator.getBaseContentIframe())));
        wait(15_000).until(visibilityOfElementLocated(By.xpath(Locator.getGamesTriviaBtn(state.getLocale())))).click();
        informObservers("opening Trivia");
        updateTriviaUsersData();
    }

    @Override
    public void selectRidesGame() {
        closePopup(2_500);
        wait(15_000).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.xpath(Locator.getBaseContentIframe())));
        wait(15_000).until(visibilityOfElementLocated(By.xpath(Locator.getGamesRidesBtn(state.getLocale())))).click();
        informObservers("opening Rides");
    }
    
    private void updateTriviaUsersData() {
        closePopup(2_500);
        String userBalance = wait(10_000).until(visibilityOfElementLocated(By.xpath(Locator.getBaseUserBalance()))).getText();
        wait(15_000).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.xpath(Locator.getBaseContentIframe())));
        String dailyPoints = wait(10_000).until(visibilityOfElementLocated(By.xpath(Locator.getTriviaOwnDailyResult()))).getText();
        informObservers("balance: " + userBalance + " daily points: " + dailyPoints);
        
        driver.findElement(By.xpath(Locator.getTriviaDailyRatingsPageBtn())).click();
        closePopup(2_500);
        wait(15_000).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.xpath(Locator.getBaseContentIframe())));
        String first = wait(10_000).until(visibilityOfElementLocated(By.xpath(Locator.getTriviaPositionDailyResult(1)))).getText();
        String tenth = wait(10_000).until(visibilityOfElementLocated(By.xpath(Locator.getTriviaPositionDailyResult(10)))).getText();
        informObservers("first: " + first + " tenth: " + tenth);
        driver.switchTo().defaultContent();
        driver.findElement(By.xpath(Locator.getBaseBackBtn())).click();
        
        userStats.setFirstPlacePoints(Integer.parseInt(first));
        userStats.setTenthPlacePoints(Integer.parseInt(tenth));
        userStats.setUserCoins(Double.parseDouble(userBalance));
        userStats.setUserPoints(Integer.parseInt(dailyPoints));
        
        informObservers("unlim available: " + userStats.isUnlimAvailable(Unlim.MIN, 1));
    }

    @Override
    public void terminate() {
        if (driver != null) {
            driver.quit();
        }
    }

}
