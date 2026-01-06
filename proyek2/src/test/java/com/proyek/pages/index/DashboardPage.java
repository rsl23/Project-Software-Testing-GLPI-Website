package com.proyek.pages.index;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.proyek.base.BasePage;

public class DashboardPage extends BasePage {
    private final String dashboardUrl = "https://myaccount.glpi-network.cloud/index.php?mode=dashboard";

    public DashboardPage(WebDriver driver) {
        super(driver);
    }

    // ================= URL =================
    public void open() {
        driver.get(dashboardUrl);
        driver.manage().window().maximize();
        acceptCookieIfPresent();
        waitForPageLoad();
    }

    // ================= LOCATORS =================
    private final By addPaymentModeBtn = By.xpath("//a[contains(@href, 'mode=registerpaymentmode') and contains(@class, 'btn-warning')]");
    private final By goToApplicationBtn = By.xpath("//a[contains(@href, 'glpi-network.cloud') and contains(@class, 'btn-primary')]");
    private final By seeInstancesBtn = By.xpath("//a[contains(@href, 'mode=instances')]");
    private final By seeProfileBtn = By.xpath("//a[contains(@href, 'mode=myaccount')]");
    private final By seeBillingBtn = By.xpath("//a[contains(@href, 'mode=billing')]");

    // ================= VISIBILITY =================
    public boolean isAddPaymentModeBtnVisible() {
        return isVisible(addPaymentModeBtn);
    }

    public boolean isGoToApplicationBtnVisible() {
        return isVisible(goToApplicationBtn);
    }

    public boolean isSeeInstancesBtnVisible() {
        return isVisible(seeInstancesBtn);
    }

    public boolean isSeeProfileBtnVisible() {
        return isVisible(seeProfileBtn);
    }

    public boolean isSeeBillingBtnVisible() {
        return isVisible(seeBillingBtn);
    }

    // ================= ACTIONS =================
    public void clickAddPaymentMode() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(addPaymentModeBtn));
        scrollIntoView(button);
        highlight(button);
        demoSleep(500);
        
        try {
            button.click();
        } catch (Exception e) {
            // Fallback to JavaScript click if regular click fails
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
        }
        demoSleep(1000);
    }

    public void clickGoToApplication() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(goToApplicationBtn));
        scrollIntoView(button);
        highlight(button);
        demoSleep(500);
        
        try {
            button.click();
        } catch (Exception e) {
            // Fallback to JavaScript click if regular click fails
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
        }
        demoSleep(1000);
    }

    public void clickSeeInstances() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(seeInstancesBtn));
        scrollIntoView(button);
        highlight(button);
        demoSleep(500);
        
        try {
            button.click();
        } catch (Exception e) {
            // Fallback to JavaScript click if regular click fails
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
        }
        demoSleep(1000);
    }

    public void clickSeeProfile() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(seeProfileBtn));
        scrollIntoView(button);
        highlight(button);
        demoSleep(500);
        
        try {
            button.click();
        } catch (Exception e) {
            // Fallback to JavaScript click if regular click fails
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
        }
        demoSleep(1000);
    }

    public void clickSeeBilling() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(seeBillingBtn));
        scrollIntoView(button);
        highlight(button);
        demoSleep(500);
        
        try {
            button.click();
        } catch (Exception e) {
            // Fallback to JavaScript click if regular click fails
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
        }
        demoSleep(1000);
    }

    // ================= HELPERS =================
    private void waitForPageLoad() {
        wait.until(webDriver -> ((JavascriptExecutor) webDriver)
                .executeScript("return document.readyState").equals("complete"));
    }

    // ================= URL VERIFICATION =================
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public boolean isOnDashboardPage() {
        return getCurrentUrl().contains("mode=dashboard");
    }
}
