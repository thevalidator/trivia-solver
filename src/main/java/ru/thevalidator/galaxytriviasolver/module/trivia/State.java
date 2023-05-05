/*
 * Copyright (C) 2022 thevalidator
 */
package ru.thevalidator.galaxytriviasolver.module.trivia;

import java.nio.file.Path;
import java.nio.file.Paths;
import ru.thevalidator.galaxytriviasolver.account.User;
import ru.thevalidator.galaxytriviasolver.options.ChromeDriverArgument;
import ru.thevalidator.galaxytriviasolver.options.TriviaArgument;
import ru.thevalidator.galaxytriviasolver.web.Locale;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class State {

    private TriviaArgument triviaArgs;
    private ChromeDriverArgument chromeArgs;
    private int topicIndex;
    private Locale locale;
    private User user;
    private boolean isAnonymous;
    private boolean shouldStayInTop;
    private boolean shouldGetOnTop;
    private boolean shouldPlayRides;
    private boolean isPassive;
    private boolean isManualStrategy;
    private boolean isMaxUnlimOnly;
    private boolean hasPointsDelta;
    private boolean hasHumanImitation;
    private int pointsDelta = 10_000;
    private int nosDelayTime = 5_200;
    private int unlimStrategyTime = 0;

    public TriviaArgument getTriviaArgs() {
        return triviaArgs;
    }

    public void setTriviaArgs(TriviaArgument triviaArgs) {
        this.triviaArgs = triviaArgs;
    }

    public ChromeDriverArgument getChromeArgs() {
        return chromeArgs;
    }

    public void setChromeArgs(ChromeDriverArgument chromeArgs) {
        if (chromeArgs.getWebdriverCustomPath() != null && !chromeArgs.getWebdriverCustomPath().isEmpty()) {
            Path p = Paths.get("data/driver/" + chromeArgs.getWebdriverCustomPath());
            Path absPath = p.normalize().toAbsolutePath();
            System.setProperty("webdriver.chrome.driver", absPath.toString());
        }
        this.chromeArgs = chromeArgs;
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

    public boolean isAnonymous() {
        return isAnonymous;
    }

    public void setIsAnonymous(boolean isAnonymous) {
        this.isAnonymous = isAnonymous;
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

    public boolean isMaxUnlimOnly() {
        return isMaxUnlimOnly;
    }

    public void setIsMaxUnlimOnly(boolean isMaxUnlimOnly) {
        this.isMaxUnlimOnly = isMaxUnlimOnly;
    }

    public boolean hasPointsDelta() {
        return hasPointsDelta;
    }

    public void setHasPointsDelta(boolean hasPointsDelta) {
        this.hasPointsDelta = hasPointsDelta;
    }

    public int getPointsDelta() {
        return pointsDelta;
    }

    public void setPointsDelta(int pointsDelta) {
        this.pointsDelta = pointsDelta;
    }

    public boolean hasHumanImitation() {
        return hasHumanImitation;
    }

    public void setHasHumanImitation(boolean hasHumanImitation) {
        this.hasHumanImitation = hasHumanImitation;
    }

    @Override
    public String toString() {
        return "State{" 
                + "\ntriviaArgs=" + triviaArgs 
                + ", \nchromeArgs=" + chromeArgs 
                + ", \ntopicIndex=" + topicIndex 
                + ", \nlocale=" + locale 
                + ", \nuser=" + user 
                + ", \nisAnonymous=" + isAnonymous 
                + ", \nshouldStayInTop=" + shouldStayInTop 
                + ", \nshouldGetOnTop=" + shouldGetOnTop 
                + ", \nshouldPlayRides=" + shouldPlayRides 
                + ", \nisPassive=" + isPassive 
                + ", \nisManualStrategy=" + isManualStrategy 
                + ", \nnosDelayTime=" + nosDelayTime 
                + ", \nisMaxUnlimOnly=" + isMaxUnlimOnly 
                + ", \nunlimStrategyTime=" + unlimStrategyTime + '}';
    }

}
