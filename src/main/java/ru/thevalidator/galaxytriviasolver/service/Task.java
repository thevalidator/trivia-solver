/*
 * Copyright (C) 2022 thevalidator
 */
package ru.thevalidator.galaxytriviasolver.service;

import java.util.concurrent.TimeUnit;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
        try {

            robot.login();
            robot.openGames();
            robot.selectTriviaGame();
            robot.startTriviaGame();
            robot.playTriviaGame();
            while (isActive) {
                TimeUnit.SECONDS.sleep(5);
            }

        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            ((GalaxyBaseRobotImpl) robot).unregisterObserver(window);
            robot.terminate();
            isActive = false;
            window.setStartButtonStatus(-1);
            window.appendToPane("STOPPED");
        }
    }

}
