package com.proyek.pages;

import com.proyek.base.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class AboutUsPage extends BasePage {

    private final String aboutUsUrl = "https://www.glpi-project.org/en/about-us/";

    public AboutUsPage(WebDriver driver) {
        super(driver);
    }

    // ================= URL =================
    public void open() {
        driver.get(aboutUsUrl);
        driver.manage().window().maximize();
        acceptCookieIfPresent();
        wait.until(ExpectedConditions.visibilityOfElementLocated(headlineAboutUs));
    }

    // ================= LOCATORS =================
    private final By headlineAboutUs = By.xpath("//h2[contains(text(),'About us')]");
    private final By discoverJobsBtn = By.xpath("//a[contains(text(),'Discover our jobs')]");

    // ================= VISIBILITY =================
    public boolean isHeadlineVisible() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(headlineAboutUs)).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isDiscoverJobsButtonVisible() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(discoverJobsBtn)).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    // ================= ACTIONS =================
    public void clickDiscoverJobs() {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(discoverJobsBtn));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", btn);
        highlight(btn);
        btn.click();
        // Tunggu pindah ke tab baru jika diperlukan
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));
    }

    // ================= HELPERS =================
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}
