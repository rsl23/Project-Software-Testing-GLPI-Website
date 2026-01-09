package com.proyek.pages.common;

import com.proyek.base.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.List;

public class HomePage extends BasePage {

    private final String homepageUrl = "https://www.glpi-project.org/en/";

    public HomePage(WebDriver driver) {
        super(driver);
    }

    // ================= URL =================
    public void open() {
        driver.get(homepageUrl);
        driver.manage().window().maximize(); // Pastikan layar penuh
        acceptCookieIfPresent();
        wait.until(ExpectedConditions.visibilityOfElementLocated(heroTitle));
    }

    // ================= LOCATORS =================
    private final By heroTitle = By.tagName("h1");
    private final By getStartedHeroBtn = By.xpath(
            "(//a[contains(translate(.,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'get started')])[1]");
    private final By helpdeskCard = By.xpath(
            "//h3[normalize-space()='Helpdesk']/following::a[contains(.,'Get Started')][1]");
    private final By inventoryCard = By.xpath(
            "//h3[contains(normalize-space(),'Inventory')]/following::a[contains(.,'Get Started')][1]");
    private final By financialCard = By.xpath(
            "//h3[contains(normalize-space(),'Financial')]/following::a[contains(.,'Get Started')][1]");
    private final By startNowBtn = By.xpath(
            "//a[contains(translate(.,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'start now')]");
    private final By viewTestimonialsBtn = By.xpath(
            "//a[contains(text(),'View All Testimonials') or contains(text(),'View all testimonials')]");
    private final By discoverPartnersBtn = By.xpath(
            "//a[contains(text(),'Discover All') or contains(text(),'Discover all')]");
    private final By newsletterEmail = By.cssSelector("input[type='email']");
    private final By newsletterSubmit = By.cssSelector("input[type='submit']");
    private final By socialLinks = By.cssSelector("footer a[target='_blank'][href^='http']");
    private final By newsletterIframe = By.cssSelector("iframe[src*='mailpoet']");

    // ================= VISIBILITY =================
    public boolean isHeroVisible() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(heroTitle)).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isGetStartedHeroVisible() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(getStartedHeroBtn)).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    // ================= ACTIONS =================
    public void clickGetStartedHeroAndClose() {
        clickAndReturn(getStartedHeroBtn);
    }

    public void clickGetStartedHelpdeskAndClose() {
        clickAndReturn(helpdeskCard);
    }

    public void clickGetStartedInventoryAndClose() {
        clickAndReturn(inventoryCard);
    }

    public void clickGetStartedFinancialAndClose() {
        clickAndReturn(financialCard);
    }

    public void clickStartNowAndClose() {
        clickAndReturn(startNowBtn);
    }

    public void clickViewTestimonialsAndClose() {
        clickAndReturn(viewTestimonialsBtn);
    }

    public void clickDiscoverPartnersAndClose() {
        clickAndReturn(discoverPartnersBtn);
    }

    // ================= NEWSLETTER =================
    public void fillNewsletter(String email) {
        // scroll pelan-pelan ke bawah biar JS render jalan
        ((JavascriptExecutor) driver).executeScript(
                "window.scrollTo(0, document.body.scrollHeight);");

        By emailLocator = By.id("form_email_1");

        WebElement emailInput = wait.until(
                ExpectedConditions.presenceOfElementLocated(emailLocator));

        // pastikan kelihatan
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});", emailInput);

        highlight(emailInput);

        emailInput.clear();
        emailInput.sendKeys(email);

        // submit (opsional)
        By submitBtn = By.cssSelector("input[type='submit']");
        if (!driver.findElements(submitBtn).isEmpty()) {
            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].click();",
                            driver.findElement(submitBtn));
        }
    }

    // ================= SOCIAL =================
    public void clickAllSocialMediaLinks() {
        scrollToFooter();
        String main = driver.getWindowHandle();
        List<WebElement> links = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(socialLinks));

        for (WebElement link : links) {
            if (link.isDisplayed()) {
                scrollIntoView(link);
                highlight(link);
                link.click();
                wait.until(ExpectedConditions.numberOfWindowsToBe(2));
                closeNewTab(main);
            }
        }
    }

    // ================= HELPERS =================
    private void clickAndReturn(By locator) {
        String main = driver.getWindowHandle();
        WebElement el = wait.until(ExpectedConditions.elementToBeClickable(locator));

        // scroll ke tengah layar
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", el);
        highlight(el);

        try {
            el.click();
        } catch (ElementClickInterceptedException e) {
            // fallback: paksa klik via JS
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
        }

        wait.until(ExpectedConditions.numberOfWindowsToBe(2));
        closeNewTab(main);
    }

    private void closeNewTab(String main) {
        for (String win : driver.getWindowHandles()) {
            if (!win.equals(main)) {
                driver.switchTo().window(win);
                driver.close();
            }
        }
        driver.switchTo().window(main);
    }

    private final By newsletterSuccess = By.cssSelector(".mailpoet_message, .mailpoet_success");

    public boolean isNewsletterSuccessMessageDisplayed() {
        try {
            return wait.until(
                    ExpectedConditions.visibilityOfElementLocated(newsletterSuccess)).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

}
