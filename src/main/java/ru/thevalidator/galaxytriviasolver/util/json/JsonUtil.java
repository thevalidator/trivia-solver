/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.galaxytriviasolver.util.json;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class JsonUtil {

   private static class StaticHolder {
        static final ObjectMapper INSTANCE = new ObjectMapper();
    }
 
    public static ObjectMapper getMapper() {
        return StaticHolder.INSTANCE;
    }

}
