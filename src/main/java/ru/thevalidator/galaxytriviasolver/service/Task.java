/*
 * Copyright (C) 2022 thevalidator
 */
package ru.thevalidator.galaxytriviasolver.service;

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
        try {
            window.setStartButtonStatus(1);
            GalaxyBaseRobot robot = new GalaxyBaseRobotImpl(state);
            ((GalaxyBaseRobotImpl) robot).registerObserver(window);

            robot.openURL();
            
            ((GalaxyBaseRobotImpl) robot).unregisterObserver(window);
            robot.terminate();
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            isActive = false;
            window.setStartButtonStatus(-1);
        }
    }

}
