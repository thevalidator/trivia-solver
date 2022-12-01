/*
 * Copyright (C) 2022 thevalidator
 */

package ru.thevalidator.galaxytriviasolver.module.trivia;

import ru.thevalidator.galaxytriviasolver.exception.CanNotPlayException;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public interface TriviaRobot {
    
    void selectTriviaGame();
    void startTriviaGame() throws CanNotPlayException;
    void playTriviaGame();
    int getSleepTime();

}
