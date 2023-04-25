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
import ru.thevalidator.galaxytriviasolver.exception.CanNotPlayException;
import ru.thevalidator.galaxytriviasolver.exception.ExceptionUtil;
import ru.thevalidator.galaxytriviasolver.exception.LoginErrorException;
import ru.thevalidator.galaxytriviasolver.module.trivia.GameResult;
import ru.thevalidator.galaxytriviasolver.module.trivia.State;
import ru.thevalidator.galaxytriviasolver.module.trivia.TriviaUserStatsData;
import ru.thevalidator.galaxytriviasolver.module.trivia.Unlim;
import ru.thevalidator.galaxytriviasolver.module.trivia.UnlimUtil;
import static ru.thevalidator.galaxytriviasolver.module.trivia.UnlimUtil.MAX_UNLIM_MINUTES;
import static ru.thevalidator.galaxytriviasolver.module.trivia.UnlimUtil.MID_UNLIM_MINUTES;
import static ru.thevalidator.galaxytriviasolver.module.trivia.UnlimUtil.MIN_UNLIM_MINUTES;
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
    private final WebDriver driver;
    private final TriviaUserStatsData userStats;
    private final State state;
    private DateTimeFormatter formatter;

    public Robot(Solver solver, Task task, WebDriver driver, State state) {
        this.solver = solver;
        this.task = task;
        this.driver = driver;
        this.state = state;
        this.userStats = new TriviaUserStatsData();
        formatter = new DateTimeFormatterForName();
    }

    @Override
    public void login() throws LoginErrorException {
        int maxAttempts = 15;
        for (int i = 1; i <= maxAttempts; i++) {
            try {

                openURL();

                //wait(60_000).until(visibilityOfElementLocated(By.xpath(getBaseCookiesCloseBtn()))).click();
                WebDriverUtil.wait(driver, 60_000).until(elementToBeClickable(By.xpath(getBaseCookiesCloseBtn()))).click();
                WebDriverUtil.wait(driver, 60_000).until(elementToBeClickable(By.xpath(getBaseHaveAccountBtn()))).click();

                driver.findElement(By.xpath(getBaseRecoveryCodeField())).sendKeys(state.getUser().getCode());
                driver.findElement(By.xpath(getBaseFooterAcceptBtn())).click();
                TimeUnit.SECONDS.sleep(2);
                if (WebDriverUtil.wait(driver, 60_000).until(presenceOfElementLocated(By.xpath(getBaseAuthUserContent()))).isDisplayed()) {
                    informObservers("logged in successfully");
                    break;
                }

            } catch (Exception e) {
                String fileName = getFileNameTimeStamp() + "_login";
                saveDataToFile(fileName + ".log", e);
                WebDriverUtil.takeScreenshot(driver, fileName + ".png");

                if (driver != null) {
                    driver.quit();
                }

                if (i == maxAttempts) {
                    informObservers("LOGIN ERROR: couldn't log in " + maxAttempts + " times in a row, task stopped");
                    throw new LoginErrorException(e.getMessage());
                } else {
                    int minutesToWait = (2 * i) + ((i - 1) * 10);
                    informObservers("LOGIN ERROR: try " + i + " was unsuccessfull, next try in " + minutesToWait
                            + "\nreason: " + e.getMessage());
                    try {
                        TimeUnit.MINUTES.sleep(minutesToWait);
                    } catch (InterruptedException ignored) {
                    }
                }
            }
        }
    }

    @Override
    public void openMail() {
        closePopup(2_500);
        WebDriverUtil.wait(driver, 15_000).until(elementToBeClickable(By.xpath(getBaseMenuMailBtn(state.getLocale())))).click();
        closePopup(2_500);
        WebDriverUtil.wait(driver, 15_000).until(frameToBeAvailableAndSwitchToIt(By.xpath(getBaseContentIframe())));
        try {
            WebDriverUtil.wait(driver, 15_000).until(visibilityOfElementLocated(By.xpath(getNotificationsBtn()))).click();
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
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void refreshPage() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void switchToDefaultContent() {
        driver.switchTo().defaultContent();
    }

    @Override
    public void selectTriviaGame() {
        closePopup(2_500);
        WebDriverUtil.wait(driver, 15_000).until(frameToBeAvailableAndSwitchToIt(By.xpath(getBaseContentIframe())));
        WebDriverUtil.wait(driver, 15_000).until(visibilityOfElementLocated(By.xpath(getGamesTriviaBtn(state.getLocale())))).click();
        informObservers("opening Trivia");
        updateTriviaUsersData();
    }

    @Override
    public boolean startTriviaGame() throws CanNotPlayException {
        closePopup(1_500);
        WebDriverUtil.wait(driver, 15_000).until(frameToBeAvailableAndSwitchToIt(By.xpath(getBaseContentIframe())));
        String attempts = WebDriverUtil.wait(driver, 10_000).until(visibilityOfElementLocated(By.xpath(getTriviaEnergyCount()))).getText();
        if (!attempts.equals("0")) {
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
        } else {
//            if (state.shouldGetOnTop() || state.shouldStayInTop()) {
//                if (pointsDiff > -5_000) {
//
//                    Unlim unlimType = Unlim.MAX;
//                    if (pointsDiff <= TriviaUserStatsData.AVERAGE_POINTS_PER_HOUR * hoursLeft
//                            && userStats.isUnlimAvailable(unlimType, (int) Math.ceil(hoursLeft / unlimType.getHours()))) {
//
//                        informObservers("BUYING UNLIM TO REACH THE TARGET!");
//                        buyUnlimOption(unlimType);
//                        try {
//                            startTriviaGame();
//                            continue;
//                        } catch (CanNotPlayException ex) {
//                            takeScreenshot(getFileNameTimeStamp() + "_continueAfterUnlim.png");
//                            logger.error(ex.getMessage());
//                        }
//                    } else {
//                        String message = "Top list target is UNREACHABLE!" + pointsDiff
//                                + " hour left: " + hoursLeft
//                                + " coins: " + userStats.getUserCoins();
//                        logger.error(message);
//                        informObservers("Top list target is UNREACHABLE!");
//                        wait(15_000).until(frameToBeAvailableAndSwitchToIt(By.xpath(getBaseContentIframe())));
//                        break;
//                    }
//                }
//            }
            informObservers("TRIVIA: no attempts available");
            return false;
        }
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
                        informObservers("1st: " + userStats.getFirstPlacePoints()
                                + " 10th: " + userStats.getTenthPlacePoints()
                                + " YOU: " + userStats.getUserDailyPoints());
                        driver.switchTo().frame(driver.findElement(By.xpath(getTriviaGameMainFrame())));
                        driver.switchTo().defaultContent();

                        int maxUnlimCount = unlimTime / MAX_UNLIM_MINUTES;
                        if (maxUnlimCount > 0) {
                            // buy max unlim
                            buyUnlimOption(Unlim.MAX);
                            state.setUnlimStrategyTime(unlimTime - MAX_UNLIM_MINUTES);
                            try {
                                startTriviaGame();
                                continue;
                            } catch (CanNotPlayException ex) {
                                WebDriverUtil.takeScreenshot(driver, getFileNameTimeStamp() + "_continueAfterUnlimMax.png");
                                logger.error(ex.getMessage());
                            }
                        }

                        int midUnlimCount = unlimTime / MID_UNLIM_MINUTES;
                        if (midUnlimCount > 0) {
                            // buy mid unlim
                            buyUnlimOption(Unlim.MID);
                            state.setUnlimStrategyTime(unlimTime - MID_UNLIM_MINUTES);
                            try {
                                startTriviaGame();
                                continue;
                            } catch (CanNotPlayException ex) {
                                WebDriverUtil.takeScreenshot(driver, getFileNameTimeStamp() + "_continueAfterUnlimMid.png");
                                logger.error(ex.getMessage());
                            }
                        }

                        int minUnlimCount = unlimTime / MIN_UNLIM_MINUTES;
                        if (minUnlimCount > 0) {
                            // buy min unlim
                            buyUnlimOption(Unlim.MIN);
                            state.setUnlimStrategyTime(unlimTime - MIN_UNLIM_MINUTES);
                            try {
                                startTriviaGame();
                                continue;
                            } catch (CanNotPlayException ex) {
                                WebDriverUtil.takeScreenshot(driver, getFileNameTimeStamp() + "_continueAfterUnlimMin.png");
                                logger.error(ex.getMessage());
                            }
                        }
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

                    int currentTimeInSeconds = LocalTime.now(ZoneId.of("Europe/Moscow")).toSecondOfDay();
                    int timeLeftInSeconds = currentTimeInSeconds > TriviaUserStatsData.START_TIME_IN_SECONDS
                            ? TriviaUserStatsData.ONE_DAY_TIME_IN_SECONDS - currentTimeInSeconds + TriviaUserStatsData.START_TIME_IN_SECONDS
                            : TriviaUserStatsData.START_TIME_IN_SECONDS - currentTimeInSeconds;
                    int hoursLeft = Math.round(timeLeftInSeconds / 3_600F);

                    informObservers("1st: " + userStats.getFirstPlacePoints()
                            + " 10th: " + userStats.getTenthPlacePoints()
                            + " YOU: " + userStats.getUserDailyPoints());

                    informObservers("Diff: " + pointsDiff
                            + " hours left: " + hoursLeft
                            + " coins: " + userStats.getUserCoins());

                    driver.switchTo().frame(driver.findElement(By.xpath(getTriviaGameMainFrame())));
                    if (state.isPassive() && state.shouldGetOnTop() && hoursLeft > 10 && pointsDiff < 20_000) {
                        break;
                    } else if (state.isPassive() && state.shouldStayInTop() && hoursLeft > 6 && pointsDiff < 40_000) {
                        break;
                    }

                    driver.switchTo().defaultContent();

                    if (state.shouldGetOnTop() && pointsDiff == 0) {
                        int pointsAhead = userStats.getUserDailyPoints() - userStats.getSecondPlacePoints();
                        if (pointsAhead > 10_000) {
                            break;
                        }
                    }

                    if (pointsDiff > -5_000) {

                        Unlim unlimType = state.shouldStayInTop() ? (pointsDiff > 16_000 ? Unlim.MAX : Unlim.MID) : Unlim.MAX;
                        if (pointsDiff <= TriviaUserStatsData.AVERAGE_POINTS_PER_HOUR * hoursLeft
                                && userStats.isUnlimAvailable(unlimType, (int) Math.ceil(hoursLeft / unlimType.getHours()))) {

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
                            String message = "Top list target is UNREACHABLE!" + pointsDiff
                                    + " hour left: " + hoursLeft
                                    + " coins: " + userStats.getUserCoins();
                            logger.error(message);
                            informObservers("Top list target is UNREACHABLE!");
                            WebDriverUtil.wait(driver, 15_000).until(frameToBeAvailableAndSwitchToIt(By.xpath(getBaseContentIframe())));
                            break;
                        }
                    } else {
                        informObservers("Top list target is OK!");
                        WebDriverUtil.wait(driver, 15_000).until(frameToBeAvailableAndSwitchToIt(By.xpath(getBaseContentIframe())));
                        break;
                    }

                } else {
                    informObservers("not enough energy");
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
            timeInSeconds = timeInSeconds * 60;
        }

        return timeInSeconds;
    }

    public String getFileNameTimeStamp() {
        String path = "logs" + File.separator;
        return path + formatter.getFormattedDateTime(LocalDateTime.now());
    }

    private void saveDataToFile(String path, Exception ex) {
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

    private void closePopup(int timeToWait) {
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
        informObservers("balance: " + userBalance + " my daily points: " + dailyPoints);
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
                driver.switchTo().defaultContent();
                closePopup(2_000);
                WebDriverUtil.wait(driver, 6_000).until(frameToBeAvailableAndSwitchToIt(By.xpath(getTriviaGameProcessFrame())));
            }
            WebDriverUtil.wait(driver, 45_000).until(not(textToBe(By.xpath(getTriviaQuestionHeader()), questionText)));
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
            WebDriverUtil.takeScreenshot(driver, getFileNameTimeStamp() + "_lost.png");
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
        userStats.setUserCoins(userStats.getUserCoins() - option.getPrice());
    }

    private void startAgainTriviaGame() {
        driver.switchTo().defaultContent();
        closePopup(2_000);
        WebDriverUtil.wait(driver, 5_000).until(frameToBeAvailableAndSwitchToIt(By.xpath(getTriviaGameResultsFrame())));
        driver.findElement(By.xpath(getTriviaPlayAgainBtn(state.getLocale()))).click();
    }

}
