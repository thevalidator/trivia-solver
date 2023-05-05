/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.galaxytriviasolver.options;

import com.beust.jcommander.Parameter;
import ru.thevalidator.galaxytriviasolver.options.webdriver.WebDriverArgument;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class ChromeDriverArgument extends WebDriverArgument {

    @Parameter(names = {"-h", "--headless"}, arity = 1, description = "Headless mode settings available")
    private boolean isHeadlessMode = true;

    @Parameter(names = {"-o", "--origin"}, description = "Remote allow origins option for chrome driver")
    private boolean hasRemoteAllowOriginsOption;

    @Parameter(names = {"-w", "--webdriver"}, description = "Custom chrome webdriver path")
    private String webdriverCustomPath;

    public boolean isHeadlessMode() {
        return isHeadlessMode;
    }

    public void setIsHeadlessMode(boolean isHeadlessMode) {
        this.isHeadlessMode = isHeadlessMode;
    }

    public boolean hasRemoteAllowOriginsOption() {
        return hasRemoteAllowOriginsOption;
    }

    public String getWebdriverCustomPath() {
        return webdriverCustomPath;
    }    

}
