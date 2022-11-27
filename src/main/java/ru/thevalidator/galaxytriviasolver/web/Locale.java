/*
 * Copyright (C) 2022 thevalidator
 */
package ru.thevalidator.galaxytriviasolver.web;

/**
 *
 * @author thevalidator <the.validator@yandex.ru>
 */
public enum Locale {
    RU, EN, PT, ES;
    
    public static Locale getDefaultLocale() {
        return EN;
    }
}
