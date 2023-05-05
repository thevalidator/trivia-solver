/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.galaxytriviasolver.options;

import com.beust.jcommander.Parameter;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class TriviaArgument {

    @Parameter(names = { "-d", "--debug" }, description = "Debug mode")
    private boolean hasDebugOption;
    
    @Parameter(names = { "-l", "--logoff" }, description = "Loggin off on exit")
    private boolean hasLogOffOption;
    
    @Parameter(names = { "-m", "--mail" }, description = "Checking mail")
    private boolean hasCheckMailOption;
    
    @Parameter(names = { "-i", "--imitation" }, description = "Human imitation")
    private boolean hasHumanImitation;
    
    @Parameter(names = { "-u", "--utility" }, description = "Show app icon in tray only when minimized")
    private boolean isUtilityMode;

    public boolean hasDebugOption() {
        return hasDebugOption;
    }

    public boolean hasLogOffOption() {
        return hasLogOffOption;
    }

    public boolean hasCheckMailOption() {
        return hasCheckMailOption;
    }

    public boolean hasHumanImitation() {
        return hasHumanImitation;
    }

    public void setHasHumanImitation(boolean hasHumanImitation) {
        this.hasHumanImitation = hasHumanImitation;
    }

    public boolean isUtilityMode() {
        return isUtilityMode;
    }
    
}
