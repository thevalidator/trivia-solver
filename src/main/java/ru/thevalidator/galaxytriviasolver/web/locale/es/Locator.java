/*
 * Copyright (C) 2022 thevalidator
 */

package ru.thevalidator.galaxytriviasolver.web.locale.es;

import ru.thevalidator.galaxytriviasolver.web.AbstractLocator;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class Locator extends AbstractLocator {

    @Override
    public String getStartPageLink() {return "https://galaxy.mobstudio.ru/web/?lang=es&p=38";}
    @Override
    public final String getHaveAccountButton() {return "//div[text()='Ya tengo un personaje']/parent::a";}
    @Override
    public final String getGamesMenuItem() {return "//a[@class='mdc-list-item' ]//span[text()='Juegos']/..";}
    @Override
    public final String getDisconnectedMessageText() {return "отключили от сервера";}//TODO
    @Override
    public final String getKickedMessageText() {return "выгнал";}//TODO
    @Override
    public final String getJailMessageText() {return "Тюрьма";}//TODO
    @Override
    public final String getTriviaGameButton() {return "//div[@class='s__card__title']//div[text()='Sabelotodo']/../../../..";}
    @Override
    public final String getTriviaStartButton() {return "//div[text()='Jugar']/..";}
    @Override
    public final String getTriviaMinutesText() {return "min";}
    @Override
    public final String getTriviaQuestionOneHeaderText() {return "//p[@id='numberOfQuestion' and contains(text(), 'Cuestión № 1')]";}
    @Override
    public final String getTriviaQuestionTwoHeaderText() {return "//p[@id='numberOfQuestion' and contains(text(), 'Cuestión № 2')]";}
    @Override
    public final String getTriviaQuestionThreeHeaderText() {return "//p[@id='numberOfQuestion' and contains(text(), 'Cuestión № 3')]";}
    @Override
    public final String getTriviaQuestionFourHeaderText() {return "//p[@id='numberOfQuestion' and contains(text(), 'Cuestión № 4')]";}
    @Override
    public final String getTriviaQuestionFiveHeaderText() {return "//p[@id='numberOfQuestion' and contains(text(), 'última pregunta')]";}
    @Override
    public final String getTriviaResultHeader() {return "//div[@class='browser browser--auth-user']/header/div/section/div/div/span[contains(text(), 'Resultados del juego')]";}
    @Override
    public final String getTriviaGameResultText() {return "Resultados del juego";}
    @Override
    public final String getTriviaWinText() {return "GANASTE";}
    @Override
    public final String getTriviaDrawText() {return "¡EMPATE!";}
    @Override
    public final String getTriviaLostText() {return "PERDISTE";}
    @Override
    public final String getTriviaPlayAgainButton() {return "//div[@class='button__wrapper' and contains(text(), 'JUGAR UNA VEZ MÁS')]";}

}
