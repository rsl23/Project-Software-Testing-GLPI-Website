package com.proyek.pages.resources;

import com.proyek.base.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;

public class PartnerPage extends BasePage {

    public final String pageUrl = "https://www.glpi-project.org/en/partners/";

    public PartnerPage(WebDriver driver) {
        super(driver);
    }

    // ================= OPEN =================
    public void open() {
        driver.get(pageUrl);
        acceptCookieIfPresent();
        waitUntilDynamicListLoaded();
    }

    // ================= LOCATORS =================
    public final By searchInput = By.cssSelector("input[name='search_test']");
    public final By clearButton = By.cssSelector("button.wpgb-clear-button");
    public final By statusDropdown = By.cssSelector("select[name='statut_partenaire']");
    public final By countryDropdown = By.cssSelector("select[name='liste_pays']");
    public final By dynamicList = By.cssSelector("#_dynamic_list-66-432383 .wpgb-card");

    // ================= ACTIONS =================
    public void typeSearch(String keyword) {
        int retries = 3;
        while (retries > 0) {
            try {
                WebElement el = wait.until(ExpectedConditions.elementToBeClickable(searchInput));
                scrollIntoView(el);
                highlight(el); // highlight sekali sebelum interaksi
                el.clear();
                el.sendKeys(keyword);

                // tunggu hasil search muncul
                waitUntilDynamicListLoaded();
                return; // berhasil
            } catch (StaleElementReferenceException e) {
                retries--;
                if (retries == 0)
                    throw e;
            }
        }
    }

    public void clickClear() {
        WebElement el = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(clearButton));
        safeClick(el);
        waitUntilDynamicListLoaded();
    }

    public void selectStatus(String status) {
        int retries = 3;
        while (retries > 0) {
            try {
                WebElement el = new WebDriverWait(driver, Duration.ofSeconds(10))
                        .until(ExpectedConditions.elementToBeClickable(statusDropdown));
                scrollIntoView(el);
                highlight(el);
                safeClick(el); // klik dulu biar dropdown muncul

                Select select = new Select(el);
                boolean found = false;
                for (WebElement opt : select.getOptions()) {
                    if (!opt.isEnabled())
                        continue;
                    if (opt.getText().trim().equalsIgnoreCase(status.trim())) {
                        select.selectByVisibleText(opt.getText());
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    System.out.println("WARNING: Status option '" + status + "' tidak tersedia atau disabled!");
                }

                waitUntilDynamicListLoaded();
                return;
            } catch (StaleElementReferenceException e) {
                retries--;
                if (retries == 0)
                    throw e;
            }
        }
    }

    public void selectCountry(String country) {
        int retries = 3;
        while (retries > 0) {
            try {
                WebElement el = new WebDriverWait(driver, Duration.ofSeconds(10))
                        .until(ExpectedConditions.elementToBeClickable(countryDropdown));
                scrollIntoView(el);
                highlight(el);
                safeClick(el); // klik dulu biar dropdown muncul

                Select select = new Select(el);
                boolean found = false;
                for (WebElement opt : select.getOptions()) {
                    if (!opt.isEnabled())
                        continue;
                    if (opt.getText().trim().equalsIgnoreCase(country.trim())) {
                        select.selectByVisibleText(opt.getText());
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    System.out.println("WARNING: Country option '" + country + "' tidak tersedia!");
                }

                waitUntilDynamicListLoaded();
                return;
            } catch (StaleElementReferenceException e) {
                retries--;
                if (retries == 0)
                    throw e;
            }
        }
    }

    public String clickSeeWebsite(String partnerName) {
        int retries = 3;
        while (retries > 0) {
            try {
                waitUntilDynamicListLoaded();
                List<WebElement> cards = driver.findElements(dynamicList);
                for (WebElement card : cards) {
                    if (card.getText().toLowerCase().contains(partnerName.toLowerCase())) {
                        WebElement seeWebsiteBtn = card
                                .findElement(By.xpath(".//a[contains(text(),'See the website')]"));
                        new WebDriverWait(driver, Duration.ofSeconds(10))
                                .until(ExpectedConditions.elementToBeClickable(seeWebsiteBtn));
                        safeClick(seeWebsiteBtn);

                        String originalWindow = driver.getWindowHandle();

                        // tunggu tab baru muncul
                        new WebDriverWait(driver, Duration.ofSeconds(10))
                                .until(d -> d.getWindowHandles().size() > 1);

                        String newTab = driver.getWindowHandles().stream()
                                .filter(w -> !w.equals(originalWindow))
                                .findFirst()
                                .orElseThrow(() -> new RuntimeException("Tab partner tidak muncul"));

                        driver.switchTo().window(newTab);

                        // tunggu halaman benar-benar load
                        new WebDriverWait(driver, Duration.ofSeconds(15))
                                .until(d -> ((JavascriptExecutor) d).executeScript("return document.readyState")
                                        .equals("complete"));

                        String partnerUrl = driver.getCurrentUrl();

                        driver.close();
                        driver.switchTo().window(originalWindow);

                        return partnerUrl;
                    }
                }
                throw new NoSuchElementException(
                        "Partner '" + partnerName + "' tidak ditemukan atau tidak ada tombol 'See the website'");
            } catch (StaleElementReferenceException e) {
                retries--;
                if (retries == 0)
                    throw e;
            }
        }
        return null;
    }

    public String clickBecomePartnerButton() {
        By buttonLocator = By.id("link_button-635-432383");
        WebElement button = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(buttonLocator));

        button.click();

        new WebDriverWait(driver, Duration.ofSeconds(15))
                .until(d -> ((JavascriptExecutor) d)
                        .executeScript("return document.readyState").equals("complete"));

        String url = driver.getCurrentUrl();

        // Kembali ke halaman partner agar dynamic list tetap bisa dipakai
        driver.navigate().back();
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(d -> d.getCurrentUrl().contains("/partners/"));

        return url;
    }

    // ================= GETTERS =================
    public String getSearchValue() {
        return driver.findElement(searchInput).getAttribute("value");
    }

    public String getSelectedStatus() {
        Select select = new Select(driver.findElement(statusDropdown));
        return select.getFirstSelectedOption().getText();
    }

    public String getSelectedCountry() {
        Select select = new Select(driver.findElement(countryDropdown));
        return select.getFirstSelectedOption().getText();
    }

    public boolean isDynamicListVisible() {
        List<WebElement> cards = driver.findElements(dynamicList);
        return cards.size() > 0;
    }

    public boolean isPartnerCardVisible(String partnerName) {
        waitUntilDynamicListLoaded();
        List<WebElement> cards = driver.findElements(dynamicList);
        for (WebElement card : cards) {
            if (card.getText().toLowerCase().contains(partnerName.toLowerCase())) {
                highlight(card);
                return true;
            }
        }
        return false;
    }

    // ================= HELPERS =================
    public void safeClick(WebElement el) {
        scrollIntoView(el);
        highlight(el);
        try {
            el.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
        }
    }

    public void waitUntilDynamicListLoaded() {
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(dynamicList));
    }
}

