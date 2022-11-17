/*
 * Copyright (C) 2022 thevalidator
 */
//TODO: 
/*
    1) answers on questions in cycle
    2) 
 */
package ru.thevalidator.galaxytriviasolver.bot.impl;

import io.github.bonigarcia.wdm.WebDriverManager;
import java.awt.Color;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.frameToBeAvailableAndSwitchToIt;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.textToBe;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfAllElementsLocatedBy;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.thevalidator.galaxytriviasolver.bot.Bot;
import ru.thevalidator.galaxytriviasolver.bot.GameResult;
import ru.thevalidator.galaxytriviasolver.entity.Answer;
import ru.thevalidator.galaxytriviasolver.entity.Question;
import ru.thevalidator.galaxytriviasolver.exception.BrokenAccountException;
import ru.thevalidator.galaxytriviasolver.exception.CanNotPlayException;
import ru.thevalidator.galaxytriviasolver.exception.LoginErrorException;
import ru.thevalidator.galaxytriviasolver.exception.NotEnoughEnergyException;
import ru.thevalidator.galaxytriviasolver.model.Observer;
import ru.thevalidator.galaxytriviasolver.solver.Solver;
import ru.thevalidator.galaxytriviasolver.solver.SolverImpl;
import ru.thevalidator.galaxytriviasolver.web.AbstractLocator;
import ru.thevalidator.galaxytriviasolver.web.AbstractTopic;

public class BotImpl implements Bot {

    private static final Logger logger = LogManager.getLogger(BotImpl.class);

    private volatile boolean isHeadless;
    private volatile boolean isAnonymous;
    private volatile boolean isActive;
    private volatile int unlimOption;
    private WebDriver driver;
    private String code;
    private final List<Observer> observers;
    private final Solver solver;
    private AbstractLocator locator;
    private String topic;
    private AbstractTopic topicsList;

    public BotImpl() {
        this.isActive = false;
        this.isHeadless = true;
        this.isAnonymous = true;
        this.unlimOption = 0;
        this.observers = new ArrayList<>();
        this.solver = new SolverImpl();
        //default locator
        //default topics
    }

    @Override
    public void registerObserver(Observer o) {
        //synchronized (observers) {
        observers.add(o);
        logger.debug("add listeners");
        //}
    }

    @Override
    public void unregisterObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void startNotify() {
        for (Observer observer : observers) {
            observer.handleStart();
        }
    }

    @Override
    public void stopNotify() {
        for (Observer observer : observers) {
            observer.handleStop();
        }
    }

    @Override
    public void shoppingNotify() {
        for (Observer observer : observers) {
            observer.handleUnlimShopping();
        }
    }

    @Override
    public void messageNotify(String message, Color color) {
        for (Observer observer : observers) {
            observer.handleMessage(message, color);
        }
    }

    @Override
    public void gameResultNotify(GameResult result, int points) {
        for (Observer observer : observers) {
            observer.handleGameResult(result, points);
        }
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public void addListener(Observer o) {
        registerObserver(o);
    }

    @Override
    public void removeListeners() {
        synchronized (observers) {
            observers.clear();
        }
        //observers.clear();
    }

    @Override
    public boolean isActive() {
        return isActive;
    }

    @Override
    public void isHeadleass(boolean b) {
        this.isHeadless = b;
    }

    @Override
    public void isAnonimous(boolean b) {
        this.isAnonymous = b;
    }

    @Override
    public void setRecoveryCode(String code) {
        this.code = code;
    }

    @Override
    public void setTopic(String text) {
        topic = text;
    }

    public void login(String recoveryCode) throws LoginErrorException {
        try {
            openPage();
            driver.findElement(By.xpath(locator.getHaveAccountButton())).click();
            driver.findElement(By.xpath(locator.getRecoveryCodeField())).sendKeys(recoveryCode);
            driver.findElement(By.xpath(locator.getSendCodeButton())).click();
            if (isAuthorized()) {
                messageNotify("login success", Color.lightGray);
            }
        } catch (Exception e) {
            takeScreenshot(getFileNameTimeStamp() + "_login.png");
            messageNotify("LOGIN ERROR", Color.yellow);
            logger.error("LOGIN ERROR: {}", e.getMessage());
            throw new LoginErrorException(e.getMessage());
        }
    }

    public void openTriviaGame() {
        for (int i = 0; i < 5; i++) {
            try {
                closePopup(3_000);
                wait(15_000).until(elementToBeClickable(By.xpath(locator.getGamesMenuItem()))).click();
                closePopup(3_000);
                wait(15_000).until(frameToBeAvailableAndSwitchToIt(By.xpath(locator.getPlanetsIFrame())));
                closePopup(3_000);
                wait(15_000).until(elementToBeClickable(By.xpath(locator.getTriviaGameButton()))).click();
                break;
            } catch (Exception e) {
                takeScreenshot(getFileNameTimeStamp() + ".png");
                refreshPage();
            }
        }
    }

    public void play(String topic) throws CanNotPlayException, NotEnoughEnergyException {

        List<WebElement> topics = startGame();

        if (selectTopic(topics)) {
            while (true) {
                try {
                    waitForOpponent();
                    answerQuestions();
                    waitForResults();
                    handleResults();
                    //checkAttempts();

                    String attempts = driver.findElement(By.xpath(locator.getTriviaEnergyCountCard())).getText().trim();
                    driver.switchTo().defaultContent();
                    closePopup(2_000);
                    wait(2_000).until(frameToBeAvailableAndSwitchToIt(By.xpath(locator.getPlanetsIFrame())));
                    if (attempts.contains("0")) {

                        if (unlimOption > 0 && unlimOption < 4) {
                            buyUnlimStatus();
                            try {
                                TimeUnit.SECONDS.sleep(3);
                            } catch (InterruptedException ex) {
                                logger.error(ex.getMessage());
                            }
                            continuePlaying();
                            continue;
                        } else {
                            String statusMessage = driver.findElement(By.xpath(locator.getTriviaEnergyTimer())).getText();
                            messageNotify("no attempts left, need to wait " + statusMessage, Color.lightGray);
                            int timeToSleep = Integer.parseInt(statusMessage.substring(0, statusMessage.indexOf(" ")));
                            if (statusMessage.contains(locator.getTriviaMinutesText())) {
                                timeToSleep = (timeToSleep + 2) * 60;
                            } else {
                                timeToSleep += 60;
                            }
                            checkDailyRatings();
                            throw new NotEnoughEnergyException(timeToSleep, statusMessage);
                        }

                    } else if (!attempts.isEmpty()) {
                        messageNotify("attempts left: " + attempts, Color.lightGray);
                    }
                    driver.findElement(By.xpath(locator.getTriviaPlayAgainButton())).click();
                } catch (NotEnoughEnergyException e) {
                    throw e;
                } catch (Exception e) {
                    gameResultNotify(GameResult.LOST, 0);
                    //messageNotify("ERROR", Color.red);
                    logger.error(e.getMessage());
                    String pageSource = driver.getPageSource();
                    String fileName = getFileNameTimeStamp() + "_playing_game";
                    takeScreenshot(fileName + ".png");
                    saveDataToFile(fileName + ".html", pageSource);
                    saveDataToFile(fileName + ".log", Arrays.toString(e.getStackTrace()));
                    throw new CanNotPlayException(e.getMessage());
                }
            }
        } else {
            String fileName = getFileNameTimeStamp() + "_play_game";
            takeScreenshot(fileName + ".png");
            saveDataToFile(fileName + ".html", driver.getPageSource());
            throw new CanNotPlayException("Can't select topic");    // CanNotStartGame
        }

    }
//probably error with the first question happens here
    private void clickCorrectAnswer() {
        List<WebElement> elements = driver.findElements(By.xpath(locator.getTriviaQuestionID()));
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

    @Override
    public void setLocale(AbstractLocator locator, AbstractTopic topics) {
        this.locator = locator;
        this.topicsList = topics;
    }

    @Override
    public void stop() {
        messageNotify("stopping....", Color.lightGray);
        setIsActive(false);
    }

    @Override
    public void buyUnlim(int option) {
        this.unlimOption = option;
    }

    @Override
    public void run() {
        try {
            startNotify();
            driver = createDriver();
            setIsActive(true);
            login(code);
            openTriviaGame();
            while (isActive) {
                play(topic);
            }
            removeListeners();
            terminateSession();
        } catch (Exception e) {
            int sleepTime = 145;
            if (e instanceof NotEnoughEnergyException) {
                sleepTime = ((NotEnoughEnergyException) e).getSecondsToWait();
                //messageNotify("not enough energy, waiting " + sleepTime + " secs", Color.lightGray);
            } else {
                messageNotify("ERROR... restarting in " + sleepTime + " secs", Color.red);
                logger.error(e.getMessage());
            }
            stopNotify();
            terminateSession();
            try {
                TimeUnit.SECONDS.sleep(sleepTime);
            } catch (InterruptedException ex) {
                logger.error(ex.getMessage());
            }
            run();
        } finally {
//            if (isActive) {
//                isActive = false;
//            }
            //stopNotify();
            //removeListeners();
            //terminateSession();
        }
    }

    private WebDriver createDriver() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        if (isHeadless) {
            options.setHeadless(true);
        }
        WebDriver drv = new ChromeDriver(options);
        drv.manage().window().setSize(new Dimension(1600, 900));
        drv.manage().window().setPosition((new Point(0, 0)));
        return drv;
    }

    private WebDriverWait wait(int beforeMillis) {
        return new WebDriverWait(driver, Duration.ofMillis(beforeMillis));
    }

    private void refreshPage() {
        driver.navigate().refresh();
    }

    public void terminateSession() {
        driver.quit();
    }

    private void openPage() {
        driver.get(locator.getStartPageLink());
        try {
            wait(15_000).until(visibilityOfElementLocated(By.xpath(locator.getCookiesCloseButton()))).click();
        } catch (Exception e) {
            logger.error("cookies not found");
            messageNotify("cookies not found", Color.yellow);
        }
    }

    private boolean isAuthorized() throws LoginErrorException, BrokenAccountException {
        try {
            TimeUnit.SECONDS.sleep(2);
            checkPopup();
            driver.findElement(By.xpath(locator.getAuthUserDiv()));
            return true;
        } catch (Exception e) {
            logger.error("IS AUTHORIZED METHOD: {}", e.getMessage());
            if (e instanceof BrokenAccountException) {
                throw new BrokenAccountException(e.getMessage());
            } else {
                throw new LoginErrorException("Can't find authorized block: " + e.getMessage());
            }
        }
    }

    private void checkPopup() throws BrokenAccountException {
        List<WebElement> popupList = driver.findElements(By.xpath(locator.getPopupIFrame()));
        while (!popupList.isEmpty()) {
            driver.switchTo().frame(driver.findElement(By.xpath(locator.getPopupIFrame())));
            String text = driver.findElement(By.xpath(locator.getContentDiv())).getText();
            driver.switchTo().defaultContent();

            if (text.contains(locator.getJailMessageText())) {
                messageNotify("IN JAIL", Color.red);
                throw new BrokenAccountException("GOT IN JAIL");
            } else if (text.contains(locator.getKickedMessageText())) {
                messageNotify("KICKED OUT FROM THE PLANET", Color.yellow);
                driver.findElement(By.xpath(locator.getPopupCloseButton())).click();
                wait(10_000).until(frameToBeAvailableAndSwitchToIt(By.xpath(locator.getPlanetsIFrame())));
                List<WebElement> planetLinks = wait(1_000).until(visibilityOfAllElementsLocatedBy(By.xpath(locator.getAvailablePlanetLink())));
                for (int index = 0; index < planetLinks.size(); index++) {
                    WebElement planet = planetLinks.get(index);
                    String planetName = planet.findElement(By.xpath(".//b")).getText();
                    planet.click();
                    driver.switchTo().defaultContent();
                    if (checkPlanet(planetName)) {
                        break;
                    } else {
                        driver.navigate().refresh();
                        wait(10_000).until(frameToBeAvailableAndSwitchToIt(By.xpath(locator.getPlanetsIFrame())));
                    }
                }
            } else if (text.contains(locator.getDisconnectedMessageText())) {
                messageNotify("KICKED OUT FROM SERVER", Color.red);
                throw new BrokenAccountException("KICKED OUT FROM SERVER");
            } else {
                if (!driver.findElements(By.xpath(locator.getQuestionFooter())).isEmpty()) {
                    driver.findElement(By.xpath(locator.getFooterCancelButton())).click();
                } else {
                    driver.findElement(By.xpath(locator.getPopupCloseButton())).click();
                }
            }
            popupList = driver.findElements(By.xpath(locator.getPopupIFrame()));
        }
    }

    private void closePopup(int timeToWait) {
        try {
            while (!wait(timeToWait).until(ExpectedConditions
                    .visibilityOfAllElementsLocatedBy(By.xpath(locator.getPopupIFrame()))).isEmpty()) {
                if (!driver.findElements(By.xpath(locator.getQuestionFooter())).isEmpty()) {
                    driver.findElement(By.xpath(locator.getFooterCancelButton())).click();
                } else {
                    driver.findElement(By.xpath(locator.getPopupCloseButton())).click();
                }
            }
        } catch (Exception e) {
            //logger.error("close popup erro: {}", e.getMessage());
        }
    }

    private boolean checkPlanet(String planetName) {
        boolean r = false;
        try {
            r = wait(10_000).until(textToBe(By.xpath(locator.getActivePlanetName()), planetName));
        } catch (Exception e) {
            messageNotify("CAN'T FLY TO PLANET", Color.red);
            logger.error(e.getMessage());
        } finally {
            return r;
        }
    }

    public void takeScreenshot(String pathname) {
        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            FileUtils.copyFile(src, new File(pathname));
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    public void saveDataToFile(String fileName, String text) {
        try ( FileOutputStream fos = new FileOutputStream(fileName);  
              DataOutputStream outStream = new DataOutputStream(new BufferedOutputStream(fos))) {
            outStream.writeUTF(text);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    private void switchAnonToggle(WebElement anonSwitcher) {
        if (isAnonymous && !anonSwitcher.getAttribute("class").contains("checked")) {
            anonSwitcher.click();
        } else if (!isAnonymous && anonSwitcher.getAttribute("class").contains("checked")) {
            anonSwitcher.click();
        }
    }

    private void checkAvailableAttempts() throws NotEnoughEnergyException {
        String attempts = driver.findElement(By.xpath(locator.getTriviaEnergyCountCard())).getText().trim();
        if (attempts.contains("0")) {
            String statusMessage = driver.findElement(By.xpath(locator.getTriviaEnergyTimer())).getText();
            messageNotify("no attempts left, sleep time: " + statusMessage, Color.lightGray);
            int timeToSleep = Integer.parseInt(statusMessage.substring(0, statusMessage.indexOf(" ")));
            if (statusMessage.contains(locator.getTriviaMinutesText())) {
                timeToSleep = (timeToSleep + 2) * 60;
            } else {
                timeToSleep += 60;
            }
            throw new NotEnoughEnergyException(timeToSleep, statusMessage);
        }
    }

    private boolean selectTopic(List<WebElement> topics) {
        try {
            if (topic.equalsIgnoreCase(topicsList.getTopics()[0])) {
                int index = new Random().nextInt(topics.size());
                topics.get(index).click();
            } else {
                WebElement topicElement = null;
                for (WebElement t : topics) {
                    if (t.getText().equalsIgnoreCase(topic)) {
                        topicElement = t;
                        break;
                    }
                }
                if (topicElement != null) {
                    topicElement.click();
                } else {
                   return false;
                }
            }
            return true;
        } catch (Exception e) {
            logger.error(e.getMessage());
            String fileName = getFileNameTimeStamp() + "_select_topic";
            takeScreenshot(fileName + ".png");
            saveDataToFile(fileName + ".log", Arrays.toString(e.getStackTrace()));
            return false;
        }
    }

    private void answerQuestions() throws InterruptedException {
        //sometimes problems with Iframe, with this part of code everuthing works fine
        try {
            driver.switchTo().defaultContent();
            closePopup(1_000);
        } catch (Exception e) {
        }
        //
        wait(3_000).until(frameToBeAvailableAndSwitchToIt(By.xpath(locator.getPlanetsIFrame())));
        wait(2_000).until(presenceOfElementLocated(By.xpath(locator.getTriviaQuestionOneHeaderText())));
        TimeUnit.SECONDS.sleep(1);
        clickCorrectAnswer();

        driver.switchTo().defaultContent();
        closePopup(1_000);
        wait(2_000).until(frameToBeAvailableAndSwitchToIt(By.xpath(locator.getPlanetsIFrame())));
        wait(45_000).until(presenceOfElementLocated(By.xpath(locator.getTriviaQuestionTwoHeaderText())));
        TimeUnit.SECONDS.sleep(3);
        clickCorrectAnswer();

        driver.switchTo().defaultContent();
        closePopup(1_000);
        wait(2_000).until(frameToBeAvailableAndSwitchToIt(By.xpath(locator.getPlanetsIFrame())));
        wait(45_000).until(presenceOfElementLocated(By.xpath(locator.getTriviaQuestionThreeHeaderText())));
        TimeUnit.SECONDS.sleep(2);
        clickCorrectAnswer();

        driver.switchTo().defaultContent();
        closePopup(1_000);
        wait(2_000).until(frameToBeAvailableAndSwitchToIt(By.xpath(locator.getPlanetsIFrame())));
        wait(45_000).until(presenceOfElementLocated(By.xpath(locator.getTriviaQuestionFourHeaderText())));
        TimeUnit.SECONDS.sleep(4);
        clickCorrectAnswer();

        driver.switchTo().defaultContent();
        closePopup(1_000);
        wait(2_000).until(frameToBeAvailableAndSwitchToIt(By.xpath(locator.getPlanetsIFrame())));
        wait(45_000).until(presenceOfElementLocated(By.xpath(locator.getTriviaQuestionFiveHeaderText())));
        TimeUnit.SECONDS.sleep(1);
        clickCorrectAnswer();
    }

    private void waitForOpponent() {
        closePopup(1_500);
        wait(10_000).until(frameToBeAvailableAndSwitchToIt(By.xpath(locator.getPlanetsIFrame())));
        messageNotify("searching", Color.lightGray);
        try {
            wait(25_000).until(ExpectedConditions
                    .not(ExpectedConditions.visibilityOfElementLocated(By.xpath(locator.getTriviaQuizTitleResult()))));
            messageNotify("opponent found", Color.lightGray);
        } catch (StaleElementReferenceException ex) {
            String fileName = getFileNameTimeStamp();
            takeScreenshot(fileName + "_search.png");
            logger.error(ex.getMessage());
            //TODO: seems like not needed
        }
        closePopup(4_000);
    }

    private List<WebElement> startGame() throws NotEnoughEnergyException {
        closePopup(3_000);
        wait(15_000).until(frameToBeAvailableAndSwitchToIt(By.xpath(locator.getPlanetsIFrame())));
        WebElement anonSwitcher = wait(15_000).until(presenceOfElementLocated(By.xpath(locator.getTriviaAnonymousToggle())));
        switchAnonToggle(anonSwitcher);
        checkAvailableAttempts();
        wait(15_000).until(presenceOfElementLocated(By.xpath(locator.getTriviaStartButton()))).click();
        wait(15_000).until(frameToBeAvailableAndSwitchToIt(By.xpath(locator.getPlanetsIFrame())));

        return wait(10_000).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(locator.getTriviaTopic())));
    }

    private void waitForResults() {
        driver.switchTo().defaultContent();
        closePopup(3_000);
        try {
            wait(40_000).until(ExpectedConditions
                    .textToBePresentInElementLocated(By.xpath(locator.getTriviaResultHeader()), locator.getTriviaGameResultText()));
            messageNotify("game finished", Color.lightGray);
        } catch (Exception ex) {
            //TODO: seems like need to remove try catch block
        }
        //closePopup(2_000);
        wait(3_000).until(frameToBeAvailableAndSwitchToIt(By.xpath(locator.getPlanetsIFrame())));
        wait(2_000).until(presenceOfElementLocated(By.xpath(locator.getTriviaQuizResultWrap())));
    }

    private void handleResults() {
        String result = driver.findElement(By.xpath(locator.getTriviaQuizTitleResult())).getText().trim();
        String points = driver.findElement(By.xpath(locator.getTriviaResultPoints())).getText().trim();
        if (result.contains(locator.getTriviaWinText())) {
            gameResultNotify(GameResult.WIN, Integer.parseInt(points));
        } else if (result.contains(locator.getTriviaDrawText())) {
            gameResultNotify(GameResult.DRAW, 0);
        } else {
            gameResultNotify(GameResult.LOST, 0);
        }
    }

    private void checkAttempts() throws NotEnoughEnergyException {
        String attempts = driver.findElement(By.xpath(locator.getTriviaEnergyCountCard())).getText().trim();
        driver.switchTo().defaultContent();
        closePopup(2_000);
        wait(2_000).until(frameToBeAvailableAndSwitchToIt(By.xpath(locator.getPlanetsIFrame())));
        if (attempts.contains("0")) {

            if (unlimOption > 0 && unlimOption < 4) {
                driver.findElement(By.xpath(locator.getTriviaReturnToMainPageButton())).click();
                wait(10_000).until(frameToBeAvailableAndSwitchToIt(By.xpath(locator.getPlanetsIFrame())));
                wait(5_000).until(elementToBeClickable(By.xpath(locator.getTriviaUnlimShopButton()))).click();
                wait(10_000).until(frameToBeAvailableAndSwitchToIt(By.xpath(locator.getPlanetsIFrame())));
                wait(5_000).until(elementToBeClickable(By.xpath(locator.getTriviaBuyUnlimButton(unlimOption)))).click();
                driver.switchTo().defaultContent();
                wait(5_000).until(elementToBeClickable(By.xpath(locator.getFooterAcceptButton()))).click();
                try {
                    TimeUnit.SECONDS.sleep(3);
                } catch (InterruptedException ex) {
                    logger.error(ex.getMessage());
                }
                wait(10_000).until(frameToBeAvailableAndSwitchToIt(By.xpath(locator.getPlanetsIFrame())));
                driver.findElement(By.xpath(locator.getTriviaReturnToMainPageButton())).click();

            } else {
                String statusMessage = driver.findElement(By.xpath(locator.getTriviaEnergyTimer())).getText();
                messageNotify("no attempts left, sleep time: " + statusMessage, Color.lightGray);
                int timeToSleep = Integer.parseInt(statusMessage.substring(0, statusMessage.indexOf(" ")));
                if (statusMessage.contains(locator.getTriviaMinutesText())) {
                    timeToSleep = (timeToSleep + 2) * 60;
                } else {
                    timeToSleep += 60;
                }
                checkDailyRatings();
                throw new NotEnoughEnergyException(timeToSleep, statusMessage);
            }

        } else if (!attempts.isEmpty()) {
            messageNotify("attempts left: " + attempts, Color.lightGray);
        }
    }

    private String getFileNameTimeStamp() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMyyyy_HHmmss"));//DateTimeFormatter.ofPattern("ddMMyyyy_HHmmss").format(LocalDateTime.now());
    }

    private void checkDailyRatings() {
        driver.findElement(By.xpath(locator.getTriviaReturnToMainPageButton())).click();
        wait(10_000).until(frameToBeAvailableAndSwitchToIt(By.xpath(locator.getPlanetsIFrame())));
        String ownDailyResult = driver.findElement(By.xpath(locator.getTriviaOwnDailyResult())).getText();
        driver.findElement(By.xpath(locator.getTriviaDailyRatingsPageButton())).click();
        wait(10_000).until(frameToBeAvailableAndSwitchToIt(By.xpath(locator.getPlanetsIFrame())));
        String tenthPlaceResult = driver.findElement(By.xpath(locator.getTriviaTenthPlaceDailyResult())).getText();
        messageNotify("RATING: my - " + ownDailyResult + "  10th: - " + tenthPlaceResult, Color.cyan);
    }

    private void buyUnlimStatus() {
        driver.findElement(By.xpath(locator.getTriviaReturnToMainPageButton())).click();
        wait(10_000).until(frameToBeAvailableAndSwitchToIt(By.xpath(locator.getPlanetsIFrame())));
        wait(5_000).until(elementToBeClickable(By.xpath(locator.getTriviaUnlimShopButton()))).click();
        wait(10_000).until(frameToBeAvailableAndSwitchToIt(By.xpath(locator.getPlanetsIFrame())));
        wait(5_000).until(elementToBeClickable(By.xpath(locator.getTriviaBuyUnlimButton(unlimOption)))).click();
        driver.switchTo().defaultContent();
        wait(5_000).until(elementToBeClickable(By.xpath(locator.getFooterAcceptButton()))).click();
        messageNotify("UNLIM " + unlimOption + " was bought", Color.cyan);
        unlimOption = 0;
        shoppingNotify();
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException ex) {
            //java.util.logging.Logger.getLogger(BotImpl.class.getName()).log(Level.SEVERE, null, ex);
        }
        wait(10_000).until(frameToBeAvailableAndSwitchToIt(By.xpath(locator.getPlanetsIFrame())));
        driver.findElement(By.xpath(locator.getTriviaReturnToMainPageButton())).click();
        
    }

    private void continuePlaying() {
        closePopup(3_000);        
        wait(15_000).until(frameToBeAvailableAndSwitchToIt(By.xpath(locator.getPlanetsIFrame())));
        wait(15_000).until(presenceOfElementLocated(By.xpath(locator.getTriviaStartButton()))).click();
        wait(15_000).until(frameToBeAvailableAndSwitchToIt(By.xpath(locator.getPlanetsIFrame())));
        List<WebElement> topics = wait(10_000).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.xpath(locator.getTriviaTopic())));
        selectTopic(topics);
        //TODO: not enough coins
    }

}
