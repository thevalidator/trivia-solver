/*
 * Copyright (C) 2022 thevalidator
 */
package ru.thevalidator.galaxytriviasolver.service;

import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.thevalidator.galaxytriviasolver.exception.LoginErrorException;
import ru.thevalidator.galaxytriviasolver.exception.TokenNotFoundErrorException;
import ru.thevalidator.galaxytriviasolver.gui.TriviaMainWindow;
import ru.thevalidator.galaxytriviasolver.module.base.GalaxyBaseRobot;
import ru.thevalidator.galaxytriviasolver.module.base.impl.GalaxyBaseRobotImpl;
import ru.thevalidator.galaxytriviasolver.module.trivia.State;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class Task implements Runnable {

    private static final Logger logger = LogManager.getLogger(Task.class);
    private static final int TIME_TO_SLEEP_IN_SECONDS = 60;
    public static volatile boolean isActive = false;
    private final State state;
    private final TriviaMainWindow window;
    private GalaxyBaseRobot robot;

    public Task(State state, TriviaMainWindow window) {
        this.state = state;
        this.window = window;
        this.robot = new GalaxyBaseRobotImpl(state);
        isActive = false;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setIsActive(boolean value) {
        synchronized (this) {
            isActive = value;
        }
    }
    
    public void hardStopAction() {
        isActive = false;
        robot.terminate();
        ((GalaxyBaseRobotImpl) robot).unregisterObserver(window);
        window.setStartButtonStatus(-1);
    }

    @Override
    public void run() {
        window.setStartButtonStatus(1);
        window.appendToPane("STARTED");
        //GalaxyBaseRobot robot = new GalaxyBaseRobotImpl(state);
        ((GalaxyBaseRobotImpl) robot).registerObserver(window);
        int sleepTimeInSeconds = TIME_TO_SLEEP_IN_SECONDS;
        
        while (isActive) {
            try {
                robot.login();
                robot.openGames();
                robot.selectTriviaGame();
                if (robot.startTriviaGame()) {
                    robot.playTriviaGame();
                }
                sleepTimeInSeconds = robot.getSleepTime();
            } catch (Exception e) {
                //System.out.println("ERR = " + e.getMessage());
                logger.error(e.getMessage());
                String filename = ((GalaxyBaseRobotImpl) robot).getFileNameTimeStamp();
                ((GalaxyBaseRobotImpl) robot).takeScreenshot(filename + ".png");
                ((GalaxyBaseRobotImpl) robot).saveDataToFile(filename, e);
                ((GalaxyBaseRobotImpl) robot).savePageSourceToFile(filename);
                if ((e instanceof LoginErrorException) || (e instanceof TokenNotFoundErrorException)) {
                    window.appendToPane("ERROR: " + e.getMessage());
                    isActive = false;
                }
            } finally {
                robot.terminate();
                if (isActive) {
                    try {
                        int time = state.shouldPlayRides() ? sleepTimeInSeconds : 120 + sleepTimeInSeconds;
                        sleepTimeInSeconds = TIME_TO_SLEEP_IN_SECONDS;
                        String message = time >= 60 ? (String.valueOf(time / 60) + " min") : (String.valueOf(time) + " sec");
                        window.appendToPane("SLEEPING " + message);
                        TimeUnit.SECONDS.sleep(time);
                    } catch (InterruptedException ex) {
                        //return;
                    }
                } else {
                    ((GalaxyBaseRobotImpl) robot).unregisterObserver(window);
                    window.setStartButtonStatus(-1);
                    window.appendToPane("STOPPED - " + Thread.currentThread().getName());
                }
            }
        }
    }

}
