/*
 * Copyright (C) 2022 thevalidator
 */

package ru.thevalidator.galaxytriviasolver.service;

import ru.thevalidator.galaxytriviasolver.communication.Informer;
import ru.thevalidator.galaxytriviasolver.module.trivia.State;


/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class Task extends Informer implements Runnable {
    
    private boolean isActive;
    private final State state;

    public Task(State state) {
        this.state = state;
        this.isActive = false;
    }

    public boolean isIsActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }
    
    @Override
    public void run() {
        informObservers("START TASK");
        
        System.out.println(state.getUser().getName() + " "  + state.getUser().getCode() + " " + state.getTopicIndex());
        isActive = false;
        
        informObservers("STOP TASK");
    }

    public boolean isActive() {
        return isActive;
    }

}
