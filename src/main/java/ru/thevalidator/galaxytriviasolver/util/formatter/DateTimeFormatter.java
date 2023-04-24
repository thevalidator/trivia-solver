/*
 * Copyright (C) 2023 thevalidator
 */

package ru.thevalidator.galaxytriviasolver.util.formatter;

import java.time.LocalDateTime;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public interface DateTimeFormatter {

    String getFormattedDateTime(LocalDateTime dt);
    
}
