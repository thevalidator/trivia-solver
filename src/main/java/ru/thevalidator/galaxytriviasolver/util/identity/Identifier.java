/*
 * Copyright (C) 2022 thevalidator
 */
package ru.thevalidator.galaxytriviasolver.util.identity;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class Identifier {
    
    public static final String ERROR_KEY = "NO KEY";
    
    public static String readKey() {
        Path path = Paths.get("user.key");
        try {
            String key = Files.readAllLines(path).get(0);
            return key;
        } catch (IOException ex) {
            return ERROR_KEY;
        }
    }
    
}
