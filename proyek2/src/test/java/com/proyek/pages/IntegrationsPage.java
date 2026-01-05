package com.proyek.pages;

import com.proyek.base.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class IntegrationsPage extends BasePage {

    private final String integrationsUrl = "https://www.glpi-project.org/en/integrations/";

    public IntegrationsPage(WebDriver driver) {
        super(driver);
    }

    // ================= OPEN =================

    public void open() {
        driver.get(integrationsUrl);
        driver.manage().window().maximize();
        acceptCookieIfPresent();
        wait.until(ExpectedConditions.visibilityOfElementLocated(pageTitle));
    }

    // ================= LOCATORS =================

    private final By pageTitle = By.tagName("h1");

    // private final By searchInput = By.cssSelector("input[type='search']");
    private final By searchInput = By.cssSelector("input[type='search'], input[placeholder*='Search']");

    private final By searchButton = By.cssSelector("img[src*='Vector-26']");

    private final By integrationCards = By.cssSelector(".oxy-dynamic-list > div.ct-div-block");

    private final By integrationLinks = By.cssSelector(".oxy-dynamic-list a.ct-link-text[target='_blank']");

    public final By allFilterButton = By.cssSelector("button.wpgb-reset[name='toutes_reinit']");
    public final By apiFilterButton = By.xpath("//span[text()='API']/ancestor::div[contains(@class,'wpgb-button')]");

    private final By loadMoreButton = By.cssSelector("button.wpgb-load-more");

    // ================= BASIC =================

    public boolean isPageTitleVisible() {
        try {
            return wait.until(
                    ExpectedConditions.visibilityOfElementLocated(pageTitle)).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean areIntegrationCardsVisible() {
        try {
            return !wait.until(
                    ExpectedConditions.presenceOfAllElementsLocatedBy(integrationCards)).isEmpty();
        } catch (TimeoutException e) {
            return false;
        }
    }

    // ================= SEARCH =================

    public void searchIntegration(String keyword) {

        WebElement input = wait.until(
                ExpectedConditions.visibilityOfElementLocated(searchInput));

        WebElement button = wait.until(
                ExpectedConditions.elementToBeClickable(searchButton));

        int beforeCount = driver.findElements(integrationCards).size();

        input.clear();
        input.sendKeys(keyword);

        scrollIntoView(button);
        button.click();

        // Tunggu hasil search beneran berubah
        wait.until(driver -> driver.findElements(integrationCards).size() != beforeCount);
    }

    public int getVisibleCardCount() {
        return driver.findElements(integrationCards).size();
    }

    // ================= ACTION + VERIFY =================

    public boolean clickFirstIntegrationAndVerifyPage() {
        List<WebElement> links = wait.until(
                ExpectedConditions.presenceOfAllElementsLocatedBy(integrationLinks));

        WebElement first = links.get(0);
        scrollIntoView(first);
        highlight(first);

        String mainWindow = driver.getWindowHandle();

        first.click();

        wait.until(ExpectedConditions.numberOfWindowsToBe(2));

        String integrationWindow = driver.getWindowHandles()
                .stream()
                .filter(win -> !win.equals(mainWindow))
                .findFirst()
                .orElseThrow();

        driver.switchTo().window(integrationWindow);

        // ✅ VERIFICATION
        boolean validUrl = driver.getCurrentUrl().contains("/integration/");
        boolean titleVisible;

        try {
            titleVisible = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(By.tagName("h1"))).isDisplayed();
        } catch (TimeoutException e) {
            titleVisible = false;
        }

        driver.close();
        driver.switchTo().window(mainWindow);

        return validUrl && titleVisible;
    }

    public boolean atLeastOneCardContainsApiKeyword() {

        List<WebElement> cards = driver.findElements(integrationCards);

        for (WebElement card : cards) {
            String text = card.getText().toLowerCase();
            if (text.contains("api")) {
                return true;
            }
        }
        return false;
    }

    // Klik tombol filter dan tunggu card berubah
    public void clickFilterAndWait(By filterButton) {
        List<String> beforeTexts = getCardTexts(); // ambil teks card sebelum klik
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(filterButton));
        scrollIntoView(btn);
        btn.click();
        waitForCardsToChange(beforeTexts); // tunggu card berubah
    }

    private void waitForCardsToChange(List<String> beforeTexts) {
        wait.until(driver -> {
            List<WebElement> cards = driver.findElements(integrationCards);
            List<String> afterTexts = cards.stream().map(WebElement::getText).toList();
            if (afterTexts.size() != beforeTexts.size())
                return true; // jumlah card berubah
            for (int i = 0; i < afterTexts.size(); i++) {
                if (!afterTexts.get(i).equals(beforeTexts.get(i)))
                    return true; // isi card berubah
            }
            return false;
        });
    }

    private List<String> getCardTexts() {
        return driver.findElements(integrationCards).stream()
                .map(WebElement::getText)
                .toList();
    }

    public void clickLoadMoreArticles() {
        // Tunggu tombol muncul
        WebElement btn = wait.until(driver -> {
            List<WebElement> list = driver.findElements(loadMoreButton);
            for (WebElement el : list) {
                if (el.isDisplayed() && el.isEnabled())
                    return el;
            }
            return null;
        });

        if (btn == null) {
            System.out.println("Load More button not found or not clickable.");
            return;
        }

        // Scroll dulu
        scrollIntoView(btn);

        // Klik pakai JS supaya aman dari overlay / animasi
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);

        // Tunggu card baru muncul (jumlah card berubah)
        int beforeCount = driver.findElements(integrationCards).size();
        wait.until(driver -> driver.findElements(integrationCards).size() > beforeCount);
    }

}
