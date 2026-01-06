package com.proyek.pages.auth;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.proyek.base.BasePage;

public class LoginPage extends BasePage {
    private final String loginUrl = "https://myaccount.glpi-network.cloud/index.php";

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    // ================= URL =================
    public void open() {
        driver.get(loginUrl);
        driver.manage().window().maximize();
        acceptCookieIfPresent();
        waitForPageLoad();
    }

    // ================= LOCATORS =================
    private final By usernameField = By.id("username");
    private final By passwordField = By.id("password");
    private final By formActionInput = By.xpath("//*[@id='formActions']/div/input");

    // ================= VISIBILITY =================
    public boolean isUsernameFieldVisible() {
        return isVisible(usernameField);
    }

    public boolean isSubmitButtonVisible() {
        return isVisible(formActionInput);
    }

    // ================= FORM FILLING =================
    public void fillUsername(String username) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(usernameField));
        scrollIntoView(field);
        highlight(field);
        field.clear();
        field.sendKeys(username);
        demoSleep(500);
    }

    public void fillPassword(String password) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField));
        scrollIntoView(field);
        highlight(field);
        field.clear();
        field.sendKeys(password);
        demoSleep(500);
    }

    public void clickSubmit() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(formActionInput));
        scrollIntoView(button);
        highlight(button);
        demoSleep(1000);
        
        try {
            button.click();
        } catch (Exception e) {
            // Fallback to JavaScript click if regular click fails
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
        }
        demoSleep(2000);
    }

    // ================= COMPLETE FORM =================
    public void fillCompleteLoginForm(String username, String password) {
        fillUsername(username);
        fillPassword(password);
    }

    // ================= HELPERS =================
    private void waitForPageLoad() {
        wait.until(webDriver -> ((JavascriptExecutor) webDriver)
                .executeScript("return document.readyState").equals("complete"));
    }
}

