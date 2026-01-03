package com.proyek.pages;

import com.proyek.base.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class PricesPage extends BasePage {

    private final String pricesUrl = "https://www.glpi-project.org/en/pricing/";

    public PricesPage(WebDriver driver) {
        super(driver);
    }

    // ================= OPEN =================
    public void open() {
        driver.get(pricesUrl);
        acceptCookieIfPresent();
        demoSleep(1200);
    }

    // ================= PAGE =================
    private final By pageTitle = By.xpath("//h1[contains(.,'Prices')]");

    public boolean isPageVisible() {
        return isVisible(pageTitle);
    }

    // ================= SWITCH =================
    private final By pricingSwitch = By.cssSelector("div.oxel_switcher__toggle");

    // ================= BUTTONS =================
    private final By chooseButtons = By.cssSelector("a[href*='refo=network-']");

    // START NOW PERTAMA (SEBELUM EXPLORE)
    private final By startNowBeforeExplore = By
            .xpath("(//a[contains(@href,'register.php') and contains(@class,'btn-tertiary')])");

    // START NOW KEDUA (SETELAH EXPLORE)
    private final By startNowAfterExplore = By
            .xpath("(//a[contains(@href,'register.php') and contains(@class,'btn-primary')])");

    private final By browseTestimonials = By.cssSelector("a[href*='youtube.com/playlist']");

    private final By exploreNow = By.cssSelector("section a[href*='/features/']");

    // ================= CONTACT FORM =================
    private final By lastName = By.name("your-name");
    private final By firstName = By.name("your-first-name");
    private final By company = By.name("societe");
    private final By country = By.name("pays");
    private final By email = By.name("your-email");
    private final By phone = By.name("your-phone");
    private final By message = By.name("your-message");
    private final By contactSubmit = By.cssSelector("input[type='submit'][value='Contact us']");

    // ================= NEWSLETTER =================
    private final By newsletterEmail = By.cssSelector("input[name='email']");
    private final By newsletterCheckbox = By.cssSelector("input[type='checkbox'][name='checkbox-75[]']");
    private final By newsletterSubmit = By.cssSelector("input.wpcf7-submit");

    // ================= UTIL =================
    private void safeClick(WebElement el) {
        scrollIntoView(el);
        highlight(el);
        demoSleep(400);
        try {
            el.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].click();", el);
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
    public void togglePricingSwitch() {
        WebElement toggle = wait.until(
                ExpectedConditions.elementToBeClickable(pricingSwitch));
        safeClick(toggle);
    }

    public void clickAllChooseButtons() {
        List<WebElement> buttons = wait.until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(chooseButtons));

        for (WebElement btn : buttons) {
            if (btn.isDisplayed()) {
                clickOpenNewTab(btn);
            }
        }
    }

    public void clickStartNowBeforeExplore() {
        List<WebElement> buttons = wait.until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(startNowBeforeExplore));

        for (WebElement btn : buttons) {
            if (btn.isDisplayed()) {
                clickOpenNewTab(btn);
            }
        }
    }

    public void clickStartNowAfterExplore() {
        List<WebElement> buttons = wait.until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(startNowAfterExplore));

        for (WebElement btn : buttons) {
            if (btn.isDisplayed()) {
                clickOpenNewTab(btn);
            }
        }
    }

    // ================= CONTACT FORM (FIX INTERCEPT) =================
    public void fillContactForm() {

        WebElement lastNameEl = wait.until(
                ExpectedConditions.visibilityOfElementLocated(lastName));
        scrollIntoView(lastNameEl);

        driver.findElement(lastName).clear();
        driver.findElement(lastName).sendKeys("Doe");

        driver.findElement(firstName).clear();
        driver.findElement(firstName).sendKeys("John");

        driver.findElement(company).clear();
        driver.findElement(company).sendKeys("ACME Corp");

        driver.findElement(country).clear();
        driver.findElement(country).sendKeys("Indonesia");

        driver.findElement(email).clear();
        driver.findElement(email).sendKeys("john.doe@test.com");

        driver.findElement(phone).clear();
        driver.findElement(phone).sendKeys("08123456789");

        driver.findElement(message).clear();
        driver.findElement(message).sendKeys("Need pricing information");

        WebElement submit = wait.until(
                ExpectedConditions.presenceOfElementLocated(contactSubmit));

        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});", submit);
        demoSleep(600);

        // HILANGKAN HEADER FIXED YANG NUTUPIN
        ((JavascriptExecutor) driver).executeScript(
                "document.querySelector('header, .oxy-header-center')?.style.setProperty('pointer-events','none');");

        demoSleep(300);

        // FORCE CLICK VIA JS (ANTI INTERCEPT)
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", submit);

        demoSleep(1500);
    }

    public void openTestimonials() {
        WebElement btn = wait.until(
                ExpectedConditions.elementToBeClickable(browseTestimonials));
        clickOpenNewTab(btn);
    }

    // ================= EXPLORE SAME TAB =================
    public void exploreAndReturn() {
        String url = driver.getCurrentUrl();

        WebElement btn = wait.until(
                ExpectedConditions.elementToBeClickable(exploreNow));
        safeClick(btn);

        demoSleep(1500);
        driver.navigate().back();

        wait.until(ExpectedConditions.urlToBe(url));
        wait.until(ExpectedConditions.visibilityOfElementLocated(pageTitle));
        demoSleep(800);
    }

    public void fillNewsletter(String email) {
        scrollToFooter();
        demoSleep(800);

        WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(newsletterEmail));
        highlight(emailInput);
        emailInput.clear();
        emailInput.sendKeys(email);
        demoSleep(500);

        WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(newsletterCheckbox));
        if (!checkbox.isSelected()) {
            scrollIntoView(checkbox);
            highlight(checkbox);
            checkbox.click();
        }

        demoSleep(400);
        driver.findElement(newsletterSubmit).click();
        demoSleep(1200);
    }

    // ================= FULL FLOW =================
    public void testAllElementsComplete() {

        isPageVisible();

        togglePricingSwitch();
        clickAllChooseButtons();

        // START NOW PERTAMA
        clickStartNowBeforeExplore();

        fillContactForm();
        openTestimonials();

        exploreAndReturn();

        // START NOW KEDUA (SETELAH EXPLORE, TIDAK BALIK TESTIMONIAL)
        clickStartNowAfterExplore();

        fillNewsletter("demo@test.com");
    }
}
