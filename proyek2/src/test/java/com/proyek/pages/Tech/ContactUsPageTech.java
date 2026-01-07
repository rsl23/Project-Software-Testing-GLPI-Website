package com.proyek.pages.Tech;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;
import java.util.Set;

public class ContactUsPageTech {

    private final WebDriver driver;
    private final WebDriverWait wait;
    private String lastNavigatedUrl;

    private static final String URL = "https://glpi-network.cloud/contact-us/";

    public ContactUsPageTech(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    // ================= LOCATORS =================

    private final By pageTitle = By.xpath("//h1[normalize-space()='Contact Us']");

    // CTA
    private final By knowledgeBaseBtn = By.xpath("//a[contains(.,'ACCESS KNOWLEDGE BASE')]");

    private final By startTrialBtn = By.xpath("//a[normalize-space()='START TRIAL']");

    // Form fields
    private final By nameField = By.id("et_pb_contact_nameandsurname_0");
    private final By companyField = By.id("et_pb_contact_company_0");
    private final By countrySelect = By.id("et_pb_contact_country_0");
    private final By phoneField = By.id("et_pb_contact_phone_0");
    private final By emailField = By.id("et_pb_contact_email_0");
    private final By typeRequestSelect = By.id("et_pb_contact_typeofrequest_0");
    private final By commentsField = By.id("et_pb_contact_additionalcomments_0");
    private final By sendButton = By.cssSelector("button.et_pb_contact_submit");

    private final By form = By.cssSelector("form.et_pb_contact_form");

    private final By successMessage = By.xpath("//p[contains(text(),'Your request has been sent')]");

    // ================= PAGE ACTIONS =================

    public void open() {
        driver.get(URL);
        wait.until(ExpectedConditions.presenceOfElementLocated(pageTitle));
    }

    public boolean isPageTitleVisible() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(pageTitle))
                .isDisplayed();
    }

    // ================= CTA ACTIONS =================

    public void clickKnowledgeBase() {
        clickNewTab(knowledgeBaseBtn);
    }

    public void clickStartTrial() {
        clickSameTab(startTrialBtn);
    }

    // ================= FORM VALIDATION =================

    public boolean isContactFormVisible() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(form))
                .isDisplayed();
    }

    public boolean areAllRequiredFieldsVisible() {
        return driver.findElement(nameField).isDisplayed()
                && driver.findElement(companyField).isDisplayed()
                && driver.findElement(countrySelect).isDisplayed()
                && driver.findElement(phoneField).isDisplayed()
                && driver.findElement(emailField).isDisplayed()
                && driver.findElement(typeRequestSelect).isDisplayed()
                && driver.findElement(commentsField).isDisplayed();
    }

    public boolean isSendButtonEnabled() {
        return driver.findElement(sendButton).isEnabled();
    }

    public String getFormActionUrl() {
        return driver.findElement(form).getAttribute("action");
    }

    // ================= HELPERS =================

    private void clickSameTab(By locator) {
        String startUrl = driver.getCurrentUrl();

        WebElement el = wait.until(ExpectedConditions.elementToBeClickable(locator));
        scrollIntoView(el);
        el.click();

        wait.until(d -> !d.getCurrentUrl().equals(startUrl));
        lastNavigatedUrl = driver.getCurrentUrl();

        driver.navigate().back();
        wait.until(ExpectedConditions.urlToBe(startUrl));
    }

    private void clickNewTab(By locator) {
        String main = driver.getWindowHandle();
        Set<String> before = driver.getWindowHandles();

        WebElement el = wait.until(ExpectedConditions.elementToBeClickable(locator));
        scrollIntoView(el);
        el.click();

        wait.until(d -> d.getWindowHandles().size() > before.size());

        for (String win : driver.getWindowHandles()) {
            if (!win.equals(main)) {
                driver.switchTo().window(win);
                wait.until(d -> !d.getCurrentUrl().isBlank());
                lastNavigatedUrl = driver.getCurrentUrl();
                driver.close();
            }
        }
        driver.switchTo().window(main);
    }

    private void scrollIntoView(WebElement el) {
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block:'center'});", el);
    }

    public String getLastNavigatedUrl() {
        return lastNavigatedUrl;
    }

    // ================= FORM ACTIONS =================

    /**
     * Mengisi semua field contact form
     */
    public void fillContactForm(String name, String company, String country, String phone,
            String email, String typeRequest, String comments) {
        driver.findElement(nameField).sendKeys(name);
        driver.findElement(companyField).sendKeys(company);

        // pakai Select untuk dropdown country
        Select countryDropdown = new Select(driver.findElement(countrySelect));
        countryDropdown.selectByVisibleText(country);

        driver.findElement(phoneField).sendKeys(phone);
        driver.findElement(emailField).sendKeys(email);

        // pakai Select untuk dropdown type of request
        Select typeDropdown = new Select(driver.findElement(typeRequestSelect));
        typeDropdown.selectByVisibleText(typeRequest);

        driver.findElement(commentsField).sendKeys(comments);
    }

    /**
     * Submit form
     */
    public void submitForm() {
        WebElement btn = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(sendButton));

        scrollIntoView(btn); // scroll supaya terlihat

        // klik via JS supaya event JS ter-trigger
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
    }

    /**
     * Mengecek apakah form sudah dikirim dengan memeriksa URL atau presence message
     */
    public boolean isFormSubmitted() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(successMessage))
                    .isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

}
