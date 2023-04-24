/*
 * Copyright (C) 2023 thevalidator
 */

package ru.thevalidator.galaxytriviasolver.service;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public interface Task extends Runnable {
    
    boolean isActive();
    void setIsActive(boolean b);
    void hardStopAction();

}
