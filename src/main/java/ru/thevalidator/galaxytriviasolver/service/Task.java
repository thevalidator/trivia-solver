/*
 * Copyright (C) 2022 thevalidator
 */

package ru.thevalidator.galaxytriviasolver.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.thevalidator.galaxytriviasolver.communication.Informer;
import ru.thevalidator.galaxytriviasolver.module.base.GalaxyBaseRobot;
import ru.thevalidator.galaxytriviasolver.module.base.impl.GalaxyBaseRobotImpl;
import ru.thevalidator.galaxytriviasolver.module.trivia.State;


/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class Task extends Informer implements Runnable {
    
    private static final Logger logger = LogManager.getLogger(Task.class);
    private boolean isActive;
    private final State state;

    public Task(State state) {
        this.state = state;
        this.isActive = false;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
    
    @Override
    public void run() {
        informObservers("STARTING");
        GalaxyBaseRobot robot = new GalaxyBaseRobotImpl(state);
        robot.openURL();
        informObservers(state.getUser().getName() + " "  + state.getUser().getCode() + " " + state.getTopicIndex());
        isActive = false;
        informObservers("STOPPED");
    }

    public boolean isActive() {
        return isActive;
    }

}
