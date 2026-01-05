package com.proyek.pages;

import com.proyek.base.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class RndPage extends BasePage {

    private final String rndUrl = "https://www.glpi-project.org/en/research-and-development/";

    public RndPage(WebDriver driver) {
        super(driver);
    }

    // ================= URL =================
    public void open() {
        driver.get(rndUrl);
        driver.manage().window().maximize();
        acceptCookieIfPresent();
        wait.until(ExpectedConditions.visibilityOfElementLocated(rndHeadline));
    }

    // ================= LOCATORS =================
    private final By rndHeadline = By.xpath("//h2[contains(text(),\"Teclib'\")]");

    private final By safe4socTitle = By.xpath("//h3[contains(text(),'SAFE4SOC')]");
    private final By safe4socLink = By.xpath("//a[@id='link_button-16-433330']");

    private final By ienTitle = By.xpath("//h3[contains(text(),'IEN')]");
    private final By ienLink = By.xpath("//a[@id='link_button-33-433330']");

    // ================= VISIBILITY =================
    public boolean isRndHeadlineVisible() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(rndHeadline)).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isSafe4socVisible() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(safe4socTitle)).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isIenVisible() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(ienTitle)).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    // ================= ACTIONS =================
    public void clickSafe4socLink() {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(safe4socLink));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", btn);
        highlight(btn);
        btn.click();
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));
    }

    public void clickIenLink() {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(ienLink));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", btn);
        highlight(btn);
        btn.click();
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}
