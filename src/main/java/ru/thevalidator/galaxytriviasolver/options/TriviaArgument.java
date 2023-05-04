/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.galaxytriviasolver.options;

import com.beust.jcommander.Parameter;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class TriviaArgument {

    @Parameter(names = { "-d", "--debug" }, description = "Debug mode ON")
    private boolean hasDebugOption;
    
    @Parameter(names = { "-r", "--rides" }, description = "Rides play settings available")
    private boolean hasPlayRidesOption;
    
    @Parameter(names = { "-l", "--logoff" }, description = "Loggin off on exit")
    private boolean hasLogOffOption;
    
    @Parameter(names = { "-a", "--advanced" }, description = "Advanced settings available")
    private boolean hasAdvancedSettingsOption;
    
    @Parameter(names = { "-m", "--mail" }, description = "Check mail ON")
    private boolean hasCheckMailOption;
    
    @Parameter(names = { "-i", "--imitation" }, description = "Human imitation ON")
    private boolean hasHumanImitation;
    
    @Parameter(names = { "-u", "--utility" }, description = "Show in tray only")
    private boolean isUtilityMode;

    public boolean hasDebugOption() {
        return hasDebugOption;
    }

    public boolean hasPlayRidesOption() {
        return hasPlayRidesOption;
    }

    public boolean hasLogOffOption() {
        return hasLogOffOption;
    }

    public boolean hasAdvancedSettingsOption() {
        return hasAdvancedSettingsOption;
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
