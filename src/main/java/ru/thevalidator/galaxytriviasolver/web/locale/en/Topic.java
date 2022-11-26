/*
 * Copyright (C) 2022 thevalidator
 */
package ru.thevalidator.galaxytriviasolver.web.locale.en;

import ru.thevalidator.galaxytriviasolver.web.AbstractTopic;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class Topic extends AbstractTopic {

    @Override
    protected String[] initTopics() {
        return new String[]{
            "Random",
            "Logos",
            "Music",
            "Movie Stars",
            "TV Shows",
            "Comic Book Characters",
            "Soccer",
            "Anime Characters",
            "Cars",
            "Flags",
            "World Currencies"
        };
    }

}
