/*
 * Copyright (C) 2022 thevalidator
 */

package ru.thevalidator.galaxytriviasolver.module.trivia.solver;

import ru.thevalidator.galaxytriviasolver.module.trivia.solver.entity.Answer;
import ru.thevalidator.galaxytriviasolver.module.trivia.solver.entity.Question;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public interface Solver {
    
    Answer getCorrectAnswer(Question question);

}
