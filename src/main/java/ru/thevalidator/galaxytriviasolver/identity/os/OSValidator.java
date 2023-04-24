/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.galaxytriviasolver.identity.os;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class OSValidator {

    public static final String OS_NAME = System.getProperty("os.name").toLowerCase();
    
    public static final boolean IS_WINDOWS = (OS_NAME.contains("win"));
    public static final boolean IS_MAC = (OS_NAME.contains("mac"));
    public static final boolean IS_UNIX = (OS_NAME.contains("nix") || OS_NAME.contains("nux") || OS_NAME.contains("aix"));
    public static final boolean IS_SOLARIS = (OS_NAME.contains("sunos"));

}
