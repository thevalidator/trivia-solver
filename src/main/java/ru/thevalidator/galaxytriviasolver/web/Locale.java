/*
 * Copyright (C) 2022 thevalidator
 */
package ru.thevalidator.galaxytriviasolver.web;

/**
 *
 * @author thevalidator <the.validator@yandex.ru>
 */
public enum Locale {

    RU(new String[]{
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
    }),
    EN(new String[]{
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
    }),
    PT(new String[]{
        "Aleatória",
        "Logos",
        "Clubes de futebol",
        "Personagens de Quadrinhos",
        "Estrelas do cinema",
        "Personagens de anime",
        "Carros"
    }),
    ES(new String[]{
        "ALEATORIO",
        "Logos",
        "Clubes de fútbol",
        "Personajes de cómic",
        "ESTRELLAS DE CINE",
        "PERSONAJES DE ANIME",
        "COCHES"
    });

    private String[] topics;

    private Locale(String[] topics) {
        this.topics = topics;
    }

    public String[] getTopics() {
        return topics;
    }

    public static Locale getDefaultLocale() {
        return EN;
    }

}
