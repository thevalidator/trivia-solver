/*
 * Copyright (C) 2022 thevalidator
 */

package ru.thevalidator.galaxytriviasolver.solver;

import ru.thevalidator.galaxytriviasolver.entity.Answer;
import ru.thevalidator.galaxytriviasolver.entity.Question;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public interface Solver {
    
    Answer getCorrectAnswer(Question question);

}
