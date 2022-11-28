/*
 * Copyright (C) 2022 thevalidator
 */
package ru.thevalidator.galaxytriviasolver.communication;

import java.util.HashSet;
import java.util.Set;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public abstract class Informer {

    private final Object MONITOR = new Object();
    private Set<Observer> observers;

    public void registerObserver(Observer observer) {
        if (observer == null) {
            return;
        }
        synchronized (MONITOR) {
            if (observers == null) {
                observers = new HashSet<>(1);
            }
            observers.add(observer);
//            if (observers.add(observer) && observers.size() == 1) {
//                //performInit(); // some initialization when first observer added
//            }
        }
    }

    public void unregisterObserver(Observer observer) {
        if (observer == null) {
            return;
        }
        synchronized (MONITOR) {
            observers.remove(observer);
//            if (observers != null && observers.remove(observer) && observers.isEmpty()) {
//                //performCleanup(); // some cleanup when last observer removed
//            }
        }
    }

    public void informObservers(String message) {
        Set<Observer> observersCopy;
        synchronized (MONITOR) {
            if (observers == null) {
                return;
            }
            observersCopy = new HashSet<>(observers);
        }
        for (Observer observer : observersCopy) {
            observer.onUpdateRecieve(message);
        }
    }
}
