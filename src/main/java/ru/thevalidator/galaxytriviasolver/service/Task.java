/*
 * Copyright (C) 2023 thevalidator
 */

package ru.thevalidator.galaxytriviasolver.service;

import ru.thevalidator.galaxytriviasolver.module.trivia.State;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public interface Task extends Runnable {
    
    boolean isRunning();
    void stop();
    void interrupt();
    void setState(State state);

}
