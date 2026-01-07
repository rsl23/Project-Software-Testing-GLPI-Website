package com.proyek.pages.index;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.proyek.base.BasePage;

public class MyAppLoginPage extends BasePage {

    public MyAppLoginPage(WebDriver driver) {
        super(driver);
    }

    // ================= URL =================
    public void open(String appUrl) {
        driver.get(appUrl);
        driver.manage().window().maximize();
        acceptCookieIfPresent();
        waitForPageLoad();
    }

    // ================= LOCATORS =================
    private final By loginNameField = By.id("login_name");
    private final By loginPasswordField = By.id("login_password");
    private final By forgotPasswordLink = By.cssSelector("a[href*='lostpassword.php']");
    private final By signInButton = By.cssSelector("button[type='submit'][name='submit']");

    // ================= VISIBILITY =================
    public boolean isLoginNameFieldVisible() {
        return isVisible(loginNameField);
    }

    public boolean isLoginPasswordFieldVisible() {
        return isVisible(loginPasswordField);
    }

    public boolean isForgotPasswordLinkVisible() {
        return isVisible(forgotPasswordLink);
    }

    public boolean isSignInButtonVisible() {
        return isVisible(signInButton);
    }

    // ================= FORM FILLING =================
    public void fillLoginName(String username) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(loginNameField));
        scrollIntoView(field);
        highlight(field);
        field.clear();
        field.sendKeys(username);
        demoSleep(500);
    }

    public void fillLoginPassword(String password) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(loginPasswordField));
        scrollIntoView(field);
        highlight(field);
        field.clear();
        field.sendKeys(password);
        demoSleep(500);
    }

    // ================= ACTIONS =================
    public void clickSignIn() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(signInButton));
        scrollIntoView(button);
        highlight(button);
        demoSleep(500);
        
        try {
            button.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
        }
        demoSleep(2000);
    }

    public void clickForgotPassword() {
        WebElement link = wait.until(ExpectedConditions.elementToBeClickable(forgotPasswordLink));
        scrollIntoView(link);
        highlight(link);
        demoSleep(500);
        
        try {
            link.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", link);
        }
        demoSleep(1000);
    }

    // ================= COMPLETE FORM =================
    public void fillCompleteLoginForm(String username, String password) {
        fillLoginName(username);
        fillLoginPassword(password);
    }

    public void loginToApp(String username, String password) {
        fillCompleteLoginForm(username, password);
        clickSignIn();
    }

    // ================= HELPERS =================
    private void waitForPageLoad() {
        wait.until(webDriver -> ((JavascriptExecutor) webDriver)
                .executeScript("return document.readyState").equals("complete"));
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public boolean isOnGlpiAppLoginPage() {
        String url = getCurrentUrl();
        return url.contains("glpi-network.cloud") && url.contains("/front/");
    }
}
