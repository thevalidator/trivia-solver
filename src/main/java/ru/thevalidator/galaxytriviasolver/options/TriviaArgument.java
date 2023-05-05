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
    
    @Parameter(names = { "-u", "--utility" }, description = "Show in tray only")
    private boolean isUtilityMode;

    public boolean hasDebugOption() {
        return hasDebugOption;
    }

    public boolean isUtilityMode() {
        return isUtilityMode;
    }
    
}
