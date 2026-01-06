package com.proyek.pages.company;

import com.proyek.base.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

import java.util.List;

public class CustomerPage extends BasePage {

    private final String customerUrl = "https://www.glpi-project.org/en/customers/";

    private final By successVideo = By.id("lyte_yNnYSwF7Xxo");

    private final By lightbox = By.cssSelector("div.lyte-popup, div.lyte.lyte-open, iframe[src*='youtube']");

    private final By successVideoWrapper = By.id("lyte_yNnYSwF7Xxo");

    public CustomerPage(WebDriver driver) {
        super(driver);
    }

    // ================= URL =================
    public void open() {
        driver.get(customerUrl);
        driver.manage().window().maximize();
        acceptCookieIfPresent();
        wait.until(ExpectedConditions.visibilityOfElementLocated(successStoriesHeadline));
    }

    // ================= LOCATORS =================
    private final By successStoriesHeadline = By.xpath("//h2[contains(text(),'Success stories')]");
    private final By watchYoutubeBtn = By.xpath("//a[contains(text(),'Watch our Youtube Channel')]");

    // ================= VISIBILITY =================
    public boolean isSuccessStoriesVisible() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(successStoriesHeadline)).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isWatchYoutubeButtonVisible() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(watchYoutubeBtn)).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    public void clickSuccessVideo() {
        WebElement video = wait.until(ExpectedConditions.elementToBeClickable(successVideoWrapper));
        video.click();
    }

    public boolean isVideoLightboxVisible() {
        try {
            // tunggu maksimal 5 detik, bukan durasi video
            return new WebDriverWait(driver, Duration.ofSeconds(5))
                    .until(ExpectedConditions.visibilityOfElementLocated(lightbox))
                    .isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    // ================= ACTIONS =================
    public void clickWatchYoutube() {
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(watchYoutubeBtn));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", btn);
        highlight(btn);
        btn.click();
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));
    }

    public boolean isSuccessVideoVisible() {
        try {
            return wait.until(ExpectedConditions.visibilityOfElementLocated(successVideo)).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    // Optional: ambil link video dari data-src
    public String getSuccessVideoSrc() {
        WebElement video = wait.until(ExpectedConditions.visibilityOfElementLocated(successVideo));
        return video.getAttribute("data-src");
    }

    // ================= HELPERS =================
    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}

