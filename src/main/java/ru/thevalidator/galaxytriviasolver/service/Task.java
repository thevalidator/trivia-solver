/*
 * Copyright (C) 2022 thevalidator
 */
package ru.thevalidator.galaxytriviasolver.service;

import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.thevalidator.galaxytriviasolver.exception.LoginErrorException;
import ru.thevalidator.galaxytriviasolver.gui.TriviaMainWindow;
import ru.thevalidator.galaxytriviasolver.module.base.GalaxyBaseRobot;
import ru.thevalidator.galaxytriviasolver.module.base.impl.GalaxyBaseRobotImpl;
import ru.thevalidator.galaxytriviasolver.module.trivia.State;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class Task implements Runnable {

    private static final Logger logger = LogManager.getLogger(Task.class);
    private boolean isActive;
    private final State state;
    private final TriviaMainWindow window;

    public Task(State state, TriviaMainWindow window) {
        this.state = state;
        this.window = window;
        this.isActive = false;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    @Override
    public void run() {
        window.setStartButtonStatus(1);
        window.appendToPane("STARTED");
        GalaxyBaseRobot robot = new GalaxyBaseRobotImpl(state);
        ((GalaxyBaseRobotImpl) robot).registerObserver(window);
        int sleepTimeInSeconds = 0;
        while (isActive) {
            try {

                robot.login();
                robot.openGames();
                robot.selectTriviaGame();
                if (robot.startTriviaGame()) {
                    robot.playTriviaGame();
                }
                sleepTimeInSeconds = robot.getSleepTime();
                if (state.shouldPlayRides()) {
                    robot.switchToDefaultContent();
                    robot.openGames();
                    robot.selectRidesGame();
                    if (robot.startRidesGame()) {
                        robot.playRidesGame();
                    }
                }

//                while (isActive) {
//                    TimeUnit.SECONDS.sleep(10);
//                }

            } catch (Exception e) {
                logger.error(e.getMessage());
                String filename = ((GalaxyBaseRobotImpl) robot).getFileNameTimeStamp();
                ((GalaxyBaseRobotImpl) robot).takeScreenshot(filename + ".png");
                ((GalaxyBaseRobotImpl) robot).saveDataToFile(filename, e);
                if (e instanceof LoginErrorException) {
                    isActive = false;
                }
            } finally {
                robot.terminate();
                if (isActive) {
                    try {
                        int time = state.shouldPlayRides() ? sleepTimeInSeconds : 120 + sleepTimeInSeconds;
                        sleepTimeInSeconds = 0;
                        String message = time > 60 ? (String.valueOf(time / 60) + " min") : (String.valueOf(time) + " sec");
                        window.appendToPane("SLEEPING " + message);
                        TimeUnit.SECONDS.sleep(time);
                    } catch (InterruptedException ex) {
                        return;
                    }
                } else {
                    ((GalaxyBaseRobotImpl) robot).unregisterObserver(window);
                    window.setStartButtonStatus(-1);
                    window.appendToPane("STOPPED");
                }
            }
        }
    }

}
