/*
 * Copyright (C) 2022 thevalidator
 */

package ru.thevalidator.galaxytriviasolver.exception;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class NotEnoughEnergyException extends Exception {
    
    private final int secondsToWait;

    public NotEnoughEnergyException(int secondsToWait, String message) {
        super(message);
        this.secondsToWait = secondsToWait;
    }

    public int getSecondsToWait() {
        return secondsToWait;
    }

}
