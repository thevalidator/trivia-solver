/*
 * Copyright (C) 2022 thevalidator
 */

package ru.thevalidator.galaxytriviasolver.module.base;

import ru.thevalidator.galaxytriviasolver.module.rides.RidesRobot;
import ru.thevalidator.galaxytriviasolver.module.trivia.TriviaRobot;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public interface GalaxyBaseRobot extends TriviaRobot, RidesRobot {
    
    void openURL();
    void login();
    void openGames();

}
