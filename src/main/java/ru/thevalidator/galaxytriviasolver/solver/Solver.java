/*
 * Copyright (C) 2022 thevalidator
 */

package ru.thevalidator.galaxytriviasolver.solver;

import ru.thevalidator.galaxytriviasolver.solver.entity.Answer;
import ru.thevalidator.galaxytriviasolver.solver.entity.Question;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public interface Solver {
    
    Answer getCorrectAnswer(Question question);

}
