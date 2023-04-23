/*
 * Copyright (C) 2023 thevalidator
 */

package ru.thevalidator.galaxytriviasolver.module;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class Option {
    
    private final boolean isHeadlessModeAvailable;
    //private final boolean 

    public Option(boolean isHeadlessModeAvailable) {
        this.isHeadlessModeAvailable = isHeadlessModeAvailable;
    }

    public boolean isIsHeadlessModeAvailable() {
        return isHeadlessModeAvailable;
    }

}
