/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.galaxytriviasolver.util.identity.os;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class OSValidator {

    public static final String OS_NAME = System.getProperty("os.name");
    private static final String OS_LOWER_CASE = OS_NAME.toLowerCase();
    
    public static final boolean IS_WINDOWS = (OS_LOWER_CASE.contains("win"));
    public static final boolean IS_MAC = (OS_LOWER_CASE.contains("mac"));
    public static final boolean IS_UNIX = (OS_LOWER_CASE.contains("nix") || OS_NAME.contains("nux") || OS_NAME.contains("aix"));
    public static final boolean IS_SOLARIS = (OS_LOWER_CASE.contains("sunos"));

}
