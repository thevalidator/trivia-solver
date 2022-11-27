/*
 * Copyright (C) 2022 thevalidator
 */

package ru.thevalidator.galaxytriviasolver.bot;

import ru.thevalidator.galaxytriviasolver.model.Observable;
import ru.thevalidator.galaxytriviasolver.model.Observer;
import ru.thevalidator.galaxytriviasolver.web.AbstractLocator;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public interface Bot extends Runnable, Observable {
    
    boolean isActive();
    void isHeadleass(boolean b);
    void isAnonimous(boolean b);
    void buyUnlim(int option);
    void setRecoveryCode(String recoveryCode);
    //void setLocale(AbstractLocator locator,  topics);
    void setTopic(String string);
    void addListener(Observer o);
    void removeListeners();
    void stop();
    void playRides(boolean b);
    
}
