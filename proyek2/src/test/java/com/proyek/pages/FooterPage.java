package com.proyek.pages;

import com.proyek.base.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.List;

public class FooterPage extends BasePage {

    private final String homepageUrl = "https://www.glpi-project.org/en/";

    public FooterPage(WebDriver driver) {
        super(driver);
    }

    // ================= URL =================
    public void open() {
        driver.get(homepageUrl);
        driver.manage().window().maximize();
        acceptCookieIfPresent();
    }

    // ================= LOCATORS =================
    private final By footerDiv = By.id("div_block-157-10"); // Hanya div ini
    private final By footerLinks = By.cssSelector("#div_block-157-10 a"); // Semua <a> di dalamnya

    // ================= ACTIONS =================
    public void goToFooterDiv() {
        WebElement footer = wait.until(ExpectedConditions.visibilityOfElementLocated(footerDiv));
        scrollIntoView(footer);
    }

    public List<WebElement> getAllFooterLinks() {
        return driver.findElements(footerLinks);
    }

    public void safeClick(WebElement element) {
        String main = driver.getWindowHandle();
        scrollIntoView(element);
        highlight(element);

        try {
            element.click();
        } catch (ElementNotInteractableException e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        } catch (StaleElementReferenceException e) {
            // Jika element hilang karena reload → skip klik
            return;
        }

        try {
            Thread.sleep(800);
        } catch (InterruptedException ignored) {
        }

        // New tab scenario
        if (driver.getWindowHandles().size() > 1) {
            for (String win : driver.getWindowHandles()) {
                if (!win.equals(main)) {
                    driver.switchTo().window(win);
                    driver.close();
                }
            }
            driver.switchTo().window(main);
        } else {
            // Self-redirect → tunggu footer reload
            try {
                wait.until(ExpectedConditions.stalenessOf(element));
            } catch (TimeoutException ignored) {
            }
            wait.until(ExpectedConditions.visibilityOfElementLocated(footerDiv));
        }
    }
}
