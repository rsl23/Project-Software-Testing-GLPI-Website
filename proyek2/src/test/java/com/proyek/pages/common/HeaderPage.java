package com.proyek.pages.common;

import com.proyek.base.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.List;

public class HeaderPage extends BasePage {

    private final String homepageUrl = "https://www.glpi-project.org/en/";

    public HeaderPage(WebDriver driver) {
        super(driver);
    }

    // ================= URL =================
    public void open() {
        driver.get(homepageUrl);
        driver.manage().window().maximize();
        acceptCookieIfPresent();
    }

    // ================= LOCATORS =================
    private final By headerLinks = By.cssSelector("header a:not(.dropdown-toggle)");
    private final By dropdownParents = By.cssSelector("header li.dropdown");
    private final By dropdownLinks = By.cssSelector("li.dropdown a");

    // ================= ACTIONS =================
    public List<WebElement> getAllHeaderLinks() {
        return driver.findElements(headerLinks);
    }

    public List<WebElement> getAllDropdownParents() {
        return driver.findElements(dropdownParents);
    }

    public List<WebElement> getAllDropdownLinks(WebElement parent) {
        return parent.findElements(By.tagName("a"));
    }

    public void hover(WebElement element) {
        new Actions(driver).moveToElement(element).perform();
    }

    public void safeClick(WebElement element) {
        String main = driver.getWindowHandle();
        scrollIntoView(element);
        highlight(element);

        try {
            element.click();
        } catch (ElementClickInterceptedException e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        } catch (StaleElementReferenceException e) {
            // Jika element hilang karena reload, re-find tidak perlu klik lagi
            return;
        }

        try {
            Thread.sleep(1000);
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
            // Self-redirect → tunggu halaman reload
            try {
                wait.until(ExpectedConditions.stalenessOf(element));
            } catch (TimeoutException ignored) {
            }
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("header")));
        }
    }
}

