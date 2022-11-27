/*
 * Copyright (C) 2022 thevalidator
 */

package ru.thevalidator.galaxytriviasolver.model;

import java.awt.Color;
import ru.thevalidator.galaxytriviasolver.module.trivia.GameResult;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public interface Observer {
    
    void handleStart();
    void handleStop();
    void handleUnlimShopping();
    void handleMessage(String message);
    void handleGameResult(GameResult result, int points);

}
