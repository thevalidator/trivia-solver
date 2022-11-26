/*
 * Copyright (C) 2022 thevalidator
 */

package ru.thevalidator.galaxytriviasolver.web.locale.pt;

import ru.thevalidator.galaxytriviasolver.web.AbstractLocator;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class Locator extends AbstractLocator {

    @Override
    public String getStartPageLink() {return "https://galaxy.mobstudio.ru/web/?lang=pt&p=38";}
    @Override
    public final String getHaveAccountButton() {return "//div[text()='Entrar']/parent::a";}
    @Override
    public final String getGamesMenuItem() {return "//a[@class='mdc-list-item' ]//span[text()='Jogos']/..";}
    @Override
    public final String getDisconnectedMessageText() {return "отключили от сервера";}//TODO
    @Override
    public final String getKickedMessageText() {return "выгнал";}//TODO
    @Override
    public final String getJailMessageText() {return "Тюрьма";}//TODO
    @Override
    public final String getTriviaGameButton() {return "//div[@class='s__card__title']//div[text()='Trivia']/../../../..";}
    @Override
    public final String getTriviaStartButton() {return "//div[text()='Jogar']/..";}
    @Override
    public final String getTriviaMinutesText() {return "min";}
    @Override
    public final String getTriviaQuestionOneHeaderText() {return "//p[@id='numberOfQuestion' and contains(text(), 'Pergunta № 1')]";}
    @Override
    public final String getTriviaQuestionTwoHeaderText() {return "//p[@id='numberOfQuestion' and contains(text(), 'Pergunta № 2')]";}
    @Override
    public final String getTriviaQuestionThreeHeaderText() {return "//p[@id='numberOfQuestion' and contains(text(), 'Pergunta № 3')]";}
    @Override
    public final String getTriviaQuestionFourHeaderText() {return "//p[@id='numberOfQuestion' and contains(text(), 'Pergunta № 4')]";}
    @Override
    public final String getTriviaQuestionFiveHeaderText() {return "//p[@id='numberOfQuestion' and contains(text(), 'Última pergunta')]";}
    @Override
    public final String getTriviaResultHeader() {return "//div[@class='browser browser--auth-user']/header/div/section/div/div/span[contains(text(), 'Resultados')]";}
    @Override
    public final String getTriviaGameResultText() {return "Resultados";}
    @Override
    public final String getTriviaWinText() {return "VOCÊ VENCEU!";}
    @Override
    public final String getTriviaDrawText() {return "EMPATE!";}
    @Override
    public final String getTriviaLostText() {return "VOCÊ PERDEU!";}
    @Override
    public final String getTriviaPlayAgainButton() {return "//div[@class='button__wrapper' and contains(text(), 'JOGAR NOVAMENTE')]";}

}
