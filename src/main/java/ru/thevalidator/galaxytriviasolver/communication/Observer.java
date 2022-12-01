/*
 * Copyright (C) 2022 thevalidator
 */

package ru.thevalidator.galaxytriviasolver.communication;

import ru.thevalidator.galaxytriviasolver.module.trivia.GameResult;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public interface Observer {
    
    void onUpdateRecieve(String message);

    void onGameResultUpdateRecieve(GameResult result, int points);

}
