/*
 * Copyright (C) 2022 thevalidator
 */

package ru.thevalidator.galaxytriviasolver.communication;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public interface Observer {
    
    void onUpdateRecieve(String message);

}
