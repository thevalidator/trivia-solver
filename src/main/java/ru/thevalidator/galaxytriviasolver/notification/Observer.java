/*
 * Copyright (C) 2022 thevalidator
 */

package ru.thevalidator.galaxytriviasolver.notification;

import ru.thevalidator.galaxytriviasolver.module.trivia.GameResult;
import ru.thevalidator.galaxytriviasolver.module.trivia.TriviaUserStatsData;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public interface Observer {
    
    void onUpdateRecieve(String message);

    void onGameResultUpdateRecieve(GameResult result, int points, TriviaUserStatsData data);

}
