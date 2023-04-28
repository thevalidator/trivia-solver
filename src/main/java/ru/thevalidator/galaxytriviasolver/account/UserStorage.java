/*
 * Copyright (C) 2022 thevalidator
 */
package ru.thevalidator.galaxytriviasolver.account;

import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.ObjectWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.thevalidator.galaxytriviasolver.exception.ExceptionUtil;
import ru.thevalidator.galaxytriviasolver.util.json.JsonUtil;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class UserStorage {

    private static final Logger logger = LogManager.getLogger(UserStorage.class);
    public static final String STORAGE_DATE_FILE_NAME = "data" + File.separator + "users.json";
    public static final int MAX_ACCOUNTS = 20;
    private User[] users;

    public UserStorage() {
        Path path = Paths.get(STORAGE_DATE_FILE_NAME);
        if (Files.exists(path)) {
            users = readUserData().toArray(User[]::new);
        } else {
            users = new User[0];
            try {
                //Files.createFile(file);
                File f = new File(STORAGE_DATE_FILE_NAME);
                f.getParentFile().mkdirs();
                f.createNewFile();
            } catch (IOException ex) {
                //ex.printStackTrace();
                logger.error(ExceptionUtil.getFormattedDescription(ex));
            }
        }
    }

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

    public ArrayList<User> getUsers() {
        return new ArrayList<>(Arrays.asList(users));
    }

    public static List<User> readUserData() {
        List<User> users = null;
        try {
            File file = Paths.get(UserStorage.STORAGE_DATE_FILE_NAME).toFile();
            users = Arrays.asList(JsonUtil.getMapper().readValue(file, User[].class));
        } catch (IOException e) {
            logger.error(ExceptionUtil.getFormattedDescription(e));
        }
        return users;
    }

    public static void writeUserData(List<User> users) {
        try {
            if (users.size() > 20) {
                throw new IllegalArgumentException("ERROR: NOT ADDED! MAXIMUM 20 PERSONS ALLOWED");
            }
            ObjectWriter writer = JsonUtil.getMapper().writer(new DefaultPrettyPrinter());
            writer.writeValue(Paths.get(UserStorage.STORAGE_DATE_FILE_NAME).toFile(), users);
        } catch (IOException e) {
            logger.error(ExceptionUtil.getFormattedDescription(e));
        }
    }

}
