/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.galaxytriviasolver.module.robot;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.frameToBeAvailableAndSwitchToIt;
import static org.openqa.selenium.support.ui.ExpectedConditions.not;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.textToBe;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfAllElementsLocatedBy;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.thevalidator.galaxytriviasolver.exception.CanNotPlayException;
import ru.thevalidator.galaxytriviasolver.exception.ExceptionUtil;
import ru.thevalidator.galaxytriviasolver.exception.LoginErrorException;
import ru.thevalidator.galaxytriviasolver.exception.LoginFailException;
import ru.thevalidator.galaxytriviasolver.module.trivia.GameResult;
import ru.thevalidator.galaxytriviasolver.module.trivia.State;
import ru.thevalidator.galaxytriviasolver.module.trivia.TriviaUserStatsData;
import ru.thevalidator.galaxytriviasolver.module.trivia.Unlim;
import ru.thevalidator.galaxytriviasolver.module.trivia.UnlimUtil;
import ru.thevalidator.galaxytriviasolver.module.trivia.solver.Solver;
import ru.thevalidator.galaxytriviasolver.module.trivia.solver.entity.trivia.Answer;
import ru.thevalidator.galaxytriviasolver.module.trivia.solver.entity.trivia.Question;
import ru.thevalidator.galaxytriviasolver.notification.Informer;
import ru.thevalidator.galaxytriviasolver.service.Task;
import ru.thevalidator.galaxytriviasolver.util.formatter.DateTimeFormatter;
import ru.thevalidator.galaxytriviasolver.util.formatter.impl.DateTimeFormatterForName;
import ru.thevalidator.galaxytriviasolver.util.webdriver.WebDriverUtil;
import ru.thevalidator.galaxytriviasolver.web.Locale;
import static ru.thevalidator.galaxytriviasolver.web.Locator.*;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public abstract class Robot extends Informer implements GalaxyBaseRobot {

    private static final Logger logger = LogManager.getLogger(Robot.class);
    private static final Random random = new Random();

    private final Solver solver;
    private final Task task;
    protected WebDriver driver;
    private final TriviaUserStatsData userStats;
    protected final State state;
    private final DateTimeFormatter formatter;

    public Robot(Solver solver, Task task, WebDriver driver, State state) {
        this.solver = solver;
        this.task = task;
        this.driver = driver;
        this.state = state;
        this.userStats = new TriviaUserStatsData();
        formatter = new DateTimeFormatterForName();
    }

    public void setDriver(WebDriver driver) {
        this.driver = driver;
    }

    @Override
    public void login() throws LoginErrorException, LoginFailException {
        try {
            openURL();
            imitateHumanActivityDelay(6);

            WebDriverWait loginWait = WebDriverUtil.wait(driver, 60_000);
            //wait(60_000).until(visibilityOfElementLocated(By.xpath(getBaseCookiesCloseBtn()))).click();
            loginWait.until(elementToBeClickable(By.xpath(getBaseCookiesCloseBtn()))).click();
            loginWait.until(elementToBeClickable(By.xpath(getBaseHaveAccountBtn()))).click();

            driver.findElement(By.xpath(getBaseRecoveryCodeField())).sendKeys(state.getUser().getCode());
            driver.findElement(By.xpath(getBaseFooterAcceptBtn())).click();
            TimeUnit.SECONDS.sleep(2);
            if (loginWait.until(presenceOfElementLocated(By.xpath(getBaseAuthUserContent()))).isDisplayed()) {
                informObservers("logged in successfully");
            } else {
                WebDriverWait wait = WebDriverUtil.wait(driver, 10_000);
                wait.until(frameToBeAvailableAndSwitchToIt(By.xpath(getBasePopupIframe())));
                WebElement elem = wait.until(visibilityOfElementLocated(By.xpath(getBaseLoginFailPopuDiv())));
                String reason = elem.getText();
                throw new LoginFailException(reason);
            }
        } catch (Exception e) {
            if (e instanceof LoginFailException loginFailException) {
                throw loginFailException;
            } else {
                throw new LoginErrorException(e.getMessage());
            }
        }
    }

    @Override
    public void openMail() {
        closePopup(2_500);
        WebDriverWait wait = WebDriverUtil.wait(driver, 15_000);
        wait.until(elementToBeClickable(By.xpath(getBaseMenuMailBtn(state.getLocale())))).click();
        closePopup(2_500);
        wait.until(frameToBeAvailableAndSwitchToIt(By.xpath(getBaseContentIframe())));
        try {
            wait.until(visibilityOfElementLocated(By.xpath(getNotificationsBtn()))).click();
            TimeUnit.SECONDS.sleep(3);
        } catch (Exception e) {
            driver.switchTo().defaultContent();
            //TODO: check if no notifiaction folder works without errors
        }
    }

    @Override
    public void openGames() {
        closePopup(2_500);
        WebDriverUtil.wait(driver, 15_000).until(elementToBeClickable(By.xpath(getBaseMenuGamesBtn(state.getLocale())))).click();
    }

    @Override
    public void logoff() {
        try {
            informObservers("loggin off");
            driver.switchTo().defaultContent();
            closePopup(2_500);
            WebDriverUtil.wait(driver, 15_000).until(elementToBeClickable(By.xpath(getBaseMenuExitBtn(state.getLocale())))).click();
        } catch (Exception e) {
            logger.error(ExceptionUtil.getFormattedDescription(e));
        }
    }

    @Override
    public void terminate() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Override
    public void refreshPage() {
        driver.navigate().refresh();
    }

    @Override
    public void switchToDefaultContent() {
        driver.switchTo().defaultContent();
    }

    @Override
    public void selectTriviaGame() {
        closePopup(4_500);
        WebDriverWait wait = WebDriverUtil.wait(driver, 15_000);
        wait.until(frameToBeAvailableAndSwitchToIt(By.xpath(getBaseContentIframe())));
        wait.until(visibilityOfElementLocated(By.xpath(getGamesTriviaBtn(state.getLocale())))).click();
        informObservers("opening Trivia");
        updateTriviaUsersData();
    }

    @Override
    public boolean startTriviaGame() throws CanNotPlayException {
        closePopup(1_500);
        WebDriverUtil.wait(driver, 15_000).until(frameToBeAvailableAndSwitchToIt(By.xpath(getBaseContentIframe())));
        String attempts = WebDriverUtil.wait(driver, 10_000).until(visibilityOfElementLocated(By.xpath(getTriviaEnergyCount()))).getText();

        if (attempts.equals("0")) {
            if (state.isManualStrategy() && state.getUnlimStrategyTime() > 0) {
                informObservers("NOT SUPPORTED BUY UNLIM ON STARTUP IN MANUAL MODE");
                return false;
            } else if (state.shouldGetOnTop() || state.shouldStayInTop()) {
                if (needUnlim()) {
                    if (userStats.isUnlimAvailable(Unlim.MAX)) {
                        informObservers("BUYING UNLIM TO REACH THE TARGET!");
                        buyUnlimOption(Unlim.MAX);
                    } else {
                        informObservers("not enough coins to buy unlim!");
                        return false;
                    }
                } else {
                    informObservers("TOP list target is OK");
                    return false;
                }
            } else {
                informObservers("TRIVIA: no attempts available");
                return false;
            }
        }

        WebElement anonSwitcher = driver.findElement(By.xpath(getTriviaAnonymousToggle()));//wait(15_000).until(presenceOfElementLocated(By.xpath(getTriviaAnonymousToggle())));
        switchAnonToggle(anonSwitcher);
        driver.findElement(By.xpath(getTriviaStartBtn(state.getLocale()))).click();
        driver.switchTo().defaultContent();
        closePopup(1_500);
        WebDriverUtil.wait(driver, 15_000).until(frameToBeAvailableAndSwitchToIt(By.xpath(getBaseContentIframe())));
        List<WebElement> topics = WebDriverUtil.wait(driver, 10_000).until(visibilityOfAllElementsLocatedBy(By.xpath(getTriviaTopicDiv())));
        String selectedTopic = state.getLocale().getTopics()[state.getTopicIndex()];
        WebElement topic;
        if (state.getTopicIndex() != 0) {
            topic = getTopic(selectedTopic, topics);
        } else {
            int randomIndex = random.nextInt(topics.size());
            topic = topics.get(randomIndex);
            informObservers("TRIVIA: selected topic - '" + state.getLocale().getTopics()[randomIndex + 1] + "'");
        }
        topic.click();
        return true;
    }

    @Override
    public void playTriviaGame() {
        while (task.isRunning()) {
            WebDriverUtil.wait(driver, 70_000).until(visibilityOfElementLocated(By.xpath(getTriviaGameProcessFrame()))).isDisplayed();
            informObservers("game started");
            answerQuestions();
            WebDriverUtil.wait(driver, 30_000).until(visibilityOfElementLocated(By.xpath(getTriviaGameResultsFrame()))).isDisplayed();
            informObservers("game finished");

            driver.switchTo().frame(driver.findElement(By.xpath(getTriviaGameResultsFrame())));

            String attempts = driver.findElement(By.xpath(getTriviaEnergyCount())).getText().trim();
            int points = Integer.parseInt(driver.findElement(By.xpath(getTriviaResultPoints())).getText().trim());
            GameResult result = getTriviaRoundResult();
            if (result.equals(GameResult.WIN)) {
                userStats.setUserDailyPoints(userStats.getUserDailyPoints() + points);
            }
            gameResultNotifyObservers(result, points, userStats);

            if (!task.isRunning()) {
                break;
            }

            if (attempts.equals("0")) {
                informObservers("not enough energy");
                if (state.isManualStrategy()) {
                    if (state.getUnlimStrategyTime() > 0) {

                        int unlimTime = state.getUnlimStrategyTime();

                        if (!UnlimUtil.isUnlimAvailable(userStats.getUserCoins(), unlimTime)) {
                            String message = String.format("Not enough coins for the selected strategy. Need: %.2f, you have: %.2f",
                                    UnlimUtil.getPrice(unlimTime), userStats.getUserCoins());
                            informObservers(message);
                            break;
                        }

                        driver.switchTo().defaultContent();
                        closePopup(1_500);
                        driver.switchTo().frame(driver.findElement(By.xpath(getTriviaGameResultsFrame())));
                        driver.findElement(By.xpath(getTriviaReturnToMainPageBtn())).click();
                        updateTriviaUsersData();
                        driver.switchTo().frame(driver.findElement(By.xpath(getTriviaGameMainFrame())));
                        driver.switchTo().defaultContent();

                        int maxUnlimCount = unlimTime / UnlimUtil.getUnlimMinutesValue(Unlim.MAX);
                        if (maxUnlimCount > 0) {
                            if (strategyRequireToBuyUnlim(unlimTime, Unlim.MAX)) {
                                continue;
                            }
                        }

                        int midUnlimCount = unlimTime / UnlimUtil.getUnlimMinutesValue(Unlim.MID);
                        if (midUnlimCount > 0) {
                            if (strategyRequireToBuyUnlim(unlimTime, Unlim.MID)) {
                                continue;
                            }
                        }

                        int minUnlimCount = unlimTime / UnlimUtil.getUnlimMinutesValue(Unlim.MIN);
                        if (minUnlimCount > 0) {
                            if (strategyRequireToBuyUnlim(unlimTime, Unlim.MIN)) {
                                continue;
                            }
                        }
                        break;
                    } else {
                        break;
                    }

                } else if (state.shouldGetOnTop() || state.shouldStayInTop()) {
                    driver.switchTo().defaultContent();
                    closePopup(1_500);
                    driver.switchTo().frame(driver.findElement(By.xpath(getTriviaGameResultsFrame())));
                    driver.findElement(By.xpath(getTriviaReturnToMainPageBtn())).click();
                    updateTriviaUsersData();

                    int pointsDiff = state.shouldGetOnTop()
                            ? userStats.getFirstPlacePoints() - userStats.getUserDailyPoints()
                            : userStats.getTenthPlacePoints() - userStats.getUserDailyPoints();

                    int hoursLeft = getLeftTimeInHours();

                    informObservers("Diff: " + pointsDiff
                            + " hours left: " + hoursLeft
                            + " coins: " + userStats.getUserCoins());

                    driver.switchTo().frame(driver.findElement(By.xpath(getTriviaGameMainFrame())));
                    if (state.isPassive() && hoursLeft > 6 && pointsDiff < 20_000) {
                        break;
                    }

                    driver.switchTo().defaultContent();
                    
                    if (needUnlim()) {
                        Unlim unlimType = Unlim.MAX;
                        if (userStats.isUnlimAvailable(unlimType)) {
                            informObservers("BUYING UNLIM TO REACH THE TARGET!");
                            buyUnlimOption(unlimType);
                            try {
                                startTriviaGame();
                                continue;
                            } catch (CanNotPlayException ex) {
                                WebDriverUtil.takeScreenshot(driver, getFileNameTimeStamp() + "_continueAfterUnlim.png");
                                logger.error(ex.getMessage());
                            }
                        } else {
                            String message = "not enough coins to buy unlim \n"
                                    + " coins: " + userStats.getUserCoins();
                            informObservers(message);
                            WebDriverUtil.wait(driver, 15_000).until(frameToBeAvailableAndSwitchToIt(By.xpath(getBaseContentIframe())));
                            break;
                        }
                    } else {
                        informObservers("Top list target is OK!");
                        WebDriverUtil.wait(driver, 15_000).until(frameToBeAvailableAndSwitchToIt(By.xpath(getBaseContentIframe())));
                        break;
                    }

                } else {
                    break;
                }

            } else if (attempts.isEmpty()) {
                String unlimLeftTime = driver.findElement(By.xpath(getTriviaEnergyTimer())).getText();
                informObservers("unlim is active (" + unlimLeftTime + " letf)");
            } else {
                informObservers("games left: " + attempts);
            }
            startAgainTriviaGame();
        }
    }

    @Override
    public int getSleepTime() {
        String statusMessage = driver.findElement(By.xpath(getTriviaEnergyTimer())).getText();
        int timeInSeconds = Integer.parseInt(statusMessage.substring(0, statusMessage.indexOf(" ")));
        if (statusMessage.contains("мин") || statusMessage.contains("min")) {
            int addMinutes = random.nextInt(3) + 2;
            timeInSeconds = (timeInSeconds + addMinutes) * 60;
        }

        return timeInSeconds;
    }

    public String getFileNameTimeStamp() {
        String path = "logs" + File.separator;
        return path + formatter.getFormattedDateTime(LocalDateTime.now());
    }

    public void saveDataToFile(String path, Exception ex) {
        try (StringWriter sw = new StringWriter(); PrintWriter pw = new PrintWriter(sw); FileOutputStream fos = new FileOutputStream(path); DataOutputStream outStream = new DataOutputStream(new BufferedOutputStream(fos))) {
            ex.printStackTrace(pw);
            outStream.writeUTF(sw.toString());
        } catch (IOException e) {
            logger.error(ExceptionUtil.getFormattedDescription(ex));
        }
    }

    private void openURL() {
        driver.get(Locale.getLocaleURL(state.getLocale()));
    }

    protected void closePopup(int timeToWait) {
        try {
            while (!WebDriverUtil.wait(driver, timeToWait)
                    .until(visibilityOfAllElementsLocatedBy(By.xpath(getBasePopupIframe()))).
                    isEmpty()) {
                if (!driver.findElements(By.xpath(getBasePopupCloseBtn())).isEmpty()) {
                    driver.findElement(By.xpath(getBasePopupCloseBtn())).click();
                } else {
                    driver.findElement(By.xpath(getBaseFooterCancelBtn())).click();
                }
            }
        } catch (Exception ignored) {
            //no popup found, do nothing
        }
    }

    private void updateTriviaUsersData() {
        closePopup(2_500);
        String userBalance = WebDriverUtil.wait(driver, 10_000).until(visibilityOfElementLocated(By.xpath(getBaseUserBalance()))).getText();
        WebDriverUtil.wait(driver, 15_000).until(frameToBeAvailableAndSwitchToIt(By.xpath(getBaseContentIframe())));
        String dailyPoints = WebDriverUtil.wait(driver, 10_000).until(visibilityOfElementLocated(By.xpath(getTriviaOwnDailyResult()))).getText();

        driver.findElement(By.xpath(getTriviaDailyRatingsPageBtn())).click();
        closePopup(2_500);
        WebDriverUtil.wait(driver, 15_000).until(frameToBeAvailableAndSwitchToIt(By.xpath(getBaseContentIframe())));
        String tenth = "0";
        String first = tenth;
        String second = tenth;
        try {
            first = WebDriverUtil.wait(driver, 10_000).until(visibilityOfElementLocated(By.xpath(getTriviaPositionDailyResult(1)))).getText();
        } catch (Exception ignored) {
        }
        try {
            second = WebDriverUtil.wait(driver, 10_000).until(visibilityOfElementLocated(By.xpath(getTriviaPositionDailyResult(2)))).getText();
        } catch (Exception ignored) {
        }
        try {
            tenth = WebDriverUtil.wait(driver, 10_000).until(visibilityOfElementLocated(By.xpath(getTriviaPositionDailyResult(10)))).getText();
        } catch (Exception ignored) {
        }

        driver.switchTo().defaultContent();
        driver.findElement(By.xpath(getBaseBackBtn())).click();

        userStats.setFirstPlacePoints(Integer.parseInt(first));
        userStats.setSecondPlacePoints(Integer.parseInt(second));
        userStats.setTenthPlacePoints(Integer.parseInt(tenth));
        userStats.setUserCoins(Double.parseDouble(userBalance));
        userStats.setUserDailyPoints(Integer.parseInt(dailyPoints));

        informObservers("TOPLIST: 1st: " + first + " 10th: " + tenth);
        informObservers("coins: " + userBalance + " daily points: " + dailyPoints);
    }

    private void switchAnonToggle(WebElement anonSwitcher) {
        if (state.isAnonymous() && !anonSwitcher.getAttribute("class").contains("checked")) {
            anonSwitcher.click();
        } else if (!state.isAnonymous() && anonSwitcher.getAttribute("class").contains("checked")) {
            anonSwitcher.click();
        }
    }

    private WebElement getTopic(String topic, List<WebElement> topics) throws CanNotPlayException {
        for (WebElement t: topics) {
            if (t.getText().equalsIgnoreCase(topic)) {
                return t;
            }
        }
        throw new CanNotPlayException("no selected topic found");
    }

    private void answerQuestions() {
        String questionText;
        for (int i = 1; i < 6; i++) {

            driver.switchTo().defaultContent();
            closePopup(2_000);
            WebDriverUtil.wait(driver, 6_000).until(frameToBeAvailableAndSwitchToIt(By.xpath(getTriviaGameProcessFrame())));
            questionText = WebDriverUtil.wait(driver, 20_000).until(presenceOfElementLocated(By.xpath(getTriviaQuestionHeader()))).getText();
            try {
                if (i != 5) {
                    TimeUnit.SECONDS.sleep(random.nextInt(8) + 2);
                } else {
                    TimeUnit.MILLISECONDS.sleep(500);
                }

                List<WebElement> elements = driver.findElements(By.xpath(getTriviaQuestionAnswer()));
                clickCorrectAnswer(questionText, elements);
                informObservers("question " + i + " answered");
            } catch (Exception e) {
                String path = getFileNameTimeStamp();
                WebDriverUtil.savePageSourceToFile(driver, path + "_Q.html");
                WebDriverUtil.takeScreenshot(driver, path + "_Q.png");
                saveDataToFile(path + "_Q.log", e);

                driver.switchTo().defaultContent();
                closePopup(2_000);
                WebDriverUtil.wait(driver, 6_000).until(frameToBeAvailableAndSwitchToIt(By.xpath(getTriviaGameProcessFrame())));
            }
            WebDriverUtil.wait(driver, 65_000).until(not(textToBe(By.xpath(getTriviaQuestionHeader()), questionText)));
        }
    }

    private void clickCorrectAnswer(String questionText, List<WebElement> elements) {
        Answer[] answers = new Answer[elements.size()];
        int index = 0;
        for (WebElement e: elements) {
            String text = e.getText();
            String rel = e.getAttribute("rel");
            Answer answer = new Answer(text, rel, index);
            answers[index++] = answer;
        }
        Question question = new Question(questionText, answers);
        Answer correctAnswer = solver.getCorrectAnswer(question);
        elements.get(correctAnswer.getIndex()).click();
    }

    private GameResult getTriviaRoundResult() {
        String result = driver.findElement(By.xpath(getTriviaResultHeader())).getText().trim();
        if (result.contains(Locale.getWinText(state.getLocale()))) {
            return GameResult.WIN;
        } else if (result.contains(Locale.getDrawText(state.getLocale()))) {
            return GameResult.DRAW;
        } else {
            //WebDriverUtil.takeScreenshot(driver, getFileNameTimeStamp() + "_lost.png");
            return GameResult.LOST;
        }
    }

    private void buyUnlimOption(Unlim option) {
        closePopup(1_500);
        WebDriverUtil.wait(driver, 10_000).until(frameToBeAvailableAndSwitchToIt(By.xpath(getBaseContentIframe())));
        driver.findElement(By.xpath(getTriviaUnlimShopBtn())).click();
        closePopup(1_500);
        WebDriverUtil.wait(driver, 10_000).until(frameToBeAvailableAndSwitchToIt(By.xpath(getBaseContentIframe())));
        WebDriverUtil.wait(driver, 5_000).until(elementToBeClickable(By.xpath(getTriviaBuyUnlimBtn(option)))).click();
        driver.switchTo().defaultContent();
        WebDriverUtil.wait(driver, 5_000).until(elementToBeClickable(By.xpath(getBaseFooterAcceptBtn()))).click();
        informObservers("UNLIM " + option.name() + " was bought");
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException ex) {
            logger.error(ex.getMessage());
        }
        WebDriverUtil.wait(driver, 10_000).until(frameToBeAvailableAndSwitchToIt(By.xpath(getBaseContentIframe())));
        driver.findElement(By.xpath(getTriviaReturnToMainPageBtn())).click();
        userStats.setUserCoins(Math.ceil((userStats.getUserCoins() - option.getPrice()) * 100) / 100);
    }

    private void startAgainTriviaGame() {
        driver.switchTo().defaultContent();
        closePopup(2_000);
        WebDriverUtil.wait(driver, 5_000).until(frameToBeAvailableAndSwitchToIt(By.xpath(getTriviaGameResultsFrame())));
        driver.findElement(By.xpath(getTriviaPlayAgainBtn(state.getLocale()))).click();
    }

    private boolean strategyRequireToBuyUnlim(int unlimTime, Unlim unlim) {
        try {
            buyUnlimOption(unlim);
            state.setUnlimStrategyTime(unlimTime - UnlimUtil.getUnlimMinutesValue(unlim));
            startTriviaGame();
            return true;
        } catch (CanNotPlayException ex) {
            WebDriverUtil.takeScreenshot(driver, getFileNameTimeStamp() + "_continueAfterUnlim.png");
            logger.error(ex.getMessage());
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return false;
    }

    private void sleepRandomPeriod(int bound) throws InterruptedException {
        int seconds = random.nextInt(bound + 4);
        TimeUnit.SECONDS.sleep(seconds);
    }

    private void imitateHumanActivityDelay(int delay) throws InterruptedException {
        if (state.getTriviaArgs().hasHumanImitation()) {
            sleepRandomPeriod(delay);
        }
    }

    private int getLeftTimeInHours() {
        int currentTimeInSeconds = LocalTime.now(ZoneId.of("Europe/Moscow")).toSecondOfDay();
        int timeLeftInSeconds = currentTimeInSeconds > TriviaUserStatsData.START_TIME_IN_SECONDS
                ? TriviaUserStatsData.ONE_DAY_TIME_IN_SECONDS - currentTimeInSeconds + TriviaUserStatsData.START_TIME_IN_SECONDS
                : TriviaUserStatsData.START_TIME_IN_SECONDS - currentTimeInSeconds;
        return Math.round(timeLeftInSeconds / 3_600F);
    }

    private boolean needUnlim() {
        int diff = userStats.getDiffWithLast();
        int hoursLeft = getLeftTimeInHours();
        return diff > -8_000 && hoursLeft >= 1;
    }

}
