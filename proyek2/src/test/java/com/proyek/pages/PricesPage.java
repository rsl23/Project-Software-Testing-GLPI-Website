package com.proyek.pages;

import com.proyek.base.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class PricesPage extends BasePage {

    public final String pricesUrl = "https://www.glpi-project.org/en/pricing/";

    public PricesPage(WebDriver driver) {
        super(driver);
    }

    // ================= OPEN =================
    public void open() {
        driver.get(pricesUrl);
        acceptCookieIfPresent();
        waitForPageLoad();
    }

    // ================= PAGE =================
    public final By pageTitle = By.xpath("//h1[contains(.,'Prices')]");

    public boolean isPageVisible() {
        return isVisible(pageTitle);
    }

    // ================= SWITCH =================
    public final By pricingSwitch = By.cssSelector("div.oxel_switcher__toggle");

    // ================= BUTTONS =================
    public final By chooseButtons = By.cssSelector("a[href*='refo=network-']");
    public final By startNowBeforeExplore = By
            .xpath("(//a[contains(@href,'register.php') and contains(@class,'btn-tertiary')])");
    public final By startNowAfterExplore = By
            .xpath("(//a[contains(@href,'register.php') and contains(@class,'btn-primary')])");
    public final By browseTestimonials = By.cssSelector("a[href*='youtube.com/playlist']");
    public final By exploreNow = By.cssSelector("section a[href*='/features/']");

    // ================= CONTACT FORM =================
    public final By lastName = By.name("your-name");
    public final By firstName = By.name("your-first-name");
    public final By company = By.name("societe");
    public final By country = By.name("pays");
    public final By email = By.name("your-email");
    public final By phone = By.name("your-phone");
    public final By message = By.name("your-message");
    public final By contactSubmit = By.cssSelector("input[type='submit'][value='Contact us']");

    // ================= NEWSLETTER =================
    public final By newsletterEmail = By.cssSelector("input[name='email']");
    public final By newsletterCheckbox = By.cssSelector("input[type='checkbox'][name='checkbox-75[]']");
    private final By newsletterSubmit = By.xpath("//input[@type='submit' and @value='Subscribe']");

    // ================= HELPER =================
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

    // ================= CLICK HELPERS =================
    // Overload 1: klik dengan locator
    public void clickOpenNewTab(By locator) {
        WebElement el = wait.until(ExpectedConditions.elementToBeClickable(locator));
        clickOpenNewTab(el);
    }

    // Overload 2: klik dengan WebElement
    public void clickOpenNewTab(WebElement el) {
        String main = driver.getWindowHandle();
        safeClick(el);

        for (String win : driver.getWindowHandles()) {
            if (!win.equals(main)) {
                driver.switchTo().window(win);
                waitForPageLoad();
                driver.close();
            }
        }

        driver.switchTo().window(main);
        waitForPageLoad();
    }

    // Klik tombol di tab yang sama, tidak menunggu URL berubah
    public void clickSameTab(By locator) {
        WebElement el = wait.until(ExpectedConditions.elementToBeClickable(locator));
        safeClick(el);
        waitForPageLoad(); // pastikan page ready
    }

    // ================= ACTIONS =================
    public void togglePricingSwitch() {
        clickSameTab(pricingSwitch);
    }

    public void clickAllChooseButtons() {
        List<WebElement> buttons = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(chooseButtons));
        for (WebElement btn : buttons) {
            if (btn.isDisplayed())
                clickOpenNewTab(btn);
        }
    }

    public void clickStartNowBeforeExplore() {
        List<WebElement> buttons = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(startNowBeforeExplore));
        for (WebElement btn : buttons) {
            if (btn.isDisplayed())
                clickOpenNewTab(btn);
        }
    }

    public void clickStartNowAfterExplore() {
        List<WebElement> buttons = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(startNowAfterExplore));
        for (WebElement btn : buttons) {
            if (btn.isDisplayed())
                clickOpenNewTab(btn);
        }
    }

    public void openTestimonials() {
        clickOpenNewTab(browseTestimonials);
    }

    public void exploreAndReturn() {
        clickSameTab(exploreNow);
    }

    public void fillContactForm() {
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

        WebElement submit = wait.until(ExpectedConditions.elementToBeClickable(contactSubmit));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", submit);
        demoSleep(300);
        ((JavascriptExecutor) driver).executeScript(
                "document.querySelector('header, .oxy-header-center')?.style.setProperty('pointer-events','none');");
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submit);
        waitForPageLoad();
    }

    public void fillNewsletter(String emailStr) {
        // Isi email
        WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(newsletterEmail));
        emailInput.clear();
        emailInput.sendKeys(emailStr);

        // Centang checkbox jika belum dicentang
        WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(newsletterCheckbox));
        if (!checkbox.isSelected())
            safeClick(checkbox);

        // Klik tombol Subscribe yang spesifik
        WebElement submit = wait.until(ExpectedConditions.elementToBeClickable(newsletterSubmit));
        safeClick(submit);

        waitForPageLoad();
    }

    // ================= HELPER =================
    public void waitForPageLoad() {
        wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState")
                .equals("complete"));
    }

    public boolean isMainPage() {
        return isVisible(pageTitle);
    }

}
