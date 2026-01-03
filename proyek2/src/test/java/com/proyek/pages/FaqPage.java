package com.proyek.pages;

import com.proyek.base.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class FaqPage extends BasePage {

    private final String faqUrl = "https://www.glpi-project.org/en/faq/";

    public FaqPage(WebDriver driver) {
        super(driver);
    }

    // ================= OPEN =================
    public void open() {
        driver.get(faqUrl);
        acceptCookieIfPresent();
        demoSleep(1200);
    }

    // ================= PAGE TITLE =================
    private final By pageTitle = By.xpath("//h1[contains(text(),'Frequently Asked Questions')]");

    public boolean isPageVisible() {
        return isVisible(pageTitle);
    }

    // ================= FAQ ACCORDION =================
    private final By accordionHeaders = By.cssSelector(".oxy-pro-accordion_header");
    private final By accordionBodies = By.cssSelector(".oxy-pro-accordion_body");
    private final By accordionLinks = By.cssSelector(".oxy-pro-accordion_body a");

    // ================= CONTACT FORM =================
    private final By contactName = By.name("your-name");
    private final By contactFirstName = By.name("your-first-name");
    private final By contactCompany = By.name("societe");
    private final By contactCountry = By.name("pays");
    private final By contactEmail = By.name("your-email");
    private final By contactPhone = By.name("your-phone");
    private final By contactMessage = By.name("your-message");
    private final By contactSubmit = By.cssSelector("input[type='submit'][value='Contact us']");

    // ================= NEWSLETTER =================
    private final By newsletterEmail = By.name("email");
    private final By newsletterCheckbox = By.cssSelector("input[type='checkbox']");
    private final By newsletterSubmit = By.cssSelector("input[type='submit'][value='Subscribe']");

    // ================= HELPERS =================
    private void safeClick(WebElement el) {
        scrollIntoView(el);
        highlight(el);
        demoSleep(400);
        try {
            el.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
        }
        demoSleep(800);
    }

    private void clickOpenNewTab(WebElement el) {
        String main = driver.getWindowHandle();
        safeClick(el);
        demoSleep(1500);
        for (String win : driver.getWindowHandles()) {
            if (!win.equals(main)) {
                driver.switchTo().window(win);
                demoSleep(1000);
                driver.close();
            }
        }
        driver.switchTo().window(main);
        wait.until(ExpectedConditions.visibilityOfElementLocated(pageTitle));
        demoSleep(600);
    }

    // ================= ACTIONS =================

    // Expand semua FAQ
    public void expandAllFaqs() {
        List<WebElement> headers = driver.findElements(accordionHeaders);
        for (WebElement header : headers) {
            String expanded = header.getAttribute("aria-expanded");
            if ("false".equals(expanded)) {
                safeClick(header);
                demoSleep(500);
            }
        }
    }

    // Klik semua link di FAQ (buka tab baru)
    public void clickAllFaqLinks() {
        List<WebElement> links = driver.findElements(accordionLinks);
        for (WebElement link : links) {
            clickOpenNewTab(link);
        }
    }

    // Isi contact form
    public void fillContactForm(String nameVal, String firstNameVal, String companyVal, String countryVal,
            String emailVal, String phoneVal, String messageVal) {
        driver.findElement(contactName).sendKeys(nameVal);
        driver.findElement(contactFirstName).sendKeys(firstNameVal);
        driver.findElement(contactCompany).sendKeys(companyVal);
        driver.findElement(contactCountry).sendKeys(countryVal);
        driver.findElement(contactEmail).sendKeys(emailVal);
        driver.findElement(contactPhone).sendKeys(phoneVal);
        driver.findElement(contactMessage).sendKeys(messageVal);
        safeClick(driver.findElement(contactSubmit));
    }

    // Subscribe newsletter
    public void subscribeNewsletter(String emailVal) {
        driver.findElement(newsletterEmail).sendKeys(emailVal);
        WebElement checkbox = driver.findElement(newsletterCheckbox);
        if (!checkbox.isSelected()) {
            safeClick(checkbox);
        }
        safeClick(driver.findElement(newsletterSubmit));
    }

    // ================= FULL FLOW TEST =================
    public void testAllElementsComplete(String nameVal, String firstNameVal, String companyVal, String countryVal,
            String emailVal, String phoneVal, String messageVal, String newsletterEmailVal) {
        isPageVisible();
        expandAllFaqs();
        clickAllFaqLinks();
        fillContactForm(nameVal, firstNameVal, companyVal, countryVal, emailVal, phoneVal, messageVal);
        subscribeNewsletter(newsletterEmailVal);
    }
}
