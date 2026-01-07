package com.proyek.pages.index;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.proyek.base.BasePage;

public class MyPaymentMethod extends BasePage {
    private final String myPaymentMethodUrl = "https://myaccount.glpi-network.cloud/index.php?mode=registerpaymentmode";

    public MyPaymentMethod(WebDriver driver) {
        super(driver);
    }

    // ================= URL =================
    public void open() {
        driver.get(myPaymentMethodUrl);
        driver.manage().window().maximize();
        acceptCookieIfPresent();
        waitForPageLoad();
    }

    // ================= LOCATORS (using CSS Selector) =================
    // Buttons
    private final By saveAndPayButton = By.id("buttontopay");
    private final By cancelButton = By.id("buttontocancel");
    private final By autofillLinkButton = By.id("link-pay");
    
    // Payment type radio buttons
    private final By cardRadioButton = By.cssSelector("input[name='type'][value='card']");
    private final By sepaRadioButton = By.cssSelector("input[name='type'][value='SepaMandate']");
    
    // Card information fields
    private final By cardholderNameField = By.id("cardholder-name");
    private final By cardNumberField = By.cssSelector("input[name='cardnumber']");
    private final By expirationDateField = By.cssSelector("input[name='exp-date']");
    private final By cvcField = By.cssSelector("input[name='cvc']");

    // ================= VISIBILITY =================
    public boolean isSaveAndPayButtonVisible() {
        return isVisible(saveAndPayButton);
    }

    public boolean isCancelButtonVisible() {
        return isVisible(cancelButton);
    }

    public boolean isAutofillLinkButtonVisible() {
        return isVisible(autofillLinkButton);
    }

    public boolean isCardRadioButtonVisible() {
        return isVisible(cardRadioButton);
    }

    public boolean isSepaRadioButtonVisible() {
        return isVisible(sepaRadioButton);
    }

    public boolean isCardholderNameFieldVisible() {
        return isVisible(cardholderNameField);
    }

    public boolean isCardNumberFieldVisible() {
        return isVisible(cardNumberField);
    }

    public boolean isExpirationDateFieldVisible() {
        return isVisible(expirationDateField);
    }

    public boolean isCvcFieldVisible() {
        return isVisible(cvcField);
    }

    // ================= ACTIONS - BUTTONS =================
    public void clickSaveAndPay() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(saveAndPayButton));
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

    public void clickCancel() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(cancelButton));
        scrollIntoView(button);
        highlight(button);
        demoSleep(500);
        
        try {
            button.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
    

        }
        demoSleep(1000);
    }

    public void clickAutofillLink() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(autofillLinkButton));
        scrollIntoView(button);
        highlight(button);
        demoSleep(500);
        
        try {
            button.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
        }
        demoSleep(1000);
    }

    // ================= ACTIONS - PAYMENT TYPE =================
    public void selectCardPayment() {
        WebElement radio = wait.until(ExpectedConditions.elementToBeClickable(cardRadioButton));
        if (!radio.isSelected()) {
            scrollIntoView(radio);
            highlight(radio);
            demoSleep(500);
            
            try {
                radio.click();
            } catch (Exception e) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", radio);
            }
            demoSleep(500);
        }
    }

    public void selectSepaPayment() {
        WebElement radio = wait.until(ExpectedConditions.elementToBeClickable(sepaRadioButton));
        if (!radio.isSelected()) {
            scrollIntoView(radio);
            highlight(radio);
            demoSleep(500);
            
            try {
                radio.click();
            } catch (Exception e) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", radio);
            }
            demoSleep(500);
        }
    }

    public boolean isCardPaymentSelected() {
        WebElement radio = wait.until(ExpectedConditions.presenceOfElementLocated(cardRadioButton));
        return radio.isSelected();
    }

    public boolean isSepaPaymentSelected() {
        WebElement radio = wait.until(ExpectedConditions.presenceOfElementLocated(sepaRadioButton));
        return radio.isSelected();
    }

    // ================= FORM FILLING - CARD DETAILS =================
    public void fillCardholderName(String name) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(cardholderNameField));
        scrollIntoView(field);
        highlight(field);
        field.clear();
        field.sendKeys(name);
        demoSleep(500);
    }

    public void fillCardNumber(String cardNumber) {
        // Stripe card number is inside an iframe
        try {
            // Wait for the iframe to be present
            WebElement cardNumberIframe = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("iframe[name^='__privateStripeFrame']")
            ));
            
            // Switch to the iframe
            driver.switchTo().frame(cardNumberIframe);
            
            // Find and fill the card number field inside iframe
            WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("input[name='cardnumber'], input[placeholder*='Card number']")
            ));
            field.clear();
            field.sendKeys(cardNumber);
            demoSleep(500);
            
            // Switch back to main content
            driver.switchTo().defaultContent();
        } catch (Exception e) {
            // Fallback: try direct input if not in iframe
            driver.switchTo().defaultContent();
            WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(cardNumberField));
            scrollIntoView(field);
            highlight(field);
            field.clear();
            field.sendKeys(cardNumber);
            demoSleep(500);
        }
    }

    public void fillExpirationDate(String expDate) {
        // Stripe expiration date is inside an iframe
        try {
            // Wait for the iframe to be present
            WebElement expDateIframe = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("iframe[name^='__privateStripeFrame']")
            ));
            
            // Switch to the iframe
            driver.switchTo().frame(expDateIframe);
            
            // Find and fill the expiration date field inside iframe
            WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("input[name='exp-date'], input[placeholder*='MM'], input[placeholder*='YY']")
            ));
            field.clear();
            field.sendKeys(expDate);
            demoSleep(500);
            
            // Switch back to main content
            driver.switchTo().defaultContent();
        } catch (Exception e) {
            // Fallback: try direct input if not in iframe
            driver.switchTo().defaultContent();
            WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(expirationDateField));
            scrollIntoView(field);
            highlight(field);
            field.clear();
            field.sendKeys(expDate);
            demoSleep(500);
        }
    }

    public void fillCvc(String cvc) {
        // Stripe CVC is inside an iframe
        try {
            // Wait for the iframe to be present
            WebElement cvcIframe = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector("iframe[name^='__privateStripeFrame']")
            ));
            
            // Switch to the iframe
            driver.switchTo().frame(cvcIframe);
            
            // Find and fill the CVC field inside iframe
            WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("input[name='cvc'], input[placeholder*='CVC']")
            ));
            field.clear();
            field.sendKeys(cvc);
            demoSleep(500);
            
            // Switch back to main content
            driver.switchTo().defaultContent();
        } catch (Exception e) {
            // Fallback: try direct input if not in iframe
            driver.switchTo().defaultContent();
            WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(cvcField));
            scrollIntoView(field);
            highlight(field);
            field.clear();
            field.sendKeys(cvc);
            demoSleep(500);
        }
    }

    // ================= COMPLETE FORM =================
    public void fillCompleteCardForm(String cardholderName, String cardNumber, String expDate, String cvc) {
        selectCardPayment();
        fillCardholderName(cardholderName);
        fillCardNumber(cardNumber);
        fillExpirationDate(expDate);
        fillCvc(cvc);
    }

    // ================= HELPERS =================
    private void waitForPageLoad() {
        wait.until(webDriver -> ((JavascriptExecutor) webDriver)
                .executeScript("return document.readyState").equals("complete"));
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public boolean isOnPaymentMethodPage() {
        return getCurrentUrl().contains("mode=registerpaymentmode");
    }
}
