/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.galaxytriviasolver.options;

import com.beust.jcommander.Parameter;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class ChromeDriverArgument {

    @Parameter(names = {"-h", "--headless"}, arity = 1, description = "Headless mode settings available")
    private boolean hasHeadlessModeOption = true;

    @Parameter(names = {"-o", "--origin"}, description = "Remote allow origins option for chrome driver")
    private boolean hasRemoteAllowOriginsOption;

    @Parameter(names = {"-w", "--webdriver"}, description = "Custom chrome webdriver path")
    private String webdriverCustomPath;

    public boolean isHasHeadlessModeOption() {
        return hasHeadlessModeOption;
    }

    public boolean isHasRemoteAllowOriginsOption() {
        return hasRemoteAllowOriginsOption;
    }

    public String getWebdriverCustomPath() {
        return webdriverCustomPath;
    }    

}
