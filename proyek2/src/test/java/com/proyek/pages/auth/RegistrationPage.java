package com.proyek.pages.auth;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.proyek.base.BasePage;

public class RegistrationPage extends BasePage {
    private final String registrationUrl = "https://myaccount.glpi-network.cloud/register.php";

    public RegistrationPage(WebDriver driver) {
        super(driver);
    }

    // ================= URL =================
    public void open() {
        driver.get(registrationUrl);
        driver.manage().window().maximize();
        acceptCookieIfPresent();
        waitForPageLoad();
    }

    // ================= LOCATORS =================
    private final By usernameField = By.id("username");
    private final By orgNameField = By.id("orgName");
    private final By passwordField = By.id("password");
    private final By password2Field = By.id("password2");
    private final By countrySelect = By.id("selectcountry"); // Hidden select element
    private final By countryDropdown = By.id("select2-selectcountry-container");
    private final By sldAndSubdomainField = By.id("sldAndSubdomain");
    private final By tldSelect = By.id("tldid"); // Hidden select element
    private final By tldDropdown = By.id("select2-tldid-container");
    private final By submitButton = By.id("newinstance");

    // ================= VISIBILITY =================
    public boolean isUsernameFieldVisible() {
        return isVisible(usernameField);
    }

    public boolean isSubmitButtonVisible() {
        return isVisible(submitButton);
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

    public void fillOrgName(String orgName) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(orgNameField));
        scrollIntoView(field);
        highlight(field);
        field.clear();
        field.sendKeys(orgName);
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

    public void fillConfirmPassword(String password) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(password2Field));
        scrollIntoView(field);
        highlight(field);
        field.clear();
        field.sendKeys(password);
        demoSleep(500);
    }

    public void selectCountry(String countryName) {
        try {
            // Click dropdown to open
            WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(countryDropdown));
            scrollIntoView(dropdown);
            highlight(dropdown);
            dropdown.click();
            demoSleep(500);

            // Wait for dropdown options to appear and select
            By optionLocator = By.xpath("//li[contains(text(), '" + countryName + "')]");
            WebElement option = wait.until(ExpectedConditions.elementToBeClickable(optionLocator));
            highlight(option);
            option.click();
            demoSleep(500);
        } catch (TimeoutException e) {
            System.out.println("Country dropdown tidak dapat diklik: " + e.getMessage());
        }
    }

    public void fillSubdomain(String subdomain) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(sldAndSubdomainField));
        scrollIntoView(field);
        highlight(field);
        field.clear();
        field.sendKeys(subdomain);
        demoSleep(500);
    }

    public void selectTld(String tldText) {
        try {
            // Click dropdown to open
            WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(tldDropdown));
            scrollIntoView(dropdown);
            highlight(dropdown);
            dropdown.click();
            demoSleep(500);

            // Select TLD option
            By optionLocator = By.xpath("//li[contains(text(), '" + tldText + "')]");
            WebElement option = wait.until(ExpectedConditions.elementToBeClickable(optionLocator));
            highlight(option);
            option.click();
            demoSleep(500);
        } catch (TimeoutException e) {
            System.out.println("TLD dropdown tidak dapat diklik: " + e.getMessage());
        }
    }

    public void clickSubmit() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(submitButton));
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
    public void fillCompleteRegistrationForm(String username, String orgName, String password, 
                                              String country, String subdomain, String tld) {
        fillUsername(username);
        fillOrgName(orgName);
        fillPassword(password);
        fillConfirmPassword(password);
        selectCountry(country);
        fillSubdomain(subdomain);
        selectTld(tld);
    }

    // ================= VALIDATION =================
    public boolean isRecaptchaVisible() {
        try {
            By recaptcha = By.cssSelector("iframe[title*='reCAPTCHA']");
            return driver.findElement(recaptcha).isDisplayed();
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    public void waitForWelcomePageRedirect() {
        try {
            System.out.println("Waiting for registration to process and redirect...");
            // Wait up to 60 seconds for redirect to welcome page
            // Registration processing takes time (form validation, account creation, etc)
            WebDriverWait longWait = new WebDriverWait(driver, Duration.ofSeconds(60));
            longWait.until(ExpectedConditions.urlContains("index.php?welcomecid="));
            System.out.println("Successfully redirected to: " + driver.getCurrentUrl());
        } catch (TimeoutException e) {
            System.out.println("Timeout waiting for redirect. Current URL: " + driver.getCurrentUrl());
            throw new RuntimeException("Registration did not complete within 60 seconds", e);
        }
    }

    // ================= HELPERS =================
    private void waitForPageLoad() {
        wait.until(webDriver -> ((JavascriptExecutor) webDriver)
                .executeScript("return document.readyState").equals("complete"));
    }
}
