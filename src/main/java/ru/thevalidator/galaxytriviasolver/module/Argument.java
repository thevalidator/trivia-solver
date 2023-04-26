/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.galaxytriviasolver.module;

import com.beust.jcommander.Parameter;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class Argument {
    
    @Parameter(names = { "-o", "--origin" }, description = "Remote allow origins option for chrome driver")
    private boolean hasRemoteAllowOriginsOption;

    @Parameter(names = { "-d", "--debug" }, description = "Debug mode ON")
    private boolean hasDebugOption;

    @Parameter(names = { "-h", "--headless" }, description = "Headless mode settings available")
    private boolean hasHeadlessModeOption;
    
    @Parameter(names = { "-r", "--rides" }, description = "Rides play settings available")
    private boolean hasPlayRidesOption;
    
    @Parameter(names = { "-l", "--logoff" }, description = "Loggin off on exit")
    private boolean hasLogOffOption;
    
    @Parameter(names = { "-a", "--advanced" }, description = "Advanced settings available")
    private boolean hasAdvancedSettingsOption;
    
    @Parameter(names = { "-m", "--mail" }, description = "Check mail ON")
    private boolean hasCheckMailOption;
    
    @Parameter(names = { "-w", "--webdriver" }, description = "Custom chrome webdriver path")
    private String webdriverCustomPath;

    public boolean isHasRemoteAllowOriginsOption() {
        return hasRemoteAllowOriginsOption;
    }

    public boolean hasDebugOption() {
        return hasDebugOption;
    }

    public boolean hasHeadlessModeOption() {
        return hasHeadlessModeOption;
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

    public String getWebdriverCustomPath() {
        return webdriverCustomPath;
    }
    
    
}
