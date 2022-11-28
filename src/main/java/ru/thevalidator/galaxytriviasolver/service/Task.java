/*
 * Copyright (C) 2022 thevalidator
 */

package ru.thevalidator.galaxytriviasolver.service;

import ru.thevalidator.galaxytriviasolver.account.User;
import ru.thevalidator.galaxytriviasolver.model.State;


/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class Task implements Runnable {
    
    private boolean isActive;
    private State state;

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
        System.out.println(state.getUser().getName() + " "  + state.getUser().getCode() + " " + state.getTopicIndex());
        System.out.println("stopped task");
        isActive = false;
    }

    public boolean isActive() {
        return isActive;
    }

}
