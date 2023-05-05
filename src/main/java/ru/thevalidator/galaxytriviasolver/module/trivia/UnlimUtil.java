/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.galaxytriviasolver.module.trivia;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class UnlimUtil {
    
    private static final int MAX_UNLIM_MINUTES = (int) (Unlim.MAX.getHours() * 60);
    private static final int MID_UNLIM_MINUTES = (int) (Unlim.MID.getHours() * 60);
    private static final int MIN_UNLIM_MINUTES = (int) (Unlim.MIN.getHours() * 60);
    public static final int AVERAGE_POINTS_PER_ROUND = 135;
    
    public static int getUnlimMinutesValue (Unlim type) {
        return (int) (switch (type) {
            case MAX -> MAX_UNLIM_MINUTES;
            case MID -> MID_UNLIM_MINUTES;
            default -> MIN_UNLIM_MINUTES;
        });
    }

    public static double getPrice(int unlimTime) {

        double price = 0.;

        int maxUnlimCount = unlimTime / MAX_UNLIM_MINUTES;
        if (maxUnlimCount > 0) {
            price += Unlim.MAX.getPrice() * maxUnlimCount;
        }

        unlimTime = unlimTime % MAX_UNLIM_MINUTES;
        int midUnlimCount = unlimTime / MID_UNLIM_MINUTES;
        if (midUnlimCount > 0) {
            price += Unlim.MID.getPrice() * midUnlimCount;
        }

        unlimTime = unlimTime % MID_UNLIM_MINUTES;
        int minUnlimCount = unlimTime / MIN_UNLIM_MINUTES;
        if (minUnlimCount > 0) {
            price += Unlim.MIN.getPrice() * minUnlimCount;
        }

        return Math.ceil(price * 100) / 100;

    }
    
    public static boolean isUnlimAvailable(double userCoins, int unlimTime) {
            double price = getPrice(unlimTime);
            return userCoins >= price;
        }

    public static int getApproxPoints(int totalMinutes) {
        return AVERAGE_POINTS_PER_ROUND * totalMinutes;
    }

}
