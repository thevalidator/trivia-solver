/*
 * Copyright (C) 2022 thevalidator
 */
package ru.thevalidator.galaxytriviasolver.module.base.impl;

import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
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
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import static org.openqa.selenium.support.ui.ExpectedConditions.elementToBeClickable;
import static org.openqa.selenium.support.ui.ExpectedConditions.frameToBeAvailableAndSwitchToIt;
import static org.openqa.selenium.support.ui.ExpectedConditions.invisibilityOfElementLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.not;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;
import static org.openqa.selenium.support.ui.ExpectedConditions.textToBe;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfAllElementsLocatedBy;
import static org.openqa.selenium.support.ui.ExpectedConditions.visibilityOfElementLocated;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.thevalidator.galaxytriviasolver.notification.Informer;
import ru.thevalidator.galaxytriviasolver.exception.CanNotPlayException;
import ru.thevalidator.galaxytriviasolver.exception.LoginErrorException;
import ru.thevalidator.galaxytriviasolver.module.base.GalaxyBaseRobot;
import ru.thevalidator.galaxytriviasolver.module.trivia.GameResult;
import ru.thevalidator.galaxytriviasolver.module.trivia.State;
import ru.thevalidator.galaxytriviasolver.module.trivia.Unlim;
import ru.thevalidator.galaxytriviasolver.module.trivia.solver.Solver;
import ru.thevalidator.galaxytriviasolver.module.trivia.solver.entity.trivia.Answer;
import ru.thevalidator.galaxytriviasolver.module.trivia.solver.entity.trivia.Question;
import ru.thevalidator.galaxytriviasolver.module.trivia.solver.impl.SolverImpl;
import ru.thevalidator.galaxytriviasolver.web.Locale;
import static ru.thevalidator.galaxytriviasolver.web.Locator.*;

public class GalaxyBaseRobotImpl extends Informer implements GalaxyBaseRobot {

    class TriviaUserStatsData {

        private int userDailyPoints;
        private double userCoins;
        private int tenthPlacePoints;
        private int secondPlacePoints;
        private int firstPlacePoints;
        private static final int START_TIME_IN_SECONDS = 28_800;
        private static final int ONE_DAY_TIME_IN_SECONDS = 86_400;
        private static final int AVERAGE_POINTS_PER_HOUR = 7_000;

        public TriviaUserStatsData() {
            this.userCoins = 0;
            this.userDailyPoints = 0;
            this.tenthPlacePoints = 0;
            this.secondPlacePoints = 0;
            this.firstPlacePoints = 0;
        }

        public boolean isUnlimAvailable(Unlim type, int times) {
            return (userCoins - (type.getPrice() * times)) > 0;
        }

        public int getUserDailyPoints() {
            return userDailyPoints;
        }

        public void setUserDailyPoints(int userPoints) {
            this.userDailyPoints = userPoints;
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

        public int getSecondPlacePoints() {
            return secondPlacePoints;
        }

        public void setSecondPlacePoints(int secondPlacePoints) {
            this.secondPlacePoints = secondPlacePoints;
        }

    }

    private static final Logger logger = LogManager.getLogger(GalaxyBaseRobotImpl.class);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("ddMMyyyy_HHmmss");
    private static final Random random = new Random();

    private final State state;
    private WebDriver driver;
    private TriviaUserStatsData userStats;
    private final Solver solver;

    public GalaxyBaseRobotImpl(State state) {
        this.state = state;
        this.userStats = new TriviaUserStatsData();
        this.solver = new SolverImpl();
    }
    
    private WebDriver createWebDriver() {
        WebDriver webDriver = null;
        try {
            ChromeOptions options = new ChromeOptions();
            if (state.isHeadless()) {
                options.addArguments("--headless=new");
            }
            webDriver = new ChromeDriver(options);
            webDriver.manage().window().setSize(new Dimension(1600, 845));
            webDriver.manage().window().setPosition((new Point(-5, 0)));
            webDriver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));

        } catch (Exception e) {
            logger.error(Arrays.toString(e.getStackTrace()));
            informObservers("ERROR: Can't create webdriver");
            //throw new CanNotCreateWebdriverException("Can't create webdriver");
        }
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
        try (StringWriter sw = new StringWriter(); 
                PrintWriter pw = new PrintWriter(sw); 
                FileOutputStream fos = new FileOutputStream(pathname);  
                DataOutputStream outStream = new DataOutputStream(new BufferedOutputStream(fos))) {
            exception.printStackTrace(pw);
            outStream.writeUTF(sw.toString());
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
    
    public void savePageSourceToFile(String pathname) {
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(pathname + "_src", Charset.forName("UTF-8")))){
            bufferedWriter.write(driver.getPageSource());
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
            while (!wait(timeToWait).until(visibilityOfAllElementsLocatedBy(By.xpath(getBasePopupIframe()))).isEmpty()) {
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
        wait(20_000).until(visibilityOfElementLocated(By.xpath(getBaseCookiesCloseBtn()))).click();
        driver.findElement(By.xpath(getBaseHaveAccountBtn())).isDisplayed();
    }

    @Override
    public void switchToDefaultContent() {
        driver.switchTo().defaultContent();
    }

    @Override
    public void login() throws LoginErrorException {
        int maxAttempts = 15;
        for (int i = 1; i <= maxAttempts; i++) {
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
                saveDataToFile(fileName, e);
                if (i == maxAttempts) {
                    informObservers("LOGIN ERROR: couldn't log in " + maxAttempts + " times in a row, task stopped");
                    throw new LoginErrorException(e.getMessage());
                } else {
                    logger.error(e.getMessage());   //need to be deleted?
                    int timeToWait = (2 * i) + ((i - 1) * 10);
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
    public void logoff() {
        driver.switchTo().defaultContent();
        closePopup(2_500);
        wait(15_000).until(elementToBeClickable(By.xpath(getBaseMenuExitBtn(state.getLocale())))).click();
    }

    @Override
    public void openMail() {
        closePopup(2_500);
        wait(15_000).until(elementToBeClickable(By.xpath(getBaseMenuMailBtn(state.getLocale())))).click();
        closePopup(2_500);
        wait(15_000).until(frameToBeAvailableAndSwitchToIt(By.xpath(getBaseContentIframe())));
        try {
            wait(15_000).until(visibilityOfElementLocated(By.xpath(getNotificationsBtn()))).click();
            TimeUnit.SECONDS.sleep(3);
        } catch (Exception e) {
            driver.switchTo().defaultContent();
            //TODO: check if no notifiaction folder works without errors
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
        wait(15_000).until(frameToBeAvailableAndSwitchToIt(By.xpath(getBaseContentIframe())));
        wait(15_000).until(visibilityOfElementLocated(By.xpath(getGamesTriviaBtn(state.getLocale())))).click();
        informObservers("opening Trivia");
        updateTriviaUsersData();
        informObservers("TOPLIST: 1st: " + userStats.getFirstPlacePoints() + " 10th: " + userStats.tenthPlacePoints);
        informObservers("balance: " + userStats.userCoins + " daily points: " + userStats.userDailyPoints);
    }

    @Override
    public void selectRidesGame() {
        closePopup(2_500);
        wait(15_000).until(frameToBeAvailableAndSwitchToIt(By.xpath(getBaseContentIframe())));
        wait(15_000).until(visibilityOfElementLocated(By.xpath(getGamesRidesBtn(state.getLocale())))).click();
        informObservers("opening Rides");
    }

    private void updateTriviaUsersData() {
        closePopup(2_500);
        String userBalance = wait(10_000).until(visibilityOfElementLocated(By.xpath(getBaseUserBalance()))).getText();
        wait(15_000).until(frameToBeAvailableAndSwitchToIt(By.xpath(getBaseContentIframe())));
        String dailyPoints = wait(10_000).until(visibilityOfElementLocated(By.xpath(getTriviaOwnDailyResult()))).getText();

        driver.findElement(By.xpath(getTriviaDailyRatingsPageBtn())).click();
        closePopup(2_500);
        wait(15_000).until(frameToBeAvailableAndSwitchToIt(By.xpath(getBaseContentIframe())));
        String tenth = "0";
        String first = tenth;
        String second = tenth;
        try {
            first = wait(10_000).until(visibilityOfElementLocated(By.xpath(getTriviaPositionDailyResult(1)))).getText();
        } catch (Exception e) {
        }
        try {
            second = wait(10_000).until(visibilityOfElementLocated(By.xpath(getTriviaPositionDailyResult(2)))).getText();
        } catch (Exception e) {
        }
        try {
            tenth = wait(10_000).until(visibilityOfElementLocated(By.xpath(getTriviaPositionDailyResult(10)))).getText();
        } catch (Exception e) {
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

    @Override
    public boolean startTriviaGame() throws CanNotPlayException {
//        if () {
//            for soft stop
//        }
        closePopup(1_500);
        wait(15_000).until(frameToBeAvailableAndSwitchToIt(By.xpath(getBaseContentIframe())));
        String attempts = wait(10_000).until(visibilityOfElementLocated(By.xpath(getTriviaEnergyCount()))).getText();
        if (!attempts.equals("0")) {
            WebElement anonSwitcher = driver.findElement(By.xpath(getTriviaAnonymousToggle()));//wait(15_000).until(presenceOfElementLocated(By.xpath(getTriviaAnonymousToggle())));
            switchAnonToggle(anonSwitcher);
            driver.findElement(By.xpath(getTriviaStartBtn(state.getLocale()))).click();
            driver.switchTo().defaultContent();
            closePopup(1_500);
            wait(15_000).until(frameToBeAvailableAndSwitchToIt(By.xpath(getBaseContentIframe())));
            List<WebElement> topics = wait(10_000).until(visibilityOfAllElementsLocatedBy(By.xpath(getTriviaTopicDiv())));
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
            informObservers("TRIVIA: no attempts available");
            return false;
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
            /*boolean isStarted = */
            wait(70_000).until(visibilityOfElementLocated(By.xpath(getTriviaGameProcessFrame()))).isDisplayed();
            informObservers("game started");
            answerQuestions();
            /*boolean isFinished = */
            wait(30_000).until(visibilityOfElementLocated(By.xpath(getTriviaGameResultsFrame()))).isDisplayed();
            informObservers("game finished");

            driver.switchTo().frame(driver.findElement(By.xpath(getTriviaGameResultsFrame())));

            String attempts = driver.findElement(By.xpath(getTriviaEnergyCount())).getText().trim();
            String points = driver.findElement(By.xpath(getTriviaResultPoints())).getText().trim();
            GameResult result = getTriviaRoundResult();

            gameResultNotifyObservers(result, Integer.parseInt(points));

            if (attempts.equals("0")) {
                if (state.shouldGetOnTop() || state.shouldStayInTop()) {
                    driver.switchTo().defaultContent();
                    closePopup(1_500);
                    driver.switchTo().frame(driver.findElement(By.xpath(getTriviaGameResultsFrame())));
                    driver.findElement(By.xpath(getTriviaReturnToMainPageBtn())).click();
                    //check if on default frame
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
                                takeScreenshot(getFileNameTimeStamp() + "_continueAfterUnlim.png");
                                logger.error(ex.getMessage());
                            }
                        } else {
                            String message = "Top list target is UNREACHABLE!" + pointsDiff
                                    + " hour left: " + hoursLeft
                                    + " coins: " + userStats.getUserCoins();
                            logger.error(message);
                            informObservers("Top list target is UNREACHABLE!");
                            wait(15_000).until(frameToBeAvailableAndSwitchToIt(By.xpath(getBaseContentIframe())));
                            break;
                        }
                    } else {
                        informObservers("Top list target is OK!");
                        wait(15_000).until(frameToBeAvailableAndSwitchToIt(By.xpath(getBaseContentIframe())));
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
//            if () {
//                stop(); for soft stop
//            }
            startAgainTriviaGame();
        }

    }

    private void answerQuestions() {
        String questionText = null;
        for (int i = 0; i < 5; i++) {
            driver.switchTo().defaultContent();
            closePopup(1_000);
            wait(6_000).until(frameToBeAvailableAndSwitchToIt(By.xpath(getTriviaGameProcessFrame())));
            questionText = wait(20_000).until(presenceOfElementLocated(By.xpath(getTriviaQuestionHeader()))).getText();
            try {
                if (i != 0 && i != 4) {
                    TimeUnit.SECONDS.sleep(random.nextInt(10) + 2);
                } else {
                    TimeUnit.MILLISECONDS.sleep(500);
                }
            } catch (InterruptedException e) {
            }
            clickCorrectAnswer();
            wait(33_000).until(not(textToBe(By.xpath(getTriviaQuestionHeader()), questionText)));
        }
    }

    private void clickCorrectAnswer() {
        List<WebElement> elements = driver.findElements(By.xpath(getTriviaQuestionAnswer()));
        Answer[] answers = new Answer[elements.size()];
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
        String result = driver.findElement(By.xpath(getTriviaResultHeader())).getText().trim();
        if (result.contains(Locale.getWinText(state.getLocale()))) {
            return GameResult.WIN;
        } else if (result.contains(Locale.getDrawText(state.getLocale()))) {
            return GameResult.DRAW;
        } else {
            takeScreenshot(getFileNameTimeStamp() + "_lost.png");
            return GameResult.LOST;
        }
    }

    private void startAgainTriviaGame() {
        driver.switchTo().defaultContent();
        closePopup(2_000);
        wait(5_000).until(frameToBeAvailableAndSwitchToIt(By.xpath(getTriviaGameResultsFrame())));
        driver.findElement(By.xpath(getTriviaPlayAgainBtn(state.getLocale()))).click();
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

    private void buyUnlimOption(Unlim option) {
        closePopup(1_500);
        wait(10_000).until(frameToBeAvailableAndSwitchToIt(By.xpath(getBaseContentIframe())));
        driver.findElement(By.xpath(getTriviaUnlimShopBtn())).click();
        closePopup(1_500);
        wait(10_000).until(frameToBeAvailableAndSwitchToIt(By.xpath(getBaseContentIframe())));
        wait(5_000).until(elementToBeClickable(By.xpath(getTriviaBuyUnlimBtn(option)))).click();
        driver.switchTo().defaultContent();
        wait(5_000).until(elementToBeClickable(By.xpath(getBaseFooterAcceptBtn()))).click();
        informObservers("UNLIM " + option.name() + " was bought");
        try {
            TimeUnit.SECONDS.sleep(3);
        } catch (InterruptedException ex) {
            logger.error(ex.getMessage());
        }
        wait(10_000).until(frameToBeAvailableAndSwitchToIt(By.xpath(getBaseContentIframe())));
        driver.findElement(By.xpath(getTriviaReturnToMainPageBtn())).click();
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
    public boolean startRidesGame() {
        closePopup(1_500);
        wait(15_000).until(frameToBeAvailableAndSwitchToIt(By.xpath(getBaseContentIframe())));
        String attempts = wait(10_000).until(visibilityOfElementLocated(By.xpath(getRidesGameAttemptsCounter()))).getText().trim();
        informObservers("Race attempts: " + attempts);
        if (attempts.equals("0")) {
            return false;
        }
        driver.findElement(By.xpath(getRidesStartRaceBtn())).click();

        return true;
    }

    @Override
    public void playRidesGame() {
        int wins = 0;
        while (true) {
            wait(15_000).until(frameToBeAvailableAndSwitchToIt(By.xpath(getBaseContentIframe())));
            informObservers("Race: searching for the opponent");
            wait(25_000).until(visibilityOfElementLocated(By.xpath(getRidesWaitOverlay())));
            wait(25_000).until(invisibilityOfElementLocated(By.xpath(getRidesWaitOverlay())));
            informObservers("Race started");
            try {
                long start = System.currentTimeMillis();

                while ((System.currentTimeMillis() - start) < state.getNosDelayTime()) {
                    java.lang.Thread.onSpinWait();
                }
                try {
                    wait(500).until(elementToBeClickable(By.xpath(getRidesNitroBtn()))).click();
                } catch (Exception e) {

                }
                wait(15_000).until(
                        visibilityOfElementLocated(
                                By.xpath(getRidesPopupCloseBtn())
                        )
                ).click();
                while (true) {
                    wait(3_000).until(
                            visibilityOfElementLocated(
                                    By.xpath(getRidesPopupCloseBtn())
                            )
                    ).click();
                }
            } catch (Exception e) {
            }

            WebElement resultDiv = wait(3_000).until(visibilityOfElementLocated(By.xpath(getRidesResultsDiv())));
            if (resultDiv.getAttribute("id").contains("lose")) {
                informObservers("Race finished - LOST");
            } else {
                informObservers("Race finished - WIN");
                wins++;
            }

            driver.switchTo().defaultContent();
            closePopup(1_500);
            wait(5_000).until(frameToBeAvailableAndSwitchToIt(By.xpath(getBaseContentIframe())));

            //race again button
            WebElement raceAgainBtn = driver.findElement(By.xpath(getRidesRaceAgainBtn()));

            if (raceAgainBtn.getAttribute("action") != null) {
                raceAgainBtn.click();
            } else {
                informObservers("Race: no attempts left, won " + wins + " races.");
                break;
            }
        }

    }

}
