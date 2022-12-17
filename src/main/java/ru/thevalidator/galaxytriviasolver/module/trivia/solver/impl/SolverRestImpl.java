/*
 * Copyright (C) 2022 thevalidator
 */
package ru.thevalidator.galaxytriviasolver.module.trivia.solver.impl;

import ru.thevalidator.galaxytriviasolver.module.trivia.solver.Solver;
import ru.thevalidator.galaxytriviasolver.module.trivia.solver.entity.trivia.Answer;
import ru.thevalidator.galaxytriviasolver.module.trivia.solver.entity.trivia.Question;
import ru.thevalidator.galaxytriviasolver.remote.Connector;

public class SolverRestImpl implements Solver {
    
    private final Connector connector;

    public SolverRestImpl(Connector connector) {
        this.connector = connector;
    }

    @Override
    public Answer getCorrectAnswer(Question question) {
        
        int index = 0;
        try {            
            index = connector.getCorrectAnswerIndex(question);
        } catch (Exception e) {
            e.printStackTrace();
        }

        Answer correct = question.getAnswers()[index];
        return correct;
    }

}
