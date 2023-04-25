/*
 * Copyright (C) 2023 thevalidator
 */

package ru.thevalidator.galaxytriviasolver.util.formatter.impl;

import java.time.LocalDateTime;
import ru.thevalidator.galaxytriviasolver.util.formatter.DateTimeFormatter;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class DateTimeFormatterForLogConsole implements DateTimeFormatter {
    
    private static final java.time.format.DateTimeFormatter FORMATTER = java.time.format.DateTimeFormatter.ofPattern("dd.MM.yy HH:mm.ss");

    @Override
    public String getFormattedDateTime(LocalDateTime dt) {
        return dt.format(FORMATTER);
    }

}
