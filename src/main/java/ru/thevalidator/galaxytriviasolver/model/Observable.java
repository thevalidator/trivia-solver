/*
 * Copyright (C) 2022 thevalidator
 */

package ru.thevalidator.galaxytriviasolver.model;

import java.awt.Color;
import ru.thevalidator.galaxytriviasolver.solver.GameResult;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public interface Observable {
    
    void registerObserver(Observer o);
    void unregisterObserver(Observer o);

    void startNotify();
    void stopNotify();
    void shoppingNotify();
    void messageNotify(String message);
    void gameResultNotify(GameResult result, int points);
    
}
