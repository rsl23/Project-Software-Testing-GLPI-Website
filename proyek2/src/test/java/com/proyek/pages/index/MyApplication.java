package com.proyek.pages.index;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.proyek.base.BasePage;

import java.time.Duration;

public class MyApplication extends BasePage {
    private final String myApplicationUrl = "https://myaccount.glpi-network.cloud/index.php?mode=instances";

    public MyApplication(WebDriver driver) {
        super(driver);
    }

    // ================= URL =================
    public void open() {
        driver.get(myApplicationUrl);
        driver.manage().window().maximize();
        acceptCookieIfPresent();
        waitForPageLoad();
    }

    // ================= LOCATORS =================
    // Instance link
    private final By instanceLink = By.cssSelector("a.linktoinstance[target='blankinstance']");

    // Tabs
    private final By resourcesTab = By.xpath("//a[contains(@id, 'a_tab_resource_')]");
    private final By domainTab = By.xpath("//a[contains(@id,'a_tab_domain_')]");
    private final By closeInstanceTab = By.xpath("//a[contains(@id,'a_tab_danger_')]");

    // Action links
    private final By upgradePlanLink = By.xpath("//a[contains(@href, 'action=changeplan')]");
    private final By addPaymentModeLink = By.xpath("//a[contains(@href, 'mode=registerpaymentmode')]");
    private final By moreOptionsDiv = By.cssSelector("div.boxresource.small");
    private final By addAnotherInstanceLink = By.id("addanotherinstance");

    // Form elements for new instance
    private final By serviceDropdown = By.id("service");
    private final By serviceDropdownSelection = By.id("select2-service-container");
    private final By passwordField = By.name("password");
    private final By password2Field = By.name("password2");
    private final By sldAndSubdomainField = By.id("sldAndSubdomain");
    private final By tldDropdown = By.id("tldid");
    private final By tldDropdownSelection = By.id("select2-tldid-container");
    private final By createButton = By.cssSelector("input[name='changeplan'][value='Create']");

    // ================= VISIBILITY =================
    public boolean isInstanceLinkVisible() {
        return isVisible(instanceLink);
    }

    public boolean isResourcesTabVisible() {
        return isVisible(resourcesTab);
    }

    public boolean isDomainTabVisible() {
        return isVisible(domainTab);
    }

    public boolean isCloseInstanceTabVisible() {
        return isVisible(closeInstanceTab);
    }

    public boolean isUpgradePlanLinkVisible() {
        return isVisible(upgradePlanLink);
    }

    public boolean isAddPaymentModeLinkVisible() {
        return isVisible(addPaymentModeLink);
    }

    public boolean isMoreOptionsDivVisible() {
        return isVisible(moreOptionsDiv);
    }

    public boolean isAddAnotherInstanceLinkVisible() {
        return isVisible(addAnotherInstanceLink);
    }

    // ================= ACTIONS - LINKS & TABS =================
    private void safeClick(WebElement element) {
        scrollIntoView(element);
        highlight(element);
        demoSleep(500);
        try {
            element.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        }
        demoSleep(1000);
    }

    public void clickInstanceLink() {
        WebElement link = wait.until(ExpectedConditions.elementToBeClickable(instanceLink));
        safeClick(link);
    }

    public void clickResourcesTab() {
        // Cari tab Resources berdasarkan teks, bukan ID
        WebElement tab = new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//a[contains(text(),'Resources')]")));

        safeClick(tab);

        // Tunggu konten tab muncul
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.cssSelector("div.areaforresources")));
    }

    public void clickDomainTab() {
        // ambil contractId dari hidden input di halaman
        String contractId = driver.findElement(By.cssSelector("input[name='contractid']")).getAttribute("value");

        // bangun locator sesuai contractId
        By domainTabLocator = By.id("a_tab_domain_" + contractId);

        // tunggu sampai clickable, lalu klik
        WebElement tab = wait.until(ExpectedConditions.elementToBeClickable(domainTabLocator));
        safeClick(tab);
    }

    public void clickCloseInstanceTab() {
        // ambil contract ID dari input hidden
        String contractId = driver.findElement(By.name("contractid")).getAttribute("value");

        // buat locator dinamis
        By closeInstanceTab = By.id("a_tab_danger_" + contractId);

        // tunggu sampai bisa diklik
        WebElement tab = wait.until(ExpectedConditions.elementToBeClickable(closeInstanceTab));

        // klik tab
        safeClick(tab);
    }

    public void clickUpgradePlan() {
        WebElement link = wait.until(ExpectedConditions.elementToBeClickable(upgradePlanLink));
        safeClick(link);
    }

    public void clickAddPaymentMode() {
        WebElement link = wait.until(ExpectedConditions.elementToBeClickable(addPaymentModeLink));
        safeClick(link);
    }

    public void clickMoreOptions() {
        WebElement div = wait.until(ExpectedConditions.elementToBeClickable(moreOptionsDiv));
        safeClick(div);
    }

    public void clickAddAnotherInstance() {
        WebElement link = wait.until(ExpectedConditions.elementToBeClickable(addAnotherInstanceLink));
        safeClick(link);
    }

    // ================= FORM FILLING - NEW INSTANCE =================
    public void selectService(String serviceValue) {
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(serviceDropdownSelection));
        scrollIntoView(dropdown);
        highlight(dropdown);
        dropdown.click(); // buka dropdown
        demoSleep(500);

        // tunggu list muncul
        By serviceOption = By.xpath(
                "//li[contains(@class,'select2-results__option') and starts-with(normalize-space(.), 'GLPI 11')]");

        WebElement option = wait.until(ExpectedConditions.elementToBeClickable(serviceOption));
        scrollIntoView(option);
        highlight(option);
        option.click();
        demoSleep(500);
    }

    public void selectTld(String tldValue) {
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(tldDropdownSelection));
        scrollIntoView(dropdown);
        highlight(dropdown);
        dropdown.click(); // buka dropdown
        demoSleep(500);

        // tunggu list muncul
        By tldOption = By
                .xpath("//li[contains(@class,'select2-results__option') and contains(text(),'" + tldValue + "')]");
        WebElement option = wait.until(ExpectedConditions.elementToBeClickable(tldOption));
        scrollIntoView(option);
        highlight(option);
        option.click();
        demoSleep(500);
    }

    public void fillPassword(String password) {
        try {
            WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField));
            scrollIntoView(field);
            highlight(field);
            field.clear();
            field.sendKeys(password);
            demoSleep(500);
        } catch (Exception e) {
            System.out.println("Password field not found: " + e.getMessage());
        }
    }

    public void fillPassword2(String password) {
        try {
            WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(password2Field));
            scrollIntoView(field);
            highlight(field);
            field.clear();
            field.sendKeys(password);
            demoSleep(500);
        } catch (Exception e) {
            System.out.println("Password2 field not found: " + e.getMessage());
        }
    }

    public void fillSubdomain(String subdomain) {
        try {
            WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(sldAndSubdomainField));
            scrollIntoView(field);
            highlight(field);
            field.clear();
            field.sendKeys(subdomain);
            demoSleep(500);
        } catch (Exception e) {
            System.out.println("Subdomain field not found: " + e.getMessage());
        }
    }

    public void clickCreate() {
        try {
            WebElement button = wait.until(ExpectedConditions.elementToBeClickable(createButton));
            safeClick(button);
        } catch (Exception e) {
            System.out.println("Create button not found: " + e.getMessage());
        }
    }

    // ================= COMPLETE FORM =================
    public void fillNewInstanceForm(String service, String password, String subdomain, String tld) {
        selectService(service);
        fillPassword(password);
        fillPassword2(password);
        fillSubdomain(subdomain);
        selectTld(tld);
    }

    // ================= WAIT FOR INSTALLATION =================
    public void waitForInstallationComplete() {
        try {
            // tunggu waitMask hilang
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.id("waitMask")));
            demoSleep(1000);

            // ambil URL instance baru
            WebElement instanceLink = driver.findElement(By.cssSelector("p.well a"));
            String instanceUrl = instanceLink.getAttribute("href");
            System.out.println("New instance ready at: " + instanceUrl);
        } catch (Exception e) {
            System.out.println("Installation complete page not detected: " + e.getMessage());
        }
    }

    // ================= HELPERS =================
    private void waitForPageLoad() {
        wait.until(webDriver -> ((JavascriptExecutor) webDriver)
                .executeScript("return document.readyState").equals("complete"));
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public boolean isOnMyApplicationPage() {
        return getCurrentUrl().contains("mode=instances");
    }
}
