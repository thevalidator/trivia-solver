/*
 * Copyright (C) 2022 thevalidator
 */

package ru.thevalidator.galaxytriviasolver.module.trivia.solver.entity.trivia;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class Answer {
    
    private final String text;
    private final String rel;
    private final int index;

    public Answer(String text, String rel, int index) {
        this.text = text;
        this.rel = rel;
        this.index = index;
    }

    public String getText() {
        return text;
    }

    public String getRel() {
        return rel;
    }

    public int getIndex() {
        return index;
    }

}
