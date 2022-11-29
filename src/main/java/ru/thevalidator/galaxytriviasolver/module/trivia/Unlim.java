/*
 * Copyright (C) 2022 thevalidator
 */
package ru.thevalidator.galaxytriviasolver.module.trivia;

/**
 *
 * @author thevalidator <the.validator@yandex.ru>
 */
public enum Unlim {
    MIN(0.49),
    MID(1.19),
    MAX(1.49);
    
    private final double price;

    private Unlim(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }
    
}
