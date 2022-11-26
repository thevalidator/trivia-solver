/*
 * Copyright (C) 2022 thevalidator
 */
package ru.thevalidator.galaxytriviasolver.web.locale.pt;

import ru.thevalidator.galaxytriviasolver.web.AbstractTopic;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class Topic extends AbstractTopic {

    @Override
    protected String[] initTopics() {
        return new String[]{
            "Aleatória",
            "Logos",
            "Clubes de futebol",
            "Personagens de Quadrinhos",
            "Estrelas do cinema",
            "Personagens de anime",
            "Carros"
        };
    }

}
