/*
 * Copyright (C) 2022 thevalidator
 */

package ru.thevalidator.galaxytriviasolver.web;

import ru.thevalidator.galaxytriviasolver.module.trivia.Unlim;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public final class Locator {
    
    //  BASE
    public static final String getBaseCookiesCloseBtn() {return "//div[@class='cookies-tip']/i";}
    public static final String getBaseHaveAccountBtn() {return "//div[@class='start__buttons']//a[@class='mdc-button mdc-button--black-secondary']";}
    public static final String getBaseRecoveryCodeField() {return "//input[@type='password']";}
    
    public static final String getBaseAuthUserContent() {return "//div[@class='auth-user']//div[@class='app-content mdc-drawer-app-content']";}
    public static final String getBaseUserBalance() {return "//span[@id='drawer_balance']";} //a[@class='mdc-list-item drawer__current-user__balance']//span[@id='drawer_balance']
    
    public static final String getBasePopupIframe() {return "//div[@data-component-name='dialog__html']//iframe";}
    public static final String getBasePopupDiv() {return "//div[@class='dialog mdc-dialog mdc-dialog--open']";}
    public static final String getBaseFooterAcceptBtn() {return "//footer//button[@data-mdc-dialog-action='accept']";}
    public static final String getBaseFooterCancelBtn() {return "//footer//button[@data-mdc-dialog-action='cancel']";}
    public static final String getBasePopupCloseBtn() {return "//div[@class='dialog mdc-dialog mdc-dialog--open']//button[@class='mdc-icon-button dialog__close-button']";}
    
    public static final String getBaseContentIframe() {return "//div[@class='auth-user']//iframe";}
    public static final String getBaseBackBtn() {return "//div[@class='auth-user']//div[@class='mdc-top-app-bar__navigation-icon app-bar__navigation-icon mdc-icon-button']";}
    
    public static final String getNotificationsBtn() {return "//div[contains(@data-href, 'user_notifications')]";}
    
    public static final String getTriviaDailyRatingsPageBtn() {return "//div[@class='s__quiz_main_rating_card' and contains(@data-href, 'quiz_daily_rating')]";}
    public static final String getTriviaOwnDailyResult() {return (getTriviaDailyRatingsPageBtn() + "//div[@class='s__quiz_main_rating_card_count']");}
    public static final String getTriviaEnergyCount() {return "//div[@class='s__card_energy_count']";}
    public static final String getTriviaAnonymousToggle() {return "//div[contains(@class,'s__quiz_switch')]";}
    public static final String getTriviaTopicDiv() {return "//div[@class='s__quiz_choose_theme']";}
    public static final String getTriviaQuestionHeader() {return "//p[@id='numberOfQuestion']";}
    public static final String getTriviaQuestionAnswer() {return "//div[@id='answersContainer']//div[@rel]";}
    public static final String getTriviaResultHeader() {return "//div[@class='s__quiz_title_result']";}
    public static final String getTriviaResultDiv() {return "//div[contains(@class,'s__quiz_result_self')]";}
    public static final String getTriviaResultPoints() {return getTriviaResultDiv() + "//div[@class='s__quiz_result_count']";}
    public static final String getTriviaEnergyTimer() {return "//div[@class='s__quiz_energy_banner_text']/p";}
    public static final String getTriviaReturnToMainPageBtn() {return "//a[contains(@data-href, 'quiz_index')]";}
    public static final String getTriviaUnlimShopBtn() {return "//a[contains(@data-href, 'quiz_energy_shop_index')]";}
    
    public static final String getTriviaGameProcessFrame() {return "//div[@class='auth-user']//iframe[contains(@src, 'quiz_current_game')]";}
    public static final String getTriviaGameResultsFrame() {return "//div[@class='auth-user']//iframe[contains(@src, 'quiz_results')]";}
    public static final String getTriviaGameMainFrame() {return "//div[@class='auth-user']//iframe[contains(@src, 'quiz_index')]";}
    
    
    public static final String getTriviaBuyUnlimBtn(Unlim option) {
        if (option.getId() < 0 || option.getId() > 3) {
            throw new IllegalArgumentException("Value must be from 1 to 3");
        }
        return "//div[@class='s__card_unlim_wrap']//div[contains(@data-href, 'quiz_energy_shop_buy&item_id=" + option.getId() + "')]";
    }
    
    public static final String getTriviaPositionDailyResult(int position) {
        if (position < 1 || position > 10) {
            return null;
        }
        return "//div[contains(@class,'s__gd__plank s__quiz_rating ')]"
                + "/a/div[@class='s__gd__plank__block number' and contains(text(), '" + position + " x')]"
                + "/..//div[@class='s__gd__plank__text position2']";
    }
    
    public static final String getTriviaPlayAgainBtn(Locale locale) {
        String text;
        text = switch (locale) {
            case RU -> "ИГРАТЬ СНОВА";
            case EN -> "PLAY AGAIN";
            case ES -> "JUGAR UNA VEZ MÁS";
            default -> "JOGAR NOVAMENTE";
        };
        return "//div[@class='button__wrapper' and contains(text(), '" + text + "')]";
    }
    
    public static final String getTriviaStartBtn(Locale locale) {
        String text;
        text = switch (locale) {
            case RU -> "Играть";
            case EN -> "Play";
            case ES -> "Jugar";
            default -> "Jogar";
        };
        return "//div[text()='" + text + "']/..";
    }
    
    public static final String getBaseMenuGamesBtn(Locale locale) {
        String text;
        text = switch (locale) {
            case RU -> "Игры";
            case EN -> "Games";
            case ES -> "Juegos";
            default -> "Jogos";
        };
        return "//nav//span[text()='" + text + "']/..";
    }
    
    public static final String getGamesTriviaBtn(Locale locale) {
        String text;
        text = switch (locale) {
            case RU -> "Знайка";
            case EN -> "Trivia";
            case ES -> "Sabelotodo";
            default -> "Trivia";
        };
        return "//div[@class='s__card__title']//div[text()='" + text + "']/../../../..";
    }
    
    public static final String getBaseMenuMailBtn(Locale locale) {
        String text;
        text = switch (locale) {
            case RU -> "Почта";
            case EN -> "Mail";
            case ES -> "Correo";
            default -> "Correio";
        };
        return "//nav//span[text()='" + text + "']/..";
    }

}