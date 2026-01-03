package com.proyek.pages;

import com.proyek.base.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class FinancePage extends BasePage {

    private final String financeUrl = "https://www.glpi-project.org/en/financial-management-software/";

    public FinancePage(WebDriver driver) {
        super(driver);
    }

    // ================= URL =================
    public void open() {
        driver.get(financeUrl);
        acceptCookieIfPresent();
        demoSleep(1200);
    }

    // ================= HERO =================
    private final By heroTitle = By.tagName("h1");

    // Track your expenses
    private final By trackYourExpenses = By.id("link_text-11-57");

    // ================= START NOW BUTTONS =================
    private final By startNow1 = By.id("link_button-35-57");
    private final By startNow2 = By.id("link_button-42-57");
    private final By startNow3 = By.id("link_button-48-57");
    private final By startNow4 = By.id("link_button-232-57");

    // ================= TUTORIAL =================
    private final By tutorialIframe = By.cssSelector("iframe[title='Budget - GLPI']");

    // ================= TESTIMONIAL / CTA =================
    private final By browseAllTestimonials = By.id("link_button-250-57");

    // ================= NEWSLETTER =================
    private final By newsletterEmail = By.cssSelector("input[name='email']");
    private final By newsletterCheckbox = By.cssSelector("input[type='checkbox'][name='checkbox-75[]']");
    private final By newsletterSubmit = By.cssSelector("input.wpcf7-submit");

    // ================= VISIBILITY =================
    public boolean isHeroVisible() {
        return isVisible(heroTitle);
    }

    // ================= CLICK OPEN NEW TAB =================
    private void clickOpenNewTab(By locator) {
        String main = driver.getWindowHandle();

        WebElement el = wait.until(ExpectedConditions.elementToBeClickable(locator));
        scrollIntoView(el);
        highlight(el);
        demoSleep(600);
        el.click();

        demoSleep(2000);
        for (String win : driver.getWindowHandles()) {
            if (!win.equals(main)) {
                driver.switchTo().window(win);
                demoSleep(1200);
                driver.close();
            }
        }
        driver.switchTo().window(main);
        demoSleep(800);
    }

    // ================= CLICK & RETURN (SAME TAB) =================
    public void clickAndReturnByVisibleText(String textContains) {
        String originalUrl = driver.getCurrentUrl();

        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[contains(normalize-space(.),'" + textContains + "')]")));

        scrollIntoView(btn);
        highlight(btn);
        demoSleep(600);

        try {
            btn.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].click();", btn);
        }

        // tunggu pindah halaman
        wait.until(ExpectedConditions.not(
                ExpectedConditions.urlToBe(originalUrl)));

        demoSleep(1200);

        // balik ke Finance Page
        driver.navigate().back();

        wait.until(ExpectedConditions.urlToBe(originalUrl));
        demoSleep(800);
    }

    // ================= TUTORIAL IFRAME =================
    public void runTutorialIframe() {
        try {
            WebElement iframe = wait.until(
                    ExpectedConditions.presenceOfElementLocated(tutorialIframe));
            driver.switchTo().frame(iframe);
            demoSleep(800);

            while (true) {
                List<WebElement> buttons = driver.findElements(By.cssSelector("a, button"));

                WebElement next = null;
                for (WebElement btn : buttons) {
                    if (btn.isDisplayed() && btn.isEnabled()) {
                        next = btn;
                        break;
                    }
                }

                if (next == null)
                    break;

                scrollIntoView(next);
                highlight(next);
                demoSleep(400);

                try {
                    next.click();
                } catch (Exception e) {
                    ((JavascriptExecutor) driver)
                            .executeScript("arguments[0].click();", next);
                }

                demoSleep(900);
            }

        } catch (TimeoutException e) {
            System.out.println("Tutorial iframe tidak muncul");
        } finally {
            driver.switchTo().defaultContent();
        }
    }

    // ================= NEWSLETTER =================
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

        // Track your expenses
        clickOpenNewTab(trackYourExpenses);

        // Start now (1–3)
        clickOpenNewTab(startNow1);
        clickOpenNewTab(startNow2);
        clickOpenNewTab(startNow3);

        // Tutorial
        runTutorialIframe();

        // Testimonials
        clickOpenNewTab(browseAllTestimonials);

        // Explore features (SAME TAB → BACK)
        clickAndReturnByVisibleText("Explore Now");

        // Start now terakhir
        clickOpenNewTab(startNow4);

        // Newsletter
        fillNewsletter("demo@test.com");
    }
}
