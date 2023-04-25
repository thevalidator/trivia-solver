/*
 * Copyright (C) 2023 thevalidator
 */

package ru.thevalidator.galaxytriviasolver.util.formatter.impl;

import java.time.LocalDateTime;
import ru.thevalidator.galaxytriviasolver.util.formatter.DateTimeFormatter;


public class DateTimeFormatterForName implements DateTimeFormatter {
    
    private static final java.time.format.DateTimeFormatter FORMATTER = java.time.format.DateTimeFormatter.ofPattern("ddMMyyyy_HHmmss");

    @Override
    public String getFormattedDateTime(LocalDateTime dt) {
        return dt.format(FORMATTER);
    }

}
