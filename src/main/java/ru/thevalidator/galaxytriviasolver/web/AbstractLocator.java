/*
 * Copyright (C) 2022 thevalidator
 */

package ru.thevalidator.galaxytriviasolver.web;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public abstract class AbstractLocator {
    
    //login page link
    public abstract String getStartPageLink();
    
    //login
    public abstract String getHaveAccountButton();
    public final String getNonAuthUserDiv() {return "//div[@class='auth-user' and contains(@style, 'none')]";}
    public final String getAuthUserDiv() {return "//div[@class='un-auth-user' and contains(@style, 'none')]";}
    public final String getCookiesCloseButton() {return "//div[@class='cookies-tip']/i";}
    public final String getRecoveryCodeField() {return "//input[@type='password']";}
    public final String getSendCodeButton() {return "//footer[@class='mdc-dialog__actions']/button[contains(@data-mdc-dialog-action, 'accept')]";}
    
    //menu
    public abstract String getGamesMenuItem();
    public final String getActivePlanetName() {return "//section/a/span[contains(@class, 'top-app-bar__title')]";}
    public final String getAvailablePlanetLink() {return "//a[(@action='JOIN' and not(@confirm) and not(@hint))]";}
    
    //popup
    public abstract String getDisconnectedMessageText();
    public abstract String getKickedMessageText();
    public abstract String getJailMessageText();
    public final String getContentDiv() {return "//div[@id='content']";}
    public final String getIFrame() {return "//iframe[contains(@style, 'visible')]";}
    public final String getPlanetsDivFrameName() {return "browsercontent";}
    public final String getPopupDivFrameName() {return "dialog__html";}
    public final String getPlanetsIFrame() {return "//div[@data-component-name='browsercontent']/iframe[contains(@style, 'visible')]";};
    public final String getPopupIFrame() {return "//div[@data-component-name='dialog__html']/iframe[contains(@style, 'visible')]";};
    public final String getPopupCloseButton() {return "//div[@role='alertdialog']//button[contains(@class, 'dialog__close-button')]";};
    public final String getQuestionFooter() {return "//div[@role='alertdialog']//footer[@class='mdc-dialog__actions']";};
    public final String getFooterAcceptButton() {return "//button[@data-mdc-dialog-action='accept']";}
    public final String getFooterCancelButton() {return "//div[@role='alertdialog']//button[@data-mdc-dialog-action='cancel']";};
    public final String getOverlayPopupCloseButton() {return "//button[@class='s__overlay__content_close js-overlay-close-button']";};
    
    //trivia
    public abstract String getTriviaGameButton();
    public abstract String getTriviaStartButton();
    public abstract String getTriviaMinutesText();
    public abstract String getTriviaQuestionOneHeaderText();
    public abstract String getTriviaQuestionTwoHeaderText();
    public abstract String getTriviaQuestionThreeHeaderText();
    public abstract String getTriviaQuestionFourHeaderText();
    public abstract String getTriviaQuestionFiveHeaderText();
    public abstract String getTriviaResultHeader();
    public abstract String getTriviaGameResultText();
    public abstract String getTriviaWinText();
    public abstract String getTriviaDrawText();
    public abstract String getTriviaLostText();
    public abstract String getTriviaPlayAgainButton();   
    public final String getTriviaAnonymousToggle() {return "//div[contains(@class,'s__quiz_switch')]";};
    public final String getTriviaTopic() {return "//div[@class='s__quiz_choose_theme']";};
    public final String getTriviaEnergyCountCard() {return "//div[@class='s__card_energy_count']";};
    public final String getTriviaEnergyTimer() {return "//div[@class='s__quiz_energy_banner_text']/p";};
    public final String getTriviaQuizTitleResult() {return "//div[@class='s__quiz_title_result']";};
    public final String getTriviaQuizResultWrap() {return "//div[@class='s__quiz_result_wrap']";};
    public final String getTriviaResultPoints() {return "//div[contains(@class,'s__quiz_result_self')]//div[@class='s__quiz_result_count']";};
    public final String getTriviaQuestionNumberHeader() {return "//p[@id='numberOfQuestion']";}
    public final String getTriviaQuestionID() {return "//div[@id='answersContainer']//div[@rel]";};
    public final String getTriviaReturnToMainPageButton() {return "//a[contains(@data-href, 'quiz_index')]";}
    public final String getTriviaDailyRatingsPageButton() {return "//div[@class='s__quiz_main_rating_card' and contains(@data-href, 'quiz_daily_rating')]";}
    public final String getTriviaOwnDailyResult() {return (getTriviaDailyRatingsPageButton() + "//div[@class='s__quiz_main_rating_card_count']");}
    public final String getTriviaFirstPlaceDailyResult() {return "//div[contains(@class,'s__gd__plank s__quiz_rating ')]/a/div[@class='s__gd__plank__block number' and contains(text(), '1 x')]/..//div[@class='s__gd__plank__text position2']";}
    public final String getTriviaTenthPlaceDailyResult() {return "//div[contains(@class,'s__gd__plank s__quiz_rating ')]/a/div[@class='s__gd__plank__block number' and contains(text(), '10 x')]/..//div[@class='s__gd__plank__text position2']";}
    public final String getTriviaUnlimShopButton() {return "//a[contains(@data-href, 'quiz_energy_shop_index')]";}
    
    public final String getTriviaBuyUnlimButton(int option) {
        if (option < 0 || option > 3) {
            throw new IllegalArgumentException("Value must be from 1 to 3");
        }
        return "//div[@class='s__card_unlim_wrap']//div[contains(@data-href, 'quiz_energy_shop_buy&item_id=" + option + "')]";
    }
    
    
    //races
    public final String getRacesGameButton() {return "//a[@class='s__card ' and contains(@data-href, 'cars_box_index')]";};
    public final String getRacesStartButton() {return "//a[@id='js-button-start-race']";};
    public final String getRacesAttemptsCount() {return "//div[@id='js-race-attempts-count']";};
    public final String getRacesRaceAgainButton() {return "//div[@style='display: block;']//a[contains(@data-href, 'cars_race')]/div";};
    public final String getRacesOpponentFoundTriggerBlock() {return "//div[@id='waitOverlay' and @style='display: none;']";};


//    //login page
    
}
