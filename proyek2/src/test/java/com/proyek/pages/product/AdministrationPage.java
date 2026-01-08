package com.proyek.pages.product;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.proyek.base.BasePage;

public class AdministrationPage extends BasePage {

    public final String adminUrl = "https://www.glpi-project.org/en/user-management-software/";

    public AdministrationPage(WebDriver driver) {
        super(driver);
    }

    // ================= LOCATORS =================
    public final By heroTitle = By.id("headline-6-57");

    public final By manageYourUsers = By.id("link_text-11-57");
    public final By startNow1 = By.id("link_button-35-57");
    public final By startNow2 = By.id("link_button-42-57");
    public final By startNow3 = By.id("link_button-48-57");
    public final By startNow4 = By.id("link_button-232-57");

    public final By tutorialIframe = By.cssSelector("iframe[title*='Attribuer']");
    public final By browseAllTestimonials = By.id("link_button-250-57");
    public final By exploreNow = By.id("link_button-216-57");

    public final By newsletterEmail = By.cssSelector("input[name='email']");
    public final By newsletterCheckbox = By.cssSelector("input[type='checkbox'][name='checkbox-75[]']");
    public final By newsletterSubmit = By.cssSelector("input.wpcf7-submit");

    // ================= URL =================
    public void open() {
        driver.get(adminUrl);
        driver.manage().window().maximize();
        acceptCookieIfPresent();
        waitForPageLoad();
    }

    // ================= VISIBILITY =================
    public boolean isHeroVisible() {
        return isVisible(heroTitle);
    }

    // ================= BUTTON HELPERS =================
    public void clickOpenNewTab(By locator) {
        String main = driver.getWindowHandle();
        WebElement el = wait.until(ExpectedConditions.elementToBeClickable(locator));
        scrollIntoView(el);
        highlight(el);

        try {
            el.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
        }

        // Tutup tab baru
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

    public void clickSameTab(By locator) {
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
        waitForPageLoad();
    }

    // ================= TUTORIAL IFRAME =================
    public void runTutorialIframe() {
        try {
            WebElement iframe = wait.until(ExpectedConditions.presenceOfElementLocated(tutorialIframe));
            driver.switchTo().frame(iframe);

            List<WebElement> buttons = driver.findElements(By.cssSelector("a, button"));
            if (!buttons.isEmpty()) {
                WebElement btn = buttons.get(0);
                scrollIntoView(btn);
                highlight(btn);
                try {
                    btn.click();
                } catch (Exception e) {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
                }
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

    // ================= HELPER =================
    public void waitForPageLoad() {
        wait.until(webDriver -> ((JavascriptExecutor) webDriver)
                .executeScript("return document.readyState").equals("complete"));
    }

    // ================= PUBLIC METHODS UNTUK TEST =================
    public void clickManageYourUsers() {
        clickOpenNewTab(manageYourUsers);
    }

    public void clickStartNow1() {
        clickOpenNewTab(startNow1);
    }

    // public void clickStartNow2() {
    // clickOpenNewTab(startNow2);
    // }

    // public void clickStartNow3() {
    // clickOpenNewTab(startNow3);
    // }

    public void clickStartNow4() {
        clickOpenNewTab(startNow4);
    }

    public void clickBrowseAllTestimonials() {
        clickOpenNewTab(browseAllTestimonials);
    }

    public void clickExploreNow() {
        clickSameTab(exploreNow);
    }

}
