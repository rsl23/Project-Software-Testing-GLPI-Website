package com.proyek.pages.product;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.proyek.base.BasePage;

public class ContactPage extends BasePage {

    private final String contactUrl = "https://www.glpi-project.org/contact/";

    public ContactPage(WebDriver driver) {
        super(driver);
    }

    // ================= LOCATORS =================
    // Radio buttons
    private final By becomeUserRadio = By.xpath("//span[@class='forminator-radio-label' and text()='Become a GLPI user']");
    private final By alreadyCustomerRadio = By.xpath("//span[@class='forminator-radio-label']//font[contains(text(),'I am already a customer')]");
    private final By openCloudInstanceRadio = By.xpath("//span[@class='forminator-radio-label' and text()='I would like to open a Cloud instance']");
    private final By between1And25Radio = By.xpath("//span[@class='forminator-radio-label' and text()='Between 1 and 25']");
    
    // Input fields
    private final By lastNameInput = By.name("name-1");
    private final By firstNameInput = By.name("name-2");
    private final By companyInput = By.name("text-1");
    private final By countryInput = By.name("text-2");
    private final By emailInput = By.name("email-1");
    private final By phoneInput = By.name("phone-1");
    private final By messageTextarea = By.name("textarea-1");
    
    // Buttons
    private final By resetButton = By.cssSelector("button.js-reset-to-start");
    private final By sendMessageButton = By.cssSelector("button.forminator-button-submit");
    private final By cloudLink = By.xpath("//a[@class='btn-form' and @href='https://support.teclib.com/marketplace/formcreator/front/formdisplay.php?id=100']");

    // ================= URL =================
    public void open() {
        driver.get(contactUrl);
        driver.manage().window().maximize();
        acceptCookieIfPresent();
        waitForPageLoad();
    }

    // ================= RADIO BUTTON ACTIONS =================
    public void clickBecomeUserRadio() {
        WebElement radio = wait.until(ExpectedConditions.elementToBeClickable(becomeUserRadio));
        scrollIntoView(radio);
        highlight(radio);
        demoSleep(500);
        radio.click();
        demoSleep(500);
    }

    public void clickAlreadyCustomerRadio() {
        WebElement radio = wait.until(ExpectedConditions.elementToBeClickable(alreadyCustomerRadio));
        scrollIntoView(radio);
        highlight(radio);
        demoSleep(500);
        radio.click();
        demoSleep(500);
    }

    public void clickOpenCloudInstanceRadio() {
        WebElement radio = wait.until(ExpectedConditions.elementToBeClickable(openCloudInstanceRadio));
        scrollIntoView(radio);
        highlight(radio);
        demoSleep(500);
        radio.click();
        demoSleep(500);
    }

    public void clickBetween1And25Radio() {
        WebElement radio = wait.until(ExpectedConditions.elementToBeClickable(between1And25Radio));
        scrollIntoView(radio);
        highlight(radio);
        demoSleep(500);
        radio.click();
        demoSleep(500);
    }

    // ================= FORM FILLING =================
    public void fillLastName(String lastName) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(lastNameInput));
        scrollIntoView(input);
        highlight(input);
        input.clear();
        input.sendKeys(lastName);
        demoSleep(300);
    }

    public void fillFirstName(String firstName) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameInput));
        scrollIntoView(input);
        highlight(input);
        input.clear();
        input.sendKeys(firstName);
        demoSleep(300);
    }

    public void fillCompany(String company) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(companyInput));
        scrollIntoView(input);
        highlight(input);
        input.clear();
        input.sendKeys(company);
        demoSleep(300);
    }

    public void fillCountry(String country) {
        WebElement input = wait.until(ExpectedConditions.visibilityOfElementLocated(countryInput));
        scrollIntoView(input);
        highlight(input);
        input.clear();
        input.sendKeys(country);
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

    public void fillMessage(String message) {
        WebElement textarea = wait.until(ExpectedConditions.visibilityOfElementLocated(messageTextarea));
        scrollIntoView(textarea);
        highlight(textarea);
        textarea.clear();
        textarea.sendKeys(message);
        demoSleep(300);
    }

    // ================= BUTTON ACTIONS =================
    public void clickReset() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(resetButton));
        scrollIntoView(button);
        highlight(button);
        demoSleep(500);
        button.click();
        demoSleep(1000);
    }

    public void clickSendMessage() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(sendMessageButton));
        scrollIntoView(button);
        highlight(button);
        demoSleep(500);
        button.click();
        demoSleep(1000);
    }

    public void clickCloudLink() {
        WebElement link = wait.until(ExpectedConditions.elementToBeClickable(cloudLink));
        scrollIntoView(link);
        highlight(link);
        demoSleep(500);
        link.click();
        demoSleep(1000);
    }

    // ================= COMPLETE FORM =================
    public void fillCompleteContactForm(String lastName, String firstName, String company, 
                                        String country, String email, String phone, String message) {
        clickBecomeUserRadio();
        clickOpenCloudInstanceRadio();
        clickBetween1And25Radio();
        fillLastName(lastName);
        fillFirstName(firstName);
        fillCompany(company);
        fillCountry(country);
        fillEmail(email);
        fillPhone(phone);
        fillMessage(message);
    }

    // ================= VISIBILITY CHECKS =================
    public boolean isBecomeUserRadioVisible() {
        return isVisible(becomeUserRadio);
    }

    public boolean isAlreadyCustomerRadioVisible() {
        return isVisible(alreadyCustomerRadio);
    }

    public boolean isOpenCloudInstanceRadioVisible() {
        return isVisible(openCloudInstanceRadio);
    }

    public boolean isBetween1And25RadioVisible() {
        return isVisible(between1And25Radio);
    }

    public boolean isLastNameInputVisible() {
        return isVisible(lastNameInput);
    }

    public boolean isFirstNameInputVisible() {
        return isVisible(firstNameInput);
    }

    public boolean isCompanyInputVisible() {
        return isVisible(companyInput);
    }

    public boolean isCountryInputVisible() {
        return isVisible(countryInput);
    }

    public boolean isEmailInputVisible() {
        return isVisible(emailInput);
    }

    public boolean isPhoneInputVisible() {
        return isVisible(phoneInput);
    }

    public boolean isMessageTextareaVisible() {
        return isVisible(messageTextarea);
    }

    public boolean isResetButtonVisible() {
        return isVisible(resetButton);
    }

    public boolean isSendMessageButtonVisible() {
        return isVisible(sendMessageButton);
    }

    public boolean isCloudLinkVisible() {
        return isVisible(cloudLink);
    }

    // ================= HELPERS =================
    private void waitForPageLoad() {
        wait.until(webDriver -> ((JavascriptExecutor) webDriver)
                .executeScript("return document.readyState").equals("complete"));
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public boolean isOnContactPage() {
        return getCurrentUrl().contains("contact");
    }
}

