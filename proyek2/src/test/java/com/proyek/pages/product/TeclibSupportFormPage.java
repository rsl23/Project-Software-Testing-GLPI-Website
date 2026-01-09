package com.proyek.pages.product;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.proyek.base.BasePage;

public class TeclibSupportFormPage extends BasePage {

    public TeclibSupportFormPage(WebDriver driver) {
        super(driver);
    }

    // ================= LOCATORS =================
    private final By cloudInstanceRadio = By.xpath("//input[@type='radio' and @name='answers_1057[]' and @value='8']");
    private final By additionalRadio = By.xpath("//input[@type='radio' and @name='answers_1074[]' and @value='4']");
    private final By instanceUrlInput = By.name("answers_1055");
    private final By supportKeyInput = By.name("answers_1056");
    private final By fullNameInput = By.name("answers_1058");
    private final By emailInput = By.name("answers_1059");
    private final By phoneInput = By.name("answers_1060");
    private final By altchaCheckbox = By.id("altcha_checkbox_43937195");
    private final By submitButton = By.xpath("//button[@type='button' and @data-glpi-form-renderer-action='submit']");

    // ================= RADIO ACTIONS =================
    public void clickCloudInstanceRadio() {
        WebElement radio = wait.until(ExpectedConditions.elementToBeClickable(cloudInstanceRadio));
        scrollIntoView(radio);
        highlight(radio);
        demoSleep(500);
        radio.click();
        demoSleep(500);
    }

    public void clickAdditionalRadio() {
        WebElement radio = wait.until(ExpectedConditions.elementToBeClickable(additionalRadio));
        scrollIntoView(radio);
        highlight(radio);
        demoSleep(500);
        radio.click();
        demoSleep(500);
    }

    // ================= FORM FILLING =================
    public void fillInstanceUrl(String url) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(instanceUrlInput));
        scrollIntoView(input);
        highlight(input);
        input.clear();
        input.sendKeys(url);
        demoSleep(300);
    }

    public void fillSupportKey(String key) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(supportKeyInput));
        scrollIntoView(input);
        highlight(input);
        input.clear();
        input.sendKeys(key);
        demoSleep(300);
    }

    public void fillFullName(String name) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(fullNameInput));
        scrollIntoView(input);
        highlight(input);
        input.clear();
        input.sendKeys(name);
        demoSleep(300);
    }

    public void fillEmail(String email) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(emailInput));
        scrollIntoView(input);
        highlight(input);
        input.clear();
        input.sendKeys(email);
        demoSleep(300);
    }

    public void fillPhone(String phone) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(phoneInput));
        scrollIntoView(input);
        highlight(input);
        input.clear();
        input.sendKeys(phone);
        demoSleep(300);
    }

    // ================= CHECKBOX & SUBMIT =================
    public void clickAltchaCheckbox() {
        WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(altchaCheckbox));
        scrollIntoView(checkbox);
        highlight(checkbox);
        demoSleep(500);
        checkbox.click();
        demoSleep(500);
    }

    public void clickSubmit() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(submitButton));
        scrollIntoView(button);
        highlight(button);
        demoSleep(500);
        button.click();
        demoSleep(1000);
    }

    // ================= COMPLETE FORM =================
    public void fillCompleteForm(String instanceUrl, String supportKey, String fullName, 
                                  String email, String phone) {
        clickCloudInstanceRadio();
        fillInstanceUrl(instanceUrl);
        fillSupportKey(supportKey);
        fillFullName(fullName);
        fillEmail(email);
        fillPhone(phone);
        clickAltchaCheckbox();
    }

    // ================= HELPERS =================
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public boolean isOnTeclibSupportForm() {
        return getCurrentUrl().contains("support.teclib.com/Form/Render");
    }
}
