/*
 * Copyright (C) 2022 thevalidator
 */
package ru.thevalidator.galaxytriviasolver.web.locale.es;

import ru.thevalidator.galaxytriviasolver.web.AbstractTopic;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class Topic extends AbstractTopic {

    @Override
    protected String[] initTopics() {
        return new String[] {
            "ALEATORIO",
            "Logos",
            "Clubes de fútbol",
            "Personajes de cómic",
            "ESTRELLAS DE CINE",
            "PERSONAJES DE ANIME",
            "COCHES"
        };
    }

}
