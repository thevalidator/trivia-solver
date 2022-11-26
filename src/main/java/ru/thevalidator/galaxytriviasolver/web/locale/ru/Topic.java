/*
 * Copyright (C) 2022 thevalidator
 */
package ru.thevalidator.galaxytriviasolver.web.locale.ru;

import ru.thevalidator.galaxytriviasolver.web.AbstractTopic;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class Topic extends AbstractTopic {

    @Override
    protected String[] initTopics() {
        return new String[]{
            "СЛУЧАЙНАЯ",
            "АВТОМОБИЛИ",
            "ВОКРУГ СВЕТА",
            "ИСТОРИЯ",
            "КИНОЗВЕЗДЫ",
            "ЛОГОТИПЫ",
            "МАТЕМАТИКА",
            "МУЗЫКА",
            "МУЛЬТФИЛЬМЫ",
            "ОБЩИЕ ЗНАНИЯ",
            "СЕРИАЛЫ",
            "СПОРТ",
            "ФИЛЬМЫ",
            "ФЛАГИ",
            "ФУТБОЛ"
        };
    }

}
