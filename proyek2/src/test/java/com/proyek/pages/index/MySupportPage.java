package com.proyek.pages.index;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.proyek.base.BasePage;

public class MySupportPage extends BasePage {
    private final String mySupportUrl = "https://myaccount.glpi-network.cloud/index.php?mode=support";

    public MySupportPage(WebDriver driver) {
        super(driver);
    }

    // ================= URL =================
    public void open() {
        driver.get(mySupportUrl);
        driver.manage().window().maximize();
        acceptCookieIfPresent();
        waitForPageLoad();
    }

    // ================= LOCATORS (using CSS Selector) =================
    private final By faqsLink = By.cssSelector("a[href='https://faq.teclib.com'][target='_faq']");
    private final By statusPageLink = By.cssSelector("a[href='https://status.glpi-network.cloud'][target='_status']");
    private final By supportPageLink = By.cssSelector("a[href^='https://support.teclib.com/cloud'][target='_support']");

    // ================= VISIBILITY =================
    public boolean isFaqsLinkVisible() {
        return isVisible(faqsLink);
    }

    public boolean isStatusPageLinkVisible() {
        return isVisible(statusPageLink);
    }

    public boolean isSupportPageLinkVisible() {
        return isVisible(supportPageLink);
    }

    // ================= ACTIONS =================
    public void clickFaqsLink() {
        WebElement link = wait.until(ExpectedConditions.elementToBeClickable(faqsLink));
        scrollIntoView(link);
        highlight(link);
        demoSleep(500);
        
        try {
            link.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", link);
        }
        demoSleep(1000);
    }

    public void clickStatusPageLink() {
        WebElement link = wait.until(ExpectedConditions.elementToBeClickable(statusPageLink));
        scrollIntoView(link);
        highlight(link);
        demoSleep(500);
        
        try {
            link.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", link);
        }
        demoSleep(1000);
    }

    public void clickSupportPageLink() {
        WebElement link = wait.until(ExpectedConditions.elementToBeClickable(supportPageLink));
        scrollIntoView(link);
        highlight(link);
        demoSleep(500);
        
        try {
            link.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", link);
        }
        demoSleep(1000);
    }

    // ================= HELPERS =================
    private void waitForPageLoad() {
        wait.until(webDriver -> ((JavascriptExecutor) webDriver)
                .executeScript("return document.readyState").equals("complete"));
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public boolean isOnMySupportPage() {
        return getCurrentUrl().contains("mode=support");
    }
}
