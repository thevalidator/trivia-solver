/*
 * Copyright (C) 2023 thevalidator
 */

package ru.thevalidator.galaxytriviasolver.exception;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class TokenNotFoundErrorException extends Exception {

    public TokenNotFoundErrorException() {
        super("No such token");
    }

}
