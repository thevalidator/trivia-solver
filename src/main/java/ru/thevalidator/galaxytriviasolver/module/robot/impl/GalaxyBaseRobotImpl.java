package ru.thevalidator.galaxytriviasolver.module.robot.impl;

import org.openqa.selenium.WebDriver;
import ru.thevalidator.galaxytriviasolver.module.robot.Robot;
import ru.thevalidator.galaxytriviasolver.module.trivia.State;
import ru.thevalidator.galaxytriviasolver.module.trivia.solver.Solver;
import ru.thevalidator.galaxytriviasolver.service.Task;

public class GalaxyBaseRobotImpl extends Robot {

    public GalaxyBaseRobotImpl(Solver solver, Task task, WebDriver driver, State state) {
        super(solver, task, driver, state);
    }

}