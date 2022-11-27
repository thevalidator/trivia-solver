/*
 * Copyright (C) 2022 thevalidator
 */

package ru.thevalidator.galaxytriviasolver.account;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class User {
    
    private String name;
    private String code;

    public User() {
    }

    public User(String name, String code) {
        this.name = name;
        this.code = code;
    }  

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
