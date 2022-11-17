/*
 * Copyright (C) 2022 thevalidator
 */

package ru.thevalidator.galaxytriviasolver.web;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public abstract class AbstractTopic {
    
    private String[] topics;

    public AbstractTopic() {
        topics = initTopics();
    }

    public String[] getTopics() {
        return topics;
    }
    
    public boolean isExist(String name) {
        for (String topic: topics) {
            if (name.equalsIgnoreCase(topic)) {
                return true;
            }
        }
        return false;
    }
    
    protected abstract String[] initTopics();

}
