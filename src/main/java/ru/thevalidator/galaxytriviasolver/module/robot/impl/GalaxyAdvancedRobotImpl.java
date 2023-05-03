/*
 * Copyright (C) 2023 thevalidator
 */
package ru.thevalidator.galaxytriviasolver.module.robot.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import ru.thevalidator.galaxytriviasolver.module.robot.GalaxyAdvancedRobot;
import ru.thevalidator.galaxytriviasolver.module.robot.Robot;
import ru.thevalidator.galaxytriviasolver.module.trivia.State;
import ru.thevalidator.galaxytriviasolver.module.trivia.solver.Solver;
import ru.thevalidator.galaxytriviasolver.service.Task;
import ru.thevalidator.galaxytriviasolver.util.webdriver.WebDriverUtil;
import ru.thevalidator.galaxytriviasolver.web.Locator;

public class GalaxyAdvancedRobotImpl extends Robot implements GalaxyAdvancedRobot {

    public GalaxyAdvancedRobotImpl(Solver solver, Task task, WebDriver driver, State state) {
        super(solver, task, driver, state);
    }

    @Override
    public void selectRidesGame() {
        closePopup(2_500);
        WebDriverUtil.wait(driver, 15_000).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.xpath(Locator.getBaseContentIframe())));
        ((WebElement) WebDriverUtil.wait(driver, 15_000).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Locator.getGamesRidesBtn(state.getLocale()))))).click();
        informObservers("opening Rides");
    }

    @Override
    public boolean startRidesGame() {
        closePopup(1_500);
        WebDriverUtil.wait(driver, 15_000).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.xpath(Locator.getBaseContentIframe())));
        final String attempts = ((WebElement) WebDriverUtil.wait(driver, 10_000).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Locator.getRidesGameAttemptsCounter())))).getText().trim();
        informObservers(attempts);
        if (attempts.equals("0")) {
            return false;
        }
        driver.findElement(By.xpath(Locator.getRidesStartRaceBtn())).click();
        return true;
    }

    @Override
    public void playRidesGame() {
        int wins = 0;
        while (true) {
            WebDriverUtil.wait(driver, 15000).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.xpath(Locator.getBaseContentIframe())));
            informObservers("Race: searching for the opponent");
            WebDriverUtil.wait(driver, 25000).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Locator.getRidesWaitOverlay())));
            WebDriverUtil.wait(driver, 25000).until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(Locator.getRidesWaitOverlay())));
            informObservers("Race started");
            try {
                final long start = System.currentTimeMillis();
                while (System.currentTimeMillis() - start < state.getNosDelayTime()) {
                    Thread.onSpinWait();
                }
                try {
                    ((WebElement) WebDriverUtil.wait(driver, 500).until(ExpectedConditions.elementToBeClickable(By.xpath(Locator.getRidesNitroBtn())))).click();
                } catch (Exception ex) {
                }
                ((WebElement) WebDriverUtil.wait(driver, 15000).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Locator.getRidesPopupCloseBtn())))).click();
                while (true) {
                    ((WebElement) WebDriverUtil.wait(driver, 3000).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Locator.getRidesPopupCloseBtn())))).click();
                }
            } catch (Exception ex2) {
                final WebElement resultDiv = (WebElement) WebDriverUtil.wait(driver, 3000).until(ExpectedConditions.visibilityOfElementLocated(By.xpath(Locator.getRidesResultsDiv())));
                if (resultDiv.getAttribute("id").contains("lose")) {
                    informObservers("Race finished - LOST");
                } else {
                    informObservers("Race finished - WIN");
                    ++wins;
                }
                driver.switchTo().defaultContent();
                closePopup(1500);
                WebDriverUtil.wait(driver, 5000).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.xpath(Locator.getBaseContentIframe())));
                WebElement raceAgainBtnLink = driver.findElement(By.xpath(Locator.getRidesRaceAgainBtnLink()));

                if (raceAgainBtnLink.getAttribute("action") != null) {
                    driver.findElement(By.xpath(Locator.getRidesRaceAgainBtn())).click();
                    continue;
                }
                break;
            }
        }
        informObservers("Race: no attempts left, won " + wins + " races.");
    }

}
