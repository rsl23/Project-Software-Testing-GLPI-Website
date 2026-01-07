package com.proyek.pages.Tech;

import com.proyek.base.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;
import java.util.List;
import java.util.Set;

public class HomePageTech extends BasePage {

    private final String pageUrl = "https://glpi-network.cloud/";
    private String lastNavigatedUrl;

    public HomePageTech(WebDriver driver) {
        super(driver);
    }

    // ================= OPEN =================
    public void open() {
        driver.get(pageUrl);
        driver.manage().window().maximize();
        acceptCookieIfPresent();
        wait.until(ExpectedConditions.visibilityOfElementLocated(heroTitle));
    }

    // ================= LOCATORS =================
    private final By heroTitle = By.xpath("//h1[contains(.,'GLPI Network Cloud')]");
    private final By heroSubtitle = By.xpath("//h2[contains(.,'complete professional')]");
    private final By freeDemoBtn = By.xpath("//a[contains(text(),'FREE DEMO')]");
    private final By startTrialBtn = By.xpath("//a[normalize-space()='START TRIAL']");
    private final By pricingTables = By.cssSelector(".et_pb_pricing_table");
    private final By pricingButtons = By.cssSelector(".et_pb_pricing_table_button");
    private final By knowledgeBaseBtn = By.xpath("//a[contains(text(),'READ MORE')]");
    private final By iframeForm = By.cssSelector("iframe[src*='sg-form']");
    private final By reviewBadges = By.cssSelector("a[target='_blank'] img");

    // ================= VISIBILITY =================
    public boolean isHeroVisible() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(heroTitle)).isDisplayed();
    }

    public boolean isSubtitleVisible() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(heroSubtitle)).isDisplayed();
    }

    public int getPricingTableCount() {
        return driver.findElements(pricingTables).size();
    }

    public boolean isIframeFormVisible() {
        WebElement iframe = wait.until(ExpectedConditions.presenceOfElementLocated(iframeForm));
        scrollIntoView(iframe);
        return iframe.isDisplayed();
    }

    public int getReviewBadgeCount() {
        return driver.findElements(reviewBadges).size();
    }

    // ================= ACTIONS =================
    public void clickFreeDemo() {
        clickAndCaptureUrl(freeDemoBtn);
    }

    public void clickStartTrial() {
        clickAndCaptureUrl(startTrialBtn);
    }

    public void clickKnowledgeBase() {
        clickAndCaptureUrl(knowledgeBaseBtn);
    }

    public void clickAllPricingButtons() {
        int totalButtons = driver.findElements(pricingButtons).size();

        for (int i = 0; i < totalButtons; i++) {
            List<WebElement> buttons = wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(pricingButtons));

            WebElement btn = buttons.get(i);
            clickAndCaptureUrl(btn);
        }
    }

    // ================= CORE NAVIGATION =================
    private void clickAndCaptureUrl(By locator) {
        WebElement el = wait.until(ExpectedConditions.elementToBeClickable(locator));
        clickAndCaptureUrl(el);
    }

    private void clickAndCaptureUrl(WebElement el) {
        String mainWindow = driver.getWindowHandle();
        String startUrl = driver.getCurrentUrl();
        Set<String> windowsBefore = driver.getWindowHandles();

        scrollIntoView(el);
        highlight(el);

        try {
            el.click();
        } catch (ElementClickInterceptedException e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
        }

        try {
            wait.withTimeout(Duration.ofSeconds(8))
                    .until(d -> d.getWindowHandles().size() != windowsBefore.size()
                            || !d.getCurrentUrl().equals(startUrl));
        } catch (TimeoutException ignored) {
        }

        // ========== NEW TAB ==========
        if (driver.getWindowHandles().size() > windowsBefore.size()) {
            for (String win : driver.getWindowHandles()) {
                if (!win.equals(mainWindow)) {
                    driver.switchTo().window(win);

                    // 🔐 VALIDASI PAGE LOAD
                    wait.until(ExpectedConditions.jsReturnsValue(
                            "return document.readyState === 'complete'"));

                    lastNavigatedUrl = driver.getCurrentUrl();

                    driver.close();
                }
            }
            driver.switchTo().window(mainWindow);
        }
        // ========== SAME TAB ==========
        else if (!driver.getCurrentUrl().equals(startUrl)) {

            // 🔐 VALIDASI PAGE LOAD
            wait.until(ExpectedConditions.jsReturnsValue(
                    "return document.readyState === 'complete'"));

            lastNavigatedUrl = driver.getCurrentUrl();

            driver.navigate().back();
            wait.until(ExpectedConditions.urlToBe(startUrl));
        }
    }

    // ================= GETTER =================
    public String getLastNavigatedUrl() {
        return lastNavigatedUrl;
    }
}
