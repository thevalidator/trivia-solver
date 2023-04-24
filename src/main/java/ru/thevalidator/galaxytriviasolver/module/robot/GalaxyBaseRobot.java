/*
 * Copyright (C) 2022 thevalidator
 */

package ru.thevalidator.galaxytriviasolver.module.robot;

import ru.thevalidator.galaxytriviasolver.exception.LoginErrorException;
import ru.thevalidator.galaxytriviasolver.module.trivia.TriviaRobot;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public interface GalaxyBaseRobot extends TriviaRobot {
    
    void login() throws LoginErrorException;
    void openMail();
    void openGames();
    void logoff();
    void terminate();
    void refreshPage();
    void switchToDefaultContent();

}
