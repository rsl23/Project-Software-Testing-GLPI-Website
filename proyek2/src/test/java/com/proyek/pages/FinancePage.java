package com.proyek.pages;

import com.proyek.base.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class FinancePage extends BasePage {

    public final String financeUrl = "https://www.glpi-project.org/en/financial-management-software/";

    public FinancePage(WebDriver driver) {
        super(driver);
    }

    // ================= URL =================
    public void open() {
        driver.get(financeUrl);
        acceptCookieIfPresent();
        waitForPageLoad();
    }

    // ================= LOCATORS =================
    public final By heroTitle = By.tagName("h1");
    public final By trackYourExpenses = By.id("link_text-11-57");
    public final By startNow1 = By.id("link_button-35-57");
    public final By startNow2 = By.id("link_button-42-57");
    public final By startNow3 = By.id("link_button-48-57");
    public final By startNow4 = By.id("link_button-232-57");
    public final By tutorialIframe = By.cssSelector("iframe[title='Budget - GLPI']");
    public final By browseAllTestimonials = By.id("link_button-250-57");
    public final By newsletterEmail = By.cssSelector("input[name='email']");
    public final By newsletterCheckbox = By.cssSelector("input[type='checkbox'][name='checkbox-75[]']");
    public final By newsletterSubmit = By.cssSelector("input.wpcf7-submit");

    // ================= VISIBILITY =================
    public boolean isHeroVisible() {
        return isVisible(heroTitle);
    }

    // ================= BUTTON ACTIONS =================
    public void clickTrackYourExpenses() {
        clickButtonOpenNewTab(trackYourExpenses);
    }

    public void clickStartNow1() {
        clickButtonOpenNewTab(startNow1);
    }

    public void clickStartNow2() {
        clickButtonOpenNewTab(startNow2);
    }

    public void clickStartNow3() {
        clickButtonOpenNewTab(startNow3);
    }

    public void clickStartNow4() {
        clickButtonOpenNewTab(startNow4);
    }

    public void clickBrowseAllTestimonials() {
        clickButtonOpenNewTab(browseAllTestimonials);
    }

    public void clickExploreNow() {
        clickButtonSameTab(By.xpath("//a[contains(normalize-space(.),'Explore Now')]"));
    }

    public void runTutorial() {
        runTutorialIframe();
    }

    public void submitNewsletter(String email) {
        fillNewsletter(email);
    }

    // ================= BUTTON HELPERS =================
    private void clickButtonOpenNewTab(By locator) {
        String main = driver.getWindowHandle();
        WebElement el = wait.until(ExpectedConditions.elementToBeClickable(locator));
        scrollIntoView(el);
        highlight(el);

        try {
            el.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
        }

        // Tutup tab baru jika terbuka
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

    private void clickButtonSameTab(By locator) {
        String originalUrl = driver.getCurrentUrl();
        WebElement el = wait.until(ExpectedConditions.elementToBeClickable(locator));
        scrollIntoView(el);
        highlight(el);

        try {
            el.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
        }

        wait.until(ExpectedConditions.not(ExpectedConditions.urlToBe(originalUrl)));
        driver.navigate().back();
        wait.until(ExpectedConditions.urlToBe(originalUrl));
    }

    // ================= TUTORIAL IFRAME =================
    private void runTutorialIframe() {
        try {
            WebElement iframe = wait.until(ExpectedConditions.presenceOfElementLocated(tutorialIframe));
            driver.switchTo().frame(iframe);

            while (true) {
                List<WebElement> buttons = driver.findElements(By.cssSelector("a, button"));
                WebElement next = buttons.stream()
                        .filter(b -> b.isDisplayed() && b.isEnabled())
                        .findFirst()
                        .orElse(null);

                if (next == null)
                    break;

                scrollIntoView(next);
                highlight(next);

                try {
                    next.click();
                } catch (Exception e) {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", next);
                }

                waitForPageLoad();
            }
        } catch (TimeoutException e) {
            System.out.println("Tutorial iframe tidak muncul");
        } finally {
            driver.switchTo().defaultContent();
        }
    }

    // ================= NEWSLETTER =================
    private void fillNewsletter(String email) {
        scrollToFooter();

        WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(newsletterEmail));
        highlight(emailInput);
        emailInput.clear();
        emailInput.sendKeys(email);

        try {
            WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(newsletterCheckbox));
            if (!checkbox.isSelected()) {
                scrollIntoView(checkbox);
                highlight(checkbox);
                checkbox.click();
            }
        } catch (TimeoutException | NoSuchElementException ignored) {
        }

        driver.findElement(newsletterSubmit).click();
        waitForPageLoad();
    }

    // ================= PAGE LOAD HELPER =================
    public void waitForPageLoad() {
        wait.until(webDriver -> ((JavascriptExecutor) webDriver)
                .executeScript("return document.readyState").equals("complete"));
    }
}
