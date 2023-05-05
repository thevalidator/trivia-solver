/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.galaxytriviasolver.gui.statistic;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class Statistic {

    private int winCount;
    private int drawCount;
    private int lostCount;
    private int pointCount;
    private static final int INIT_VALUE = 0;

    public Statistic() {
        this.winCount = INIT_VALUE;
        this.drawCount = INIT_VALUE;
        this.lostCount = INIT_VALUE;
        this.pointCount = INIT_VALUE;
    }

    public void incrementWin() {
        winCount++;
    }

    public void incrementDraw() {
        drawCount++;
    }

    public void incrementLost() {
        lostCount++;
    }

    public void addPoints(int amount) {
        pointCount += amount;
    }

    public int getWinCount() {
        return winCount;
    }

    public int getDrawCount() {
        return drawCount;
    }

    public int getLostCount() {
        return lostCount;
    }

    public int getTotalGamesPlayed() {
        return (winCount + lostCount + drawCount);
    }

    public int getAveragePoints() {
        return getTotalGamesPlayed() == 0 ? 0 : (pointCount / getTotalGamesPlayed());
    }

}
