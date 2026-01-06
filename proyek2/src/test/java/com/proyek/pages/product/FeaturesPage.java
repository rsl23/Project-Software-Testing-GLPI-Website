package com.proyek.pages.product;

import com.proyek.base.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class FeaturesPage extends BasePage {

    private final String featuresUrl = "https://www.glpi-project.org/en/features/";

    public FeaturesPage(WebDriver driver) {
        super(driver);
    }

    // ================= URL =================
    public void open() {
        driver.get(featuresUrl);
        driver.manage().window().maximize();
        acceptCookieIfPresent();
        wait.until(ExpectedConditions.visibilityOfElementLocated(pageTitle));
    }

    // ================= LOCATORS =================
    private final By pageTitle = By.tagName("h1");

    // CTA buttons
    private final By discoverNowBtn = By.id("link_text-9-432322");
    private final By freeInstanceBtn = By.id("link_button-390-432322");

    // Accordion
    private By accordionHeader(String title) {
        return By.xpath("//h4[normalize-space()='" + title + "']/ancestor::button");
    }

    private By accordionBody(String bodyId) {
        return By.id(bodyId);
    }

    // ================= VISIBILITY =================
    public boolean isPageTitleVisible() {
        return isVisible(pageTitle);
    }

    public boolean isDiscoverNowVisible() {
        return isVisible(discoverNowBtn);
    }

    public boolean isFreeInstanceVisible() {
        return isVisible(freeInstanceBtn);
    }

    public boolean isAccordionVisible(String title) {
        return isVisible(accordionHeader(title));
    }

    // ================= ACTIONS (WITH VERIFICATION) =================
    public String clickDiscoverNowAndGetUrl() {
        return clickAndGetNewTabUrl(discoverNowBtn);
    }

    public String clickFreeInstanceAndGetUrl() {
        return clickAndGetNewTabUrl(freeInstanceBtn);
    }

    // ================= ACTIONS (CLICK ONLY) =================
    public void clickDiscoverNowAndClose() {
        clickAndReturn(discoverNowBtn);
    }

    public void clickFreeInstanceAndClose() {
        clickAndReturn(freeInstanceBtn);
    }

    public void openAccordion(String title) {
        WebElement header = wait.until(ExpectedConditions.elementToBeClickable(accordionHeader(title)));
        scrollIntoView(header);
        highlight(header);
        header.click();
    }

    public boolean isAccordionBodyVisible(String bodyId) {
        return isVisible(accordionBody(bodyId));
    }

    // ================= HELPERS =================

    private String clickAndGetNewTabUrl(By locator) {
        String main = driver.getWindowHandle();

        WebElement el = wait.until(ExpectedConditions.elementToBeClickable(locator));
        scrollIntoView(el);
        highlight(el);

        try {
            el.click();
        } catch (ElementClickInterceptedException e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
        }

        wait.until(ExpectedConditions.numberOfWindowsToBe(2));

        for (String win : driver.getWindowHandles()) {
            if (!win.equals(main)) {
                driver.switchTo().window(win);
                String url = driver.getCurrentUrl();
                driver.close();
                driver.switchTo().window(main);
                return url;
            }
        }
        return "";
    }

    private void clickAndReturn(By locator) {
        String main = driver.getWindowHandle();

        WebElement el = wait.until(ExpectedConditions.elementToBeClickable(locator));
        scrollIntoView(el);
        highlight(el);

        try {
            el.click();
        } catch (ElementClickInterceptedException e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
        }

        wait.until(ExpectedConditions.numberOfWindowsToBe(2));
        closeNewTab(main);
    }

    private void closeNewTab(String main) {
        for (String win : driver.getWindowHandles()) {
            if (!win.equals(main)) {
                driver.switchTo().window(win);
                driver.close();
            }
        }
        driver.switchTo().window(main);
    }
}

