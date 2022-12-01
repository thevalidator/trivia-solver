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
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Random;
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
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.frameToBeAvailableAndSwitchToIt;
import static org.openqa.selenium.support.ui.ExpectedConditions.not;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.textToBe;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfAllElementsLocatedBy;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.thevalidator.galaxytriviasolver.communication.Informer;
import ru.thevalidator.galaxytriviasolver.exception.CanNotPlayException;
import ru.thevalidator.galaxytriviasolver.exception.LoginErrorException;
import ru.thevalidator.galaxytriviasolver.module.base.GalaxyBaseRobot;
import ru.thevalidator.galaxytriviasolver.module.trivia.GameResult;
import ru.thevalidator.galaxytriviasolver.module.trivia.State;
import ru.thevalidator.galaxytriviasolver.module.trivia.Unlim;
import ru.thevalidator.galaxytriviasolver.module.trivia.solver.Solver;
import ru.thevalidator.galaxytriviasolver.module.trivia.solver.entity.Answer;
import ru.thevalidator.galaxytriviasolver.module.trivia.solver.entity.Question;
import ru.thevalidator.galaxytriviasolver.module.trivia.solver.impl.SolverImpl;
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
    private static Random random = new Random();

    private final State state;
    private WebDriver driver;
    private TriviaUserStats userStats;
    private final Solver solver;

    public GalaxyBaseRobotImpl(State state) {
        this.state = state;
        this.userStats = new TriviaUserStats();
        this.solver = new SolverImpl();
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
        webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(10));

        return webDriver;
    }

    private WebDriverWait wait(int beforeMillis) {
        return new WebDriverWait(driver, Duration.ofMillis(beforeMillis));
    }

    public void takeScreenshot(String pathname) {
        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(src, new File(pathname));
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void saveDataToFile(String pathname, Exception exception) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        exception.printStackTrace(pw);
        try ( FileOutputStream fos = new FileOutputStream(pathname);  DataOutputStream outStream = new DataOutputStream(new BufferedOutputStream(fos))) {
            outStream.writeUTF(sw.toString());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public String getFileNameTimeStamp() {
        String path = "logs" + File.separator;
        return path + LocalDateTime.now().format(formatter);
    }

    private void closePopup(int timeToWait) {
        try {
            while (!wait(timeToWait).until(visibilityOfAllElementsLocatedBy(By.xpath("//div[@data-component-name='dialog__html']//iframe"))).isEmpty()) {
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
                if (wait(30_000).until(presenceOfElementLocated(By.xpath(getBaseAuthUserContent()))).isDisplayed()) {
                    informObservers("logged in successfully");
                    break;
                }
            } catch (Exception e) {
                String fileName = getFileNameTimeStamp() + "_login";
                takeScreenshot(fileName + ".png");
                saveDataToFile(fileName + ".log", e);
                if (i == 5) {
                    informObservers("LOGIN ERROR: couldn't log in 5 times in a row, task stopped");
                    throw new LoginErrorException(e.getMessage());
                } else {
                    logger.error(e.getMessage());   //need to be deleted
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
        wait(15_000).until(frameToBeAvailableAndSwitchToIt(By.xpath(Locator.getBaseContentIframe())));
        wait(15_000).until(visibilityOfElementLocated(By.xpath(Locator.getGamesTriviaBtn(state.getLocale())))).click();
        informObservers("opening Trivia");
        updateTriviaUsersData();
    }

    @Override
    public void selectRidesGame() {
        closePopup(2_500);
        wait(15_000).until(frameToBeAvailableAndSwitchToIt(By.xpath(Locator.getBaseContentIframe())));
        wait(15_000).until(visibilityOfElementLocated(By.xpath(Locator.getGamesRidesBtn(state.getLocale())))).click();
        informObservers("opening Rides");
    }

    private void updateTriviaUsersData() {
        closePopup(2_500);
        String userBalance = wait(10_000).until(visibilityOfElementLocated(By.xpath(Locator.getBaseUserBalance()))).getText();
        wait(15_000).until(frameToBeAvailableAndSwitchToIt(By.xpath(Locator.getBaseContentIframe())));
        String dailyPoints = wait(10_000).until(visibilityOfElementLocated(By.xpath(Locator.getTriviaOwnDailyResult()))).getText();
        //informObservers("balance: " + userBalance + " daily points: " + dailyPoints);

        driver.findElement(By.xpath(Locator.getTriviaDailyRatingsPageBtn())).click();
        closePopup(2_500);
        wait(15_000).until(frameToBeAvailableAndSwitchToIt(By.xpath(Locator.getBaseContentIframe())));
        String first = wait(10_000).until(visibilityOfElementLocated(By.xpath(Locator.getTriviaPositionDailyResult(1)))).getText();
        String tenth = wait(10_000).until(visibilityOfElementLocated(By.xpath(Locator.getTriviaPositionDailyResult(10)))).getText();
        informObservers("first: " + first + " tenth: " + tenth);
        driver.switchTo().defaultContent();
        driver.findElement(By.xpath(Locator.getBaseBackBtn())).click();

        userStats.setFirstPlacePoints(Integer.parseInt(first));
        userStats.setTenthPlacePoints(Integer.parseInt(tenth));
        userStats.setUserCoins(Double.parseDouble(userBalance));
        userStats.setUserPoints(Integer.parseInt(dailyPoints));

        informObservers("balance: " + userBalance + " daily points: " + dailyPoints + " unlim available: " + userStats.isUnlimAvailable(Unlim.MIN, 1));
    }

    @Override
    public void startTriviaGame() throws CanNotPlayException {
        closePopup(1_500);
        wait(15_000).until(frameToBeAvailableAndSwitchToIt(By.xpath(Locator.getBaseContentIframe())));
        String attempts = wait(10_000).until(visibilityOfElementLocated(By.xpath(Locator.getTriviaEnergyCount()))).getText();
        if (!attempts.equals("0")) {
            WebElement anonSwitcher = driver.findElement(By.xpath(Locator.getTriviaAnonymousToggle()));//wait(15_000).until(presenceOfElementLocated(By.xpath(Locator.getTriviaAnonymousToggle())));
            switchAnonToggle(anonSwitcher);
            driver.findElement(By.xpath(Locator.getTriviaStartBtn(state.getLocale()))).click();
            driver.switchTo().defaultContent();
            closePopup(1_500);
            wait(15_000).until(frameToBeAvailableAndSwitchToIt(By.xpath(Locator.getBaseContentIframe())));
            List<WebElement> topics = wait(10_000).until(visibilityOfAllElementsLocatedBy(By.xpath(Locator.getTriviaTopicDiv())));
            String selectedTopic = state.getLocale().getTopics()[state.getTopicIndex()];
            WebElement topic;
            if (state.getTopicIndex() != 0) {
                topic = getTopic(selectedTopic, topics);
            } else {
                int randomIndex = random.nextInt(topics.size());
                topic = topics.get(randomIndex);
            }
            topic.click();

        }
    }

    private WebElement getTopic(String topic, List<WebElement> topics) throws CanNotPlayException {
        for (WebElement t : topics) {
            if (t.getText().equalsIgnoreCase(topic)) {
                return t;
            }
        }
        throw new CanNotPlayException("no selected topic found");
    }

    private void switchAnonToggle(WebElement anonSwitcher) {
        if (state.isAnonymous() && !anonSwitcher.getAttribute("class").contains("checked")) {
            anonSwitcher.click();
        } else if (!state.isAnonymous() && anonSwitcher.getAttribute("class").contains("checked")) {
            anonSwitcher.click();
        }
    }

    @Override
    public void playTriviaGame() {
        while (true) {
            /*boolean isStarted = */wait(50_000).until(visibilityOfElementLocated(By.xpath(Locator.getTriviaGameProcessFrame()))).isDisplayed();
            informObservers("game started");
            answerQuestions();
            /*boolean isFinished = */wait(30_000).until(visibilityOfElementLocated(By.xpath(Locator.getTriviaGameResultsFrame()))).isDisplayed();
            informObservers("game finished");

            driver.switchTo().frame(driver.findElement(By.xpath(Locator.getTriviaGameResultsFrame())));

            String attempts = driver.findElement(By.xpath(Locator.getTriviaEnergyCount())).getText().trim();
            String points = driver.findElement(By.xpath(Locator.getTriviaResultPoints())).getText().trim();
            GameResult result = getTriviaRoundResult();

            //String attr = driver.findElement(By.xpath(Locator.getTriviaResultDiv())).getAttribute("class");
            //informObservers("att: " + attempts + " pts: " + points + " attr: " + attr);
            gameResultNotifyObservers(result, Integer.parseInt(points));
            
            if (attempts.equals("0")) {
                informObservers("not enough energy");
                break;
            } else if (attempts.isEmpty()) {
                String unlimLeftTime = driver.findElement(By.xpath(Locator.getTriviaEnergyTimer())).getText();
                informObservers("unlim is active (" + unlimLeftTime + " letf)");
            } else {
                informObservers("games left: " + attempts);
            }
            startAgainTriviaGame();
        }

    }

    private void answerQuestions() {
        String questionNumber = null;
        for (int i = 0; i < 5; i++) {
            driver.switchTo().defaultContent();
            closePopup(1_000);
            driver.switchTo().frame(driver.findElement(By.xpath(Locator.getTriviaGameProcessFrame())));//wait(15_000).until(frameToBeAvailableAndSwitchToIt(By.xpath(Locator.getBaseContentIframe())));
            //WebElement questionNumberHeader = wait(3_000).until(presenceOfElementLocated(By.xpath(Locator.getTriviaQuestionHeader())));
            questionNumber = wait(3_000).until(presenceOfElementLocated(By.xpath(Locator.getTriviaQuestionHeader()))).getText();
            try {
                if (i != 0 && i != 4) {
                    TimeUnit.SECONDS.sleep(random.nextInt(10) + 2);
                } else {
                    TimeUnit.SECONDS.sleep(1);
                }
            } catch (InterruptedException e) {
            }
            clickCorrectAnswer();
            wait(25_000).until(not(textToBe(By.xpath(Locator.getTriviaQuestionHeader()), questionNumber)));
        }
    }

    private void clickCorrectAnswer() {
        List<WebElement> elements = driver.findElements(By.xpath(Locator.getTriviaQuestionAnswer()));
        Answer[] answers = new Answer[4];
        int index = 0;
        for (WebElement e : elements) {
            String text = e.getText();
            String rel = e.getAttribute("rel");
            Answer answer = new Answer(text, rel, index);
            answers[index++] = answer;
        }
        Question question = new Question("q", answers);
        Answer correctAnswer = solver.getCorrectAnswer(question);
        elements.get(correctAnswer.getIndex()).click();
    }

    private GameResult getTriviaRoundResult() {
        //closePopup(2_000);
        String result = driver.findElement(By.xpath(Locator.getTriviaResultHeader())).getText().trim();
        if (result.contains(Locale.getWinText(state.getLocale()))) {
            return GameResult.WIN;
        } else if (result.contains(Locale.getDrawText(state.getLocale()))) {
            return GameResult.DRAW;
        } else {
            return GameResult.LOST;
        }
    }

    private void startAgainTriviaGame() {
        driver.switchTo().defaultContent();
        closePopup(2_000);
        wait(5_000).until(frameToBeAvailableAndSwitchToIt(By.xpath(Locator.getTriviaGameResultsFrame())));
        driver.findElement(By.xpath(Locator.getTriviaPlayAgainBtn(state.getLocale()))).click();
    }

    @Override
    public int getSleepTime() {
        String statusMessage = driver.findElement(By.xpath(Locator.getTriviaEnergyTimer())).getText();
        int timeInSeconds = Integer.parseInt(statusMessage.substring(0, statusMessage.indexOf(" ")));
        if (statusMessage.contains("мин") || statusMessage.contains("min")) {
            timeInSeconds = timeInSeconds * 60;
        }

        return timeInSeconds;
    }

    @Override
    public void terminate() {
        if (driver != null) {
            driver.quit();
        }
    }

}
