package com.proyek.pages.Tech;

import com.proyek.base.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.Set;

public class PricesPageTech extends BasePage {

    private final String pageUrl = "https://glpi-network.cloud/prices/";
    private String lastNavigatedUrl;

    public PricesPageTech(WebDriver driver) {
        super(driver);
    }

    // ================= OPEN =================
    public void open() {
        driver.get(pageUrl);
        driver.manage().window().maximize();
        acceptCookieIfPresent();
        wait.until(ExpectedConditions.visibilityOfElementLocated(pageTitle));
    }

    // ================= LOCATORS =================
    private final By pageTitle = By.xpath("//h1[contains(.,'Our Cloud Prices')]");

    // pricing buttons
    private final By publicCloudBtn = By.xpath(
            "//h4[text()='Public Cloud']/ancestor::div[contains(@class,'et_pb_pricing_table')]//a[contains(.,'Start')]");

    private final By privateCloudBtn = By.xpath(
            "//h4[normalize-space()='Private Cloud']" +
                    "/ancestor::div[contains(@class,'et_pb_pricing_table')]" +
                    "//a[contains(@class,'et_pb_pricing_table_button') and contains(.,'Contact')]");

    private final By customPricesBtn = By
            .xpath("//h4[text()='Custom prices']/ancestor::div[contains(@class,'et_pb_pricing_table')]//a");

    // calculator (IMPORTANT: LABEL, NOT INPUT)
    private final By publicCloudLabel = By.xpath("//span[contains(text(),'19 €')]/ancestor::label");

    private final By publicCloudQty = By.cssSelector(".calc_quantity_field_id_0 input");

    private final By totalPublicCloudValue = By.cssSelector("#total_field_id_3 .sub-item-value");

    private final By detailedDescriptionBtn = By.xpath("//a[contains(.,'Detailed description')]");

    private final By contactSalesBtn = By.xpath("//a[normalize-space()='CONTACT SALES']");

    private final By readMoreBtn = By.xpath("//a[normalize-space()='READ MORE']");

    // ================= VISIBILITY =================
    public boolean isPageTitleVisible() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(pageTitle)).isDisplayed();
    }

    // ================= CTA =================
    public void clickPublicCloudStart() {
        String startUrl = driver.getCurrentUrl();

        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(publicCloudBtn));
        scrollIntoView(btn);
        btn.click();

        // 1️⃣ TUNGGU URL BERUBAH
        wait.until(ExpectedConditions.not(
                ExpectedConditions.urlToBe(startUrl)));

        // 2️⃣ TUNGGU PAGE LOAD SELESAI
        wait.until(ExpectedConditions.jsReturnsValue(
                "return document.readyState === 'complete'"));

        // 3️⃣ SIMPAN URL AKHIR
        lastNavigatedUrl = driver.getCurrentUrl();
    }

    public void clickPrivateCloudContact() {
        clickNewTab(privateCloudBtn);
    }

    public void clickCustomPricesContact() {
        clickNewTab(customPricesBtn);
    }

    protected void clickNewTab(By locator) {

        String mainWindow = driver.getWindowHandle();
        Set<String> before = driver.getWindowHandles();

        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(locator));
        scrollIntoView(btn);
        highlight(btn);
        btn.click();

        // 1️⃣ tunggu tab baru
        wait.until(d -> d.getWindowHandles().size() > before.size());

        for (String win : driver.getWindowHandles()) {
            if (!win.equals(mainWindow)) {

                driver.switchTo().window(win);

                // 2️⃣ tunggu URL beneran berubah
                wait.until(d -> !d.getCurrentUrl().isEmpty()
                        && !d.getCurrentUrl().equals("about:blank"));

                // 3️⃣ tunggu BODY ada (cukup, jangan JS readyState)
                wait.until(ExpectedConditions.presenceOfElementLocated(
                        By.tagName("body")));

                // 4️⃣ simpan URL
                lastNavigatedUrl = driver.getCurrentUrl();

                // 5️⃣ close tab baru
                driver.close();
            }
        }

        driver.switchTo().window(mainWindow);
    }

    // ================= CALCULATOR =================
    public void enablePublicCloud(int qty) {

        WebElement label = wait.until(ExpectedConditions.elementToBeClickable(publicCloudLabel));
        scrollIntoView(label);
        label.click();

        WebElement qtyInput = wait.until(ExpectedConditions.visibilityOfElementLocated(publicCloudQty));
        qtyInput.sendKeys(Keys.CONTROL + "a");
        qtyInput.sendKeys(String.valueOf(qty));

        wait.until(d -> !getPublicCloudTotal().equals("€ 0.00"));
    }

    public String getPublicCloudTotal() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(totalPublicCloudValue))
                .getText()
                .trim();
    }

    public void clickDetailedDescription() {
        clickSameTabAndCapture(detailedDescriptionBtn);
    }

    public void clickContactSales() {
        clickSameTabAndCapture(contactSalesBtn);
    }

    public void clickReadMoreKnowledgeBase() {
        clickNewTab(readMoreBtn);
    }

    private void clickSameTabAndCapture(By locator) {

        String startUrl = driver.getCurrentUrl();

        WebElement el = wait.until(ExpectedConditions.elementToBeClickable(locator));
        scrollIntoView(el);
        highlight(el);
        el.click();

        // tunggu URL berubah
        wait.until(d -> !d.getCurrentUrl().equals(startUrl)
                && !d.getCurrentUrl().isBlank());

        // tunggu BODY render
        wait.until(ExpectedConditions.presenceOfElementLocated(By.tagName("body")));

        lastNavigatedUrl = driver.getCurrentUrl();

        // balik lagi
        driver.navigate().back();
        wait.until(ExpectedConditions.urlToBe(startUrl));
    }

    // ================= GETTER =================
    public String getLastNavigatedUrl() {
        return lastNavigatedUrl;
    }
}
