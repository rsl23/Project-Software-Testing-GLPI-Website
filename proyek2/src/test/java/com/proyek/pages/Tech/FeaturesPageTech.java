package com.proyek.pages.Tech;

import com.proyek.base.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;
import java.util.Set;

public class FeaturesPageTech extends BasePage {

    private final String pageUrl = "https://glpi-network.cloud/features/";
    private String lastNavigatedUrl;

    public FeaturesPageTech(WebDriver driver) {
        super(driver);
    }

    // ================= OPEN =================
    public void open() {
        driver.get(pageUrl);
        driver.manage().window().maximize();
        acceptCookieIfPresent();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(pageTitle));
    }

    // ================= LOCATORS =================
    private final By pageTitle = By.xpath("//h1[contains(.,'GLPI Network Cloud VS on-premise')]");

    private final By meetCloudTitle = By.xpath("//h2[contains(.,'Meet GLPI Network Cloud')]");

    private final By comparisonTable = By.cssSelector("table.wptb-preview-table");

    private final By downloadGuideBtn = By.xpath("//a[contains(.,'DOWNLOAD GLPI FEATURES GUIDE')]");

    private final By startTrialBtn = By.xpath("//a[normalize-space()='START TRIAL']");

    // ================= VISIBILITY =================
    public boolean isPageTitleVisible() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(pageTitle)).isDisplayed();
    }

    public boolean isMeetCloudSectionVisible() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(meetCloudTitle)).isDisplayed();
    }

    public boolean isComparisonTableVisible() {
        WebElement table = wait.until(ExpectedConditions.visibilityOfElementLocated(comparisonTable));
        scrollIntoView(table);
        return table.isDisplayed();
    }

    // ================= ACTIONS =================
    public void clickDownloadGuide() {
        clickAndValidateNavigation(downloadGuideBtn, By.tagName("body"));
    }

    public void clickStartTrial() {
        clickAndValidateNavigation(startTrialBtn, By.tagName("body"));
    }

    // ================= CORE NAV (FIXED) =================
    private void clickAndValidateNavigation(By locator, By targetValidationElement) {

        String mainWindow = driver.getWindowHandle();
        String startUrl = driver.getCurrentUrl();
        Set<String> before = driver.getWindowHandles();

        WebElement el = wait.until(ExpectedConditions.elementToBeClickable(locator));
        scrollIntoView(el);
        highlight(el);
        el.click();

        // ====== WAIT TAB / PAGE CHANGE ======
        wait.withTimeout(Duration.ofSeconds(10)).until(d -> d.getWindowHandles().size() > before.size()
                || !d.getCurrentUrl().equals(startUrl));

        // ====== NEW TAB ======
        if (driver.getWindowHandles().size() > before.size()) {
            for (String win : driver.getWindowHandles()) {
                if (!win.equals(mainWindow)) {
                    driver.switchTo().window(win);

                    waitForTargetPage(targetValidationElement);

                    lastNavigatedUrl = driver.getCurrentUrl();
                    driver.close();
                }
            }
            driver.switchTo().window(mainWindow);
        }
        // ====== SAME TAB ======
        else {
            waitForTargetPage(targetValidationElement);

            lastNavigatedUrl = driver.getCurrentUrl();
            driver.navigate().back();
            wait.until(ExpectedConditions.urlToBe(startUrl));
        }
    }

    // ================= PAGE LOAD VALIDATION =================
    private void waitForTargetPage(By validationElement) {

        // DOM ready
        wait.until(ExpectedConditions.jsReturnsValue(
                "return document.readyState === 'complete'"));

        // Konten utama benar-benar muncul
        wait.until(ExpectedConditions.presenceOfElementLocated(validationElement));
    }

    // ================= GETTER =================
    public String getLastNavigatedUrl() {
        return lastNavigatedUrl;
    }
}
