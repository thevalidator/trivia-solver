/*
 * Copyright (C) 2022 thevalidator
 */
package ru.thevalidator.galaxytriviasolver.module.trivia;

/**
 *
 * @author thevalidator <the.validator@yandex.ru>
 */
public enum Unlim {
    
    MIN(1, 0.49, 0.5),
    MID(2, 1.19, 2),
    MAX(3, 1.49, 4);
    
    private final int id;
    private final double price;
    private final double hours;

    private Unlim(int id, double price, double hours) {
        this.id = id;
        this.price = price;
        this.hours = hours;
    }

    public double getPrice() {
        return price;
    }

    public int getId() {
        return id;
    }

    public double getHours() {
        return hours;
    }
    
}
