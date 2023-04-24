/*
 * Copyright (C) 2022 thevalidator
 */
package ru.thevalidator.galaxytriviasolver.module.trivia;

import ru.thevalidator.galaxytriviasolver.account.User;
import ru.thevalidator.galaxytriviasolver.web.Locale;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class State {

    private final Statistic statistic;
    private int topicIndex;
    private Locale locale;
    private User user;
    private volatile boolean isHeadless;
    private boolean isAnonymous;
    private boolean shouldStayInTop;
    private boolean shouldGetOnTop;
    private boolean shouldPlayRides;
    private boolean isPassive;
    private int nosDelayTime = 5_200;
    private boolean isManualStrategy;
    private int unlimStrategyTime = 0;

    public State() {
        statistic = new Statistic();
    }

    public boolean isPassive() {
        return isPassive;
    }

    public void setIsPassive(boolean isPassive) {
        this.isPassive = isPassive;
    }

    public int getNosDelayTime() {
        return nosDelayTime;
    }

    public void setNosDelayTime(int nosDelayTime) {
        this.nosDelayTime = nosDelayTime;
    }

    public boolean shouldPlayRides() {
        return shouldPlayRides;
    }

    public void setShouldPlayRides(boolean shouldPlayRides) {
        this.shouldPlayRides = shouldPlayRides;
    }

    public boolean shouldGetOnTop() {
        return shouldGetOnTop;
    }

    public void setShouldGetOnTop(boolean shouldGetOnTop) {
        this.shouldGetOnTop = shouldGetOnTop;
    }

    public boolean shouldStayInTop() {
        return shouldStayInTop;
    }

    public void setShouldStayInTop(boolean shouldStayInTop) {
        this.shouldStayInTop = shouldStayInTop;
    }

    public boolean isHeadless() {
        return isHeadless;
    }

    public void setIsHeadless(boolean isHeadless) {
        this.isHeadless = isHeadless;
    }

    public boolean isAnonymous() {
        return isAnonymous;
    }

    public void setIsAnonymous(boolean isAnonymous) {
        this.isAnonymous = isAnonymous;
    }

//    public Statistic getStatistic() {
//        return statistic;
//    }

    public Locale getLocale() {
        return locale;
    }

    public int getTopicIndex() {
        return topicIndex;
    }

    public void setTopicIndex(int topicIndex) {
        this.topicIndex = topicIndex;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public void incrementWin() {
        statistic.winCount++;
    }

    public void incrementDraw() {
        statistic.drawCount++;
    }

    public void incrementLost() {
        statistic.lostCount++;
    }

    public void addPoints(int amount) {
        statistic.pointCount += amount;
    }

    public int getWinCount() {
        return statistic.winCount;
    }

    public int getDrawCount() {
        return statistic.drawCount;
    }

    public int getLostCount() {
        return statistic.lostCount;
    }

    public int getTotalGamesPlayed() {
        return (statistic.winCount + statistic.lostCount + statistic.drawCount);
    }

    public int getAveragePoints() {
        return (statistic.pointCount / getTotalGamesPlayed());
    }

    public boolean isManualStrategy() {
        return isManualStrategy;
    }

    public void setIsManualStrategy(boolean isManualStrategy) {
        this.isManualStrategy = isManualStrategy;
    }

    public int getUnlimStrategyTime() {
        return unlimStrategyTime;
    }

    public void setUnlimStrategyTime(int unlimStrategyTime) {
        this.unlimStrategyTime = unlimStrategyTime;
    }

    class Statistic {

        private int winCount;
        private int drawCount;
        private int lostCount;
        private int pointCount;

        public Statistic() {
            this.winCount = 0;
            this.drawCount = 0;
            this.lostCount = 0;
            this.pointCount = 0;
        }

    }

}
