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

    private Statistic statistic;
    private int topicIndex;
    private Locale locale;
    private User user;
    private boolean isHeadless;
    private boolean isAnonymous;

    public State() {
        statistic = new Statistic();
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

    public Statistic getStatistic() {
        return statistic;
    }

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

        public int getTotalGames() {
            return (statistic.winCount + statistic.lostCount + statistic.drawCount);
        }

        public int getAveragePoints() {
            return (statistic.pointCount / getTotalGames());
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