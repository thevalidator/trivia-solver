/*
 * Copyright (C) 2022 thevalidator
 */
package ru.thevalidator.galaxytriviasolver.module.trivia;

/**
 *
 * @author thevalidator <the.validator@yandex.ru>
 */
public enum Unlim {
    
    MIN(1, 0.49),
    MID(2, 1.19),
    MAX(3, 1.49);
    
    private final int id;
    private final double price;

    private Unlim(int id, double price) {
        this.id = id;
        this.price = price;
    }

    public double getPrice() {
        return price;
    }
    
}
