/*
 * Copyright (C) 2022 thevalidator
 */
package ru.thevalidator.galaxytriviasolver.module.trivia.solver.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.thevalidator.galaxytriviasolver.exception.ExceptionUtil;
import ru.thevalidator.galaxytriviasolver.module.trivia.solver.Solver;
import ru.thevalidator.galaxytriviasolver.module.trivia.solver.entity.trivia.Answer;
import ru.thevalidator.galaxytriviasolver.module.trivia.solver.entity.trivia.Question;
import ru.thevalidator.galaxytriviasolver.remote.Connector;

public class SolverRestImpl implements Solver {
    
    private static final Logger logger = LogManager.getLogger(SolverRestImpl.class);
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
            logger.error(ExceptionUtil.getFormattedDescription(e));
        }

        Answer correct = question.getAnswers()[index];
        return correct;
    }

}
