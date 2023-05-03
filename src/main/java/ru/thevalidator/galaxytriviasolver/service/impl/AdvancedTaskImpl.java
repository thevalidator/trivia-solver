/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.galaxytriviasolver.service.impl;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import ru.thevalidator.galaxytriviasolver.exception.CanNotCreateWebdriverException;
import ru.thevalidator.galaxytriviasolver.exception.ExceptionUtil;
import ru.thevalidator.galaxytriviasolver.exception.LoginErrorException;
import ru.thevalidator.galaxytriviasolver.module.robot.GalaxyAdvancedRobot;
import ru.thevalidator.galaxytriviasolver.module.robot.impl.GalaxyAdvancedRobotImpl;
import ru.thevalidator.galaxytriviasolver.module.trivia.State;
import ru.thevalidator.galaxytriviasolver.module.trivia.solver.Solver;
import ru.thevalidator.galaxytriviasolver.notification.Observer;
import ru.thevalidator.galaxytriviasolver.service.Task;
import ru.thevalidator.galaxytriviasolver.util.webdriver.WebDriverUtil;
import ru.thevalidator.galaxytriviasolver.util.webdriver.impl.ChromeWebDriverUtilImpl;

public class AdvancedTaskImpl implements Task {

    private static final Logger logger = LogManager.getLogger(AdvancedTaskImpl.class);
    private static final int TIME_TO_SLEEP_IN_SECONDS = 120;
    private final AtomicBoolean isRunning = new AtomicBoolean(false);
    private Thread worker;
    private final Observer observer;
    private GalaxyAdvancedRobot robot;
    private State state;
    private final Solver solver;

    public AdvancedTaskImpl(Observer observer, Solver solver) {
        this.observer = observer;
        this.solver = solver;
    }

    @Override
    public void run() {
        observer.onUpdateRecieve("STARTED");
        worker = Thread.currentThread();
        isRunning.set(true);
        WebDriverUtil driverUtil = new ChromeWebDriverUtilImpl();
        WebDriver driver = null;

        robot = new GalaxyAdvancedRobotImpl(solver, this, driver, state);
        ((GalaxyAdvancedRobotImpl) robot).registerObserver(observer);

        int sleepTimeInSeconds = 60;    // or ZERO?
        int loginFailCount = 0;
        int maxLoginAttempts = 15;
        while (isRunning()) {
            try {
                driver = driverUtil.createWebDriver(state.getChromeArgs());
                ((GalaxyAdvancedRobotImpl) robot).setDriver(driver);

                robot.login();

                robot.openMail();
                robot.openGames();
                robot.selectTriviaGame();

                //play Trivia
                if (robot.startTriviaGame()) {
                    robot.playTriviaGame();

                }
                if (isRunning()) {
                    sleepTimeInSeconds = robot.getSleepTime();
                }

                //play Rides
                if (state.shouldPlayRides()) {
                    robot.switchToDefaultContent();
                    robot.openGames();
                    robot.selectRidesGame();
                    if (robot.startRidesGame()) {
                        robot.playRidesGame();
                    }
                }
            } catch (CanNotCreateWebdriverException e) {
                logger.error(ExceptionUtil.getFormattedDescription(e));
                String name = ((GalaxyAdvancedRobotImpl) robot).getFileNameTimeStamp() + "_CRITICAL";
                ((GalaxyAdvancedRobotImpl) robot).saveDataToFile(name + ".log", e);
                observer.onUpdateRecieve("CRITICAL ERROR, THE APP WILL STOP");
                observer.onUpdateRecieve(e.getMessage());
                stop();
                break;
            } catch (LoginErrorException e) {
                logger.error(ExceptionUtil.getFormattedDescription(e));
                String fileName = ((GalaxyAdvancedRobotImpl) robot).getFileNameTimeStamp() + "_login";
                ((GalaxyAdvancedRobotImpl) robot).saveDataToFile(fileName + ".log", e);
                WebDriverUtil.takeScreenshot(driver, fileName + ".png");
                if (++loginFailCount == maxLoginAttempts) {
                    observer.onUpdateRecieve("LOGIN FAIL! couldn't log in " + maxLoginAttempts + " times in a row, stopping the app");
                    stop();
                    break;
                } else {
                    int minutesToWait = (2 * loginFailCount) + ((loginFailCount - 1) * 10);
                    observer.onUpdateRecieve("LOGIN ERROR: try " + loginFailCount + " was unsuccessfull, next try in " + minutesToWait
                            + " mins\nreason: " + e.getMessage());
                    sleepTimeInSeconds = minutesToWait * 60;
                }
            } catch (Exception e) {
                logger.error(ExceptionUtil.getFormattedDescription(e));
                String name = ((GalaxyAdvancedRobotImpl) robot).getFileNameTimeStamp();
                ((GalaxyAdvancedRobotImpl) robot).saveDataToFile(name + ".log", e);
                observer.onUpdateRecieve("UNEXPECTED ERROR");
                if (!WebDriverUtil.isTerminated((RemoteWebDriver) driver)) {
                    WebDriverUtil.savePageSourceToFile(driver, name + "_src.html");
                    WebDriverUtil.takeScreenshot(driver, name + ".png");
                }
            } finally {
                terminate(driver);
                if (isRunning()) {
                    try {
                        int time = sleepTimeInSeconds;
                        sleepTimeInSeconds = TIME_TO_SLEEP_IN_SECONDS;
                        String message = time >= 60 ? (String.valueOf(time / 60) + " min") : (String.valueOf(time) + " sec");
                        observer.onUpdateRecieve("SLEEPING " + message);
                        TimeUnit.SECONDS.sleep(time);
                    } catch (InterruptedException ignored) {
                    }
                } else {
                    ((GalaxyAdvancedRobotImpl) robot).unregisterObserver(observer);
                }
            }
        }
    }

    @Override
    public void stop() {
        isRunning.set(false);
    }

    @Override
    public void interrupt() {
        isRunning.set(false);
        worker.interrupt();
    }

    @Override
    public boolean isRunning() {
        return isRunning.get();
    }

    @Override
    public void setState(State state) {
        this.state = state;
    }

    private void terminate(WebDriver driver) {
        if (driver != null && !WebDriverUtil.isTerminated((RemoteWebDriver) driver)) {
            robot.logoff();
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
            }
            driver.quit();
        }
    }

}
