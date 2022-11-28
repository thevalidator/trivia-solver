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
    private static final int MAX_ACCOUNTS = 20;
    private User[] users;

    public UserStorage(List<User> users) {
        if (users.size() > MAX_ACCOUNTS) {
            throw new IllegalArgumentException("allowed " + MAX_ACCOUNTS + " maximum, found more");
        }
        this.users = users.toArray(User[]::new);
    }
    
    public String[] getUserNames() {
        String[] names = new String[users.length];
        for (int i = 0; i < users.length; i++) {
            names[i] = users[i].getName();
        }
        return names;
    }
    
    public User getUser(int index) {
        return users[index];
    }

}
