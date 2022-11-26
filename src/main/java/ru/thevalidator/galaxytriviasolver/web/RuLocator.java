/*
 * Copyright (C) 2022 thevalidator
 */

package ru.thevalidator.galaxytriviasolver.web;

/**
 * @author thevalidator <the.validator@yandex.ru>
 */
public class RuLocator {
    
    //login page
    public static final String NON_AUTH_USER = "//div[@class='auth-user' and contains(@style, 'none')]";
    public static final String AUTH_USER = "//div[@class='un-auth-user' and contains(@style, 'none')]";
    public static final String COOKIES_CLOSE = "//div[@class='cookies-tip']/i";
    public static final String HAVE_ACCOUNT_BUTTON = "//div[text()='Уже есть персонаж']/parent::a"; //I have an account//Entrar
    public static final String RECOVERY_CODE_FIELD = "//input[@type='password']";
    public static final String SEND_CODE_BUTTON = "//footer[@class='mdc-dialog__actions']/button[contains(@data-mdc-dialog-action, 'accept')]";

    //popup
    public static final String WAS_DISCONNECTED_MESSAGE_TEXT = "отключили от сервера";
    public static final String KICKED_OUT = "выгнал";
    public static final String JAIL_TEXT = "Тюрьма";
    public static final String CONTENT_DIV = "//div[@id='content']";
    public static final String IFRAME = "//iframe[contains(@style, 'visible')]";
    public static final String PLANETS_DIV_FRAME_NAME = "browsercontent";
    public static final String POPUP_DIV_FRAME_NAME = "dialog__html";
    public static final String PLANETS_IFRAME = "//div[@data-component-name='browsercontent']/iframe[contains(@style, 'visible')]";
    public static final String POPUP_IFRAME   = "//div[@data-component-name='dialog__html']/iframe[contains(@style, 'visible')]";
    public static final String POPUP_CLOSE_BUTTON = "//div[@role='alertdialog']//button[contains(@class, 'dialog__close-button')]";
    public static final String QUESTION_FOOTER = "//div[@role='alertdialog']//footer[@class='mdc-dialog__actions']";
    public static final String FOOTER_CANCEL_BUTTON = "//div[@role='alertdialog']//button[@data-mdc-dialog-action='cancel']";
    public static final String OVERLAY_POPUP_CLOSE_BUTTON = "//button[@class='s__overlay__content_close js-overlay-close-button']";
    
    //trivia game
    public static final String TRIVIA_GAME_BUTTON = "//div[@class='s__card__title']//div[text()='Знайка']/../../../..";//Trivia//Trivia//Sabelotodo
    public static final String TRIVIA_START_BUTTON = "//div[text()='Играть']/..";//Play//Jogar//Jugar
    public static final String TRIVIA_ANONYMOUS_TOGGLE = "//div[contains(@class,'s__quiz_switch')]";
    public static final String TRIVIA_TOPIC = "//div[@class='s__quiz_choose_theme']";
    public static final String TRIVIA_ENERGY_COUNT_CARD = "//div[@class='s__card_energy_count']";
    public static final String TRIVIA_ENERGY_TIMER = "//div[@class='s__quiz_energy_banner_text']/p";
    public static final String TRIVIA_MINUTES_TEXT = "мин";//min//min//min
    public static final String TRIVIA_QUIZ_TITLE_RESULT = "//div[@class='s__quiz_title_result']";
    public static final String TRIVIA_QUESTION_NUMBER_ONE = "//p[@id='numberOfQuestion' and contains(text(), 'Вопрос № 1')]";//Question No.1//Pergunta № 1//Cuestión № 1
    public static final String TRIVIA_QUESTION_NUMBER_TWO = "//p[@id='numberOfQuestion' and contains(text(), 'Вопрос № 2')]";//Question No.2//Pergunta № 2//Cuestión № 2
    public static final String TRIVIA_QUESTION_NUMBER_THREE = "//p[@id='numberOfQuestion' and contains(text(), 'Вопрос № 3')]";//Question No.3//Pergunta № 3//Cuestión № 3
    public static final String TRIVIA_QUESTION_NUMBER_FOUR = "//p[@id='numberOfQuestion' and contains(text(), 'Вопрос № 4')]";//Question No.4//Pergunta № 4//Cuestión № 4
    public static final String TRIVIA_QUESTION_NUMBER_FIVE = "//p[@id='numberOfQuestion' and contains(text(), 'Последний вопрос')]";//Last question//Última pergunta//última pregunta
    public static final String TRIVIA_RESULT_HEADER = "//div[@class='browser browser--auth-user']/header/div/section/div/div/span[contains(text(), 'Результаты игры')]";//Results//Resultados//Resultados del juego
    public static final String TRIVIA_GAME_RESULT_TEXT = "Результаты игры";//Results//Resultados//Resultados del juego
    public static final String TRIVIA_QUIZ_RESULT_WRAP = "//div[@class='s__quiz_result_wrap']";
    public static final String TRIVIA_RESULT_POINTS = "//div[contains(@class,'s__quiz_result_self')]//div[@class='s__quiz_result_count']";
    public static final String TRIVIA_WIN_TEXT = "ПОБЕДИЛ";//YOU WIN!//YOU LOSE //VOCÊ VENCEU!//VOCÊ PERDEU! //GANASTE//PERDISTE
    public static final String TRIVIA_DRAW_TEXT = "НИЧЬЯ";//DRAW!//EMPATE!//¡EMPATE!
    public static final String TRIVIA_PLAY_AGAIN_BUTTON = "//div[@class='button__wrapper' and contains(text(), 'ИГРАТЬ СНОВА')]";//PLAY AGAIN - 1 //JOGAR NOVAMENTE - 1 //JUGAR UNA VEZ MÁS - 1 
    public static final String TRIVIA_QUESTION_ID = "//div[@id='answersContainer']//div[@rel]";
    
    //race game
    public static final String GAMES_MENU_ITEM = "//a[@class='mdc-list-item' ]//span[text()='Игры']/..";//Games//Jogos//Juegos
    public static final String CARS_GAME_BUTTON = "//div[@class='s__card__title']//div[text()='Тачки']/../../../..";
    public static final String RACE_BUTTON = "//div[text()='Гонка!']/..";
    public static final String ATTEMPTS_TIMER = "//div[@id='js-race-attempts-time']";
    public static final String ATTEMPTS_COUNTER = "//div[@id='js-race-attempts-count']";
    public static final String BACK_BUTTON = "//div[@class='browser browser--auth-user']//section[contains(@class,'align-start')]//div[contains(@class,'top-app-bar__navigation-icon')]";
    public static final String RESULT_OVERLAY = "//div[@style='display: block;' and contains(@class, 'overlay_cars_race')]";
    public static final String WAIT_OVERLAY = "//div[@id='waitOverlay']";
    public static final String NITRO_BUTTON = "//div[@id='nitroButton']";
    public static final String GIFTS_PAGE = "//div[text()='Призы']/..";
    public static final String RANK_SECTION = "//div[text()='Рейтинг']";
    public static final String USER_RANK = "//div[contains(@class,'slide-active')]"
                    + "//div[contains(@class,'plank_self')]"
                    + "//div[contains(@class,'position2')]";

    
    
    public static final String ACTIVE_PLANET_NAME = "//section/a/span[contains(@class, 'top-app-bar__title')]";
    public static final String AVAILABLE_PLANET_LINK = "//a[(@action='JOIN' and not(@confirm) and not(@hint))]";
}
