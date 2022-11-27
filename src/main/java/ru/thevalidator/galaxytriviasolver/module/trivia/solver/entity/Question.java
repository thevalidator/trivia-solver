/*
 * Copyright (C) 2022 thevalidator
 */

package ru.thevalidator.galaxytriviasolver.module.trivia.solver.entity;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class Question {
    
    private final String question;
    private final Answer[] answers;

    public Question(String question, Answer[] answers) {
        this.question = question;
        this.answers = answers;
    }

    public String getQuestion() {
        return question;
    }

    public Answer[] getAnswers() {
        return answers;
    }

}
