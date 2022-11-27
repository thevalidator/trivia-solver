/*
 * Copyright (C) 2022 thevalidator
 */

package ru.thevalidator.galaxytriviasolver.account;

import java.util.List;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class UserStorage {
    
    public static final String STORAGE_DATE_FILE_NAME = "users.json";
    private User[] users;

    public UserStorage(List<User> users) {
        this.users = users.toArray(User[]::new);
    }
    
    public String[] getUserNames() {
        String[] names = new String[users.length];
        for (int i = 0; i < users.length; i++) {
            names[i] = users[i].getName();
        }
        return names;
    }
    
    

}
