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
    private final By addPaymentModeBtn = By
            .xpath("//a[contains(@href, 'mode=registerpaymentmode') and contains(@class, 'btn-warning')]");
    private final By goToApplicationBtn = By
            .xpath("//a[contains(@href, 'glpi-network.cloud') and contains(@class, 'btn-primary')]");
    private final By seeInstancesBtn = By.xpath("//a[contains(@href, 'mode=instances')]");
    private final By seeProfileBtn = By.xpath("//a[contains(@href, 'mode=myaccount')]");
    private final By seeBillingBtn = By.xpath("//a[contains(@href, 'mode=billing')]");

    private final By profileDropdown = By.cssSelector("a.nav-link.dropdown-toggle[href^='#socid']");
    private final By myAccountMenuItem = By
            .xpath("//a[contains(@href, 'mode=myaccount') and contains(text(),'My account')]");

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
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(profileDropdown));
        scrollIntoView(dropdown);
        highlight(dropdown);
        dropdown.click();
        demoSleep(500);
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
        // 1️⃣ klik dropdown dulu
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(profileDropdown));
        scrollIntoView(dropdown);
        highlight(dropdown);
        dropdown.click();
        demoSleep(500);

        // 2️⃣ klik menu item setelah dropdown terbuka
        WebElement menuItem = wait.until(ExpectedConditions.elementToBeClickable(myAccountMenuItem));
        scrollIntoView(menuItem);
        highlight(menuItem);

        try {
            menuItem.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", menuItem);
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
        return getCurrentUrl().contains("mainmenu=home");
    }

    public boolean isOnDashboardPageModeDashboard() {
        return getCurrentUrl().contains("mode=dashboard");
    }

}
