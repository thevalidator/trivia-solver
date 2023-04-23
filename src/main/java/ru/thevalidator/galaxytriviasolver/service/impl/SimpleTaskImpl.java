/*
 * Copyright (C) 2022 thevalidator
 */
package ru.thevalidator.galaxytriviasolver.service.impl;

import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.thevalidator.galaxytriviasolver.exception.LoginErrorException;
import ru.thevalidator.galaxytriviasolver.exception.TokenNotFoundErrorException;
import ru.thevalidator.galaxytriviasolver.gui.v2.TriviaMainWindow;
import ru.thevalidator.galaxytriviasolver.module.base.GalaxyBaseRobot;
import ru.thevalidator.galaxytriviasolver.module.base.impl.GalaxyBaseRobotImpl;
import ru.thevalidator.galaxytriviasolver.module.trivia.State;
import ru.thevalidator.galaxytriviasolver.service.Task;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class SimpleTaskImpl implements Runnable, Task {

    private static final Logger logger = LogManager.getLogger(SimpleTaskImpl.class);
    private static final int TIME_TO_SLEEP_IN_SECONDS = 60;
    public static volatile boolean isActive = false;
    private final State state;
    private final TriviaMainWindow window;
    private GalaxyBaseRobot robot;

    public SimpleTaskImpl(State state, TriviaMainWindow window) {
        this.state = state;
        this.window = window;
        this.robot = new GalaxyBaseRobotImpl(state, this);
        isActive = false;
    }

    @Override
    public boolean isActive() {
        return isActive;
    }

    @Override
    public void setIsActive(boolean value) {
        synchronized (this) {
            isActive = value;
        }
    }
    
    @Override
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
        
//        for (int i = 0; i < 10; i++) {
//            try {
//                TimeUnit.SECONDS.sleep(i);
//            } catch (InterruptedException ex) {
//                java.util.logging.Logger.getLogger(SimpleTaskImpl.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
        
        while (isActive) {
            try {
                robot.login();
                robot.openMail();
                robot.openGames();
                robot.selectTriviaGame();
                if (robot.startTriviaGame()) {
                    robot.playTriviaGame();
                }
                sleepTimeInSeconds = robot.getSleepTime();
            } catch (Exception e) {
                //window.appendToPane("ERROR\n>>>>\n" + ExceptionUtil.getFormattedDescription(e) + "\n<<<<\n");
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
                        int time = 120 + sleepTimeInSeconds;
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
