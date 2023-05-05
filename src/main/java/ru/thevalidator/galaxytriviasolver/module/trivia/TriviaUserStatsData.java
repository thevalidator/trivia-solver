/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.galaxytriviasolver.module.trivia;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class TriviaUserStatsData {

    public static final int START_TIME_IN_SECONDS = 28_800;
    public static final int ONE_DAY_TIME_IN_SECONDS = 86_400;
    public static final int AVERAGE_POINTS_PER_HOUR = 7_000;

    private int userDailyPoints;
    private double userCoins;
    private int tenthPlacePoints;
    private int secondPlacePoints;
    private int firstPlacePoints;

    public TriviaUserStatsData() {
        this.userCoins = 0;
        this.userDailyPoints = 0;
        this.tenthPlacePoints = 0;
        this.secondPlacePoints = 0;
        this.firstPlacePoints = 0;
    }

    public boolean isUnlimAvailable(Unlim type) {
        return (userCoins - type.getPrice()) > 0;
    }

    public int getUserDailyPoints() {
        return userDailyPoints;
    }

    public void setUserDailyPoints(int userPoints) {
        this.userDailyPoints = userPoints;
    }

    public double getUserCoins() {
        return userCoins;
    }

    public void setUserCoins(double userCoins) {
        this.userCoins = userCoins;
    }

    public int getTenthPlacePoints() {
        return tenthPlacePoints;
    }

    public void setTenthPlacePoints(int tenthPlacePoints) {
        this.tenthPlacePoints = tenthPlacePoints;
    }

    public int getFirstPlacePoints() {
        return firstPlacePoints;
    }

    public void setFirstPlacePoints(int firstPlacePoints) {
        this.firstPlacePoints = firstPlacePoints;
    }

    public int getSecondPlacePoints() {
        return secondPlacePoints;
    }

    public void setSecondPlacePoints(int secondPlacePoints) {
        this.secondPlacePoints = secondPlacePoints;
    }
    
    public int getDiffWithLast() {
        return this.tenthPlacePoints - this.userDailyPoints;
    }

}
