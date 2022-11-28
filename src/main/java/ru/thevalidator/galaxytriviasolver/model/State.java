/*
 * Copyright (C) 2022 thevalidator
 */

package ru.thevalidator.galaxytriviasolver.model;

import ru.thevalidator.galaxytriviasolver.account.User;
import ru.thevalidator.galaxytriviasolver.web.Locale;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class State {
    
    private int winCount;
    private int drawCount;
    private int lostCount;
    private int pointCount;
    private int topicIndex;
    private Locale locale;
    private User user;
    
    

    public State() {
        this.winCount = 0;
        this.drawCount = 0;
        this.lostCount = 0;
        this.pointCount = 0;
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

    public int getWinCount() {
        return winCount;
    }

    public int getDrawCount() {
        return drawCount;
    }

    public int getLostCount() {
        return lostCount;
    }
    
    public int getTotalGames() {
        return winCount + lostCount + drawCount;
    }
    
    public int getAveragePoints() {
        return pointCount / getTotalGames(); 
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

}
