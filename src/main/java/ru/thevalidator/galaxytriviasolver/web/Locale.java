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

    public static String getLocaleURL(Locale locale) {
        return switch (locale) {
            case RU -> "https://galaxy.mobstudio.ru/web?lang=ru&p=38";
            case EN -> "https://galaxy.mobstudio.ru/web?lang=en&p=38";
            case ES -> "https://galaxy.mobstudio.ru/web?lang=es&p=38";
            default -> "https://galaxy.mobstudio.ru/web?lang=pt&p=38";
        };
    }
    
    public static String getWinText(Locale locale) {
        return switch (locale) {
            case RU -> "ПОБЕДИЛ";
            case EN -> "YOU WIN!";
            case ES -> "GANASTE";
            default -> "VOCÊ VENCEU!";
        };
    }
    
    public static String getDrawText(Locale locale) {
        return switch (locale) {
            case RU -> "НИЧЬЯ";
            case EN -> "DRAW!";
            case ES -> "¡EMPATE!";
            default -> "EMPATE!";
        };
    }

}
