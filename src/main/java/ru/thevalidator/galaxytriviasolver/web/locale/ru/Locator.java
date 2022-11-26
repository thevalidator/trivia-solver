/*
 * Copyright (C) 2022 thevalidator
 */

package ru.thevalidator.galaxytriviasolver.web.locale.ru;

import ru.thevalidator.galaxytriviasolver.web.AbstractLocator;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class Locator extends AbstractLocator {

    @Override
    public String getStartPageLink() {return "https://galaxy.mobstudio.ru/web/?lang=ru&p=38";}
    @Override
    public final String getHaveAccountButton() {return "//div[text()='Уже есть персонаж']/parent::a";}
    @Override
    public final String getGamesMenuItem() {return "//a[@class='mdc-list-item' ]//span[text()='Игры']/..";}
    @Override
    public final String getDisconnectedMessageText() {return "отключили от сервера";}
    @Override
    public final String getKickedMessageText() {return "выгнал";}
    @Override
    public final String getJailMessageText() {return "Тюрьма";}
    @Override
    public final String getTriviaGameButton() {return "//div[@class='s__card__title']//div[text()='Знайка']/../../../..";}
    @Override
    public final String getTriviaStartButton() {return "//div[text()='Играть']/..";}
    @Override
    public final String getTriviaMinutesText() {return "мин";}
    @Override
    public final String getTriviaQuestionOneHeaderText() {return "//p[@id='numberOfQuestion' and contains(text(), 'Вопрос № 1')]";}
    @Override
    public final String getTriviaQuestionTwoHeaderText() {return "//p[@id='numberOfQuestion' and contains(text(), 'Вопрос № 2')]";}
    @Override
    public final String getTriviaQuestionThreeHeaderText() {return "//p[@id='numberOfQuestion' and contains(text(), 'Вопрос № 3')]";}
    @Override
    public final String getTriviaQuestionFourHeaderText() {return "//p[@id='numberOfQuestion' and contains(text(), 'Вопрос № 4')]";}
    @Override
    public final String getTriviaQuestionFiveHeaderText() {return "//p[@id='numberOfQuestion' and contains(text(), 'Последний вопрос')]";}
    @Override
    public final String getTriviaResultHeader() {return "//div[@class='browser browser--auth-user']/header/div/section/div/div/span[contains(text(), 'Результаты игры')]";}
    @Override
    public final String getTriviaGameResultText() {return "Результаты игры";}
    @Override
    public final String getTriviaWinText() {return "ПОБЕДИЛ";}
    @Override
    public final String getTriviaDrawText() {return "НИЧЬЯ";}
    @Override
    public final String getTriviaLostText() {return "ПРОИГРАЛ";}
    @Override
    public final String getTriviaPlayAgainButton() {return "//div[@class='button__wrapper' and contains(text(), 'ИГРАТЬ СНОВА')]";}

}
