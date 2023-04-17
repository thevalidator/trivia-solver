/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.galaxytriviasolver.module.trivia;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class UnlimUtil {

    public static double getPrice(int unlimTime) {

        int maxUnlimMinutes = (int) (Unlim.MAX.getHours() * 60);
        int midUnlimMinutes = (int) (Unlim.MID.getHours() * 60);
        int minUnlimMinutes = (int) (Unlim.MIN.getHours() * 60);

        double price = 0.;

        int maxUnlimCount = unlimTime / maxUnlimMinutes;
        if (maxUnlimCount > 0) {
            price += Unlim.MAX.getPrice() * maxUnlimCount;
        }

        unlimTime = unlimTime % maxUnlimMinutes;
        int midUnlimCount = unlimTime / midUnlimMinutes;
        if (midUnlimCount > 0) {
            price += Unlim.MID.getPrice() * midUnlimCount;
        }

        unlimTime = unlimTime % midUnlimMinutes;
        int minUnlimCount = unlimTime / minUnlimMinutes;
        if (minUnlimCount > 0) {
            price += Unlim.MIN.getPrice() * minUnlimCount;
        }

        return Math.ceil(price * 100) / 100;

    }

}
