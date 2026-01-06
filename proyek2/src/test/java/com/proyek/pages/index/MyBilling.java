package com.proyek.pages.index;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.proyek.base.BasePage;

public class MyBilling extends BasePage {
    private final String myBillingUrl = "https://myaccount.glpi-network.cloud/index.php?mode=billing";

    public MyBilling(WebDriver driver) {
        super(driver);
    }

    // ================= URL =================
    public void open() {
        driver.get(myBillingUrl);
        driver.manage().window().maximize();
        acceptCookieIfPresent();
        waitForPageLoad();
    }

    // ================= LOCATORS =================
    private final By contractLink = By.cssSelector("a.font-green-sharp[target='_blankinstance']");
    private final By addPaymentModeBtn = By.xpath("//a[contains(@href, 'mode=registerpaymentmode') and contains(@class, 'green-stripe')]");

    // ================= VISIBILITY =================
    public boolean isContractLinkVisible() {
        return isVisible(contractLink);
    }

    public boolean isAddPaymentModeBtnVisible() {
        return isVisible(addPaymentModeBtn);
    }

    // ================= ACTIONS =================
    public void clickContractLink() {
        WebElement link = wait.until(ExpectedConditions.elementToBeClickable(contractLink));
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

    public void clickAddPaymentMode() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(addPaymentModeBtn));
        scrollIntoView(button);
        highlight(button);
        demoSleep(500);
        
        try {
            button.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
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

    public boolean isOnMyBillingPage() {
        return getCurrentUrl().contains("mode=billing");
    }
}
