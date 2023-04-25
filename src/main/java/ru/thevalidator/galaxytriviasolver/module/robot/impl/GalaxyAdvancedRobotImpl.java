/*
 * Copyright (C) 2023 thevalidator
 */

package ru.thevalidator.galaxytriviasolver.module.robot.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import ru.thevalidator.galaxytriviasolver.module.robot.GalaxyAdvancedRobot;
import ru.thevalidator.galaxytriviasolver.module.robot.Robot;
import ru.thevalidator.galaxytriviasolver.module.trivia.State;
import ru.thevalidator.galaxytriviasolver.module.trivia.solver.Solver;
import ru.thevalidator.galaxytriviasolver.service.Task;


public class GalaxyAdvancedRobotImpl extends Robot implements GalaxyAdvancedRobot {
    
    private static final Logger logger = LogManager.getLogger(GalaxyAdvancedRobotImpl.class);

    public GalaxyAdvancedRobotImpl(Solver solver, Task task, WebDriver driver, State state) {
        super(solver, task, driver, state);
    }

    @Override
    public void selectRidesGame() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public boolean startRidesGame() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void playRidesGame() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
