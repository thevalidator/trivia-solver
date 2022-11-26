/*
 * Copyright (C) 2022 thevalidator
 */

package ru.thevalidator.galaxytriviasolver.web.locale.en;

import ru.thevalidator.galaxytriviasolver.web.AbstractLocator;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class Locator extends AbstractLocator {

    @Override
    public String getStartPageLink() {return "https://galaxy.mobstudio.ru/web?lang=en&p=38";}
    @Override
    public final String getHaveAccountButton() {return "//div[text()='I have an account']/parent::a";}
    @Override
    public final String getGamesMenuItem() {return "//a[@class='mdc-list-item' ]//span[text()='Games']/..";}
    @Override
    public final String getDisconnectedMessageText() {return "отключили от сервера";} //TODO:
    @Override
    public final String getKickedMessageText() {return "выгнал";}//TODO:
    @Override
    public final String getJailMessageText() {return "Тюрьма";}//TODO:
    @Override
    public final String getTriviaGameButton() {return "//div[@class='s__card__title']//div[text()='Trivia']/../../../..";}
    @Override
    public final String getTriviaStartButton() {return "//div[text()='Play']/..";}
    @Override
    public final String getTriviaMinutesText() {return "min";}
    @Override
    public final String getTriviaQuestionOneHeaderText() {return "//p[@id='numberOfQuestion' and contains(text(), 'Question No.1')]";}
    @Override
    public final String getTriviaQuestionTwoHeaderText() {return "//p[@id='numberOfQuestion' and contains(text(), 'Question No.2')]";}
    @Override
    public final String getTriviaQuestionThreeHeaderText() {return "//p[@id='numberOfQuestion' and contains(text(), 'Question No.3')]";}
    @Override
    public final String getTriviaQuestionFourHeaderText() {return "//p[@id='numberOfQuestion' and contains(text(), 'Question No.4')]";}
    @Override
    public final String getTriviaQuestionFiveHeaderText() {return "//p[@id='numberOfQuestion' and contains(text(), 'Last question')]";}
    @Override
    public final String getTriviaResultHeader() {return "//div[@class='browser browser--auth-user']/header/div/section/div/div/span[contains(text(), 'Results')]";}
    @Override
    public final String getTriviaGameResultText() {return "Results";}
    @Override
    public final String getTriviaWinText() {return "YOU WIN!";}
    @Override
    public final String getTriviaDrawText() {return "DRAW!";}
    @Override
    public final String getTriviaLostText() {return "YOU LOSE";}
    @Override
    public final String getTriviaPlayAgainButton() {return "//div[@class='button__wrapper' and contains(text(), 'PLAY AGAIN')]";}

}
