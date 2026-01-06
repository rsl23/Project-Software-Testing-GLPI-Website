package com.proyek.pages.index;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.proyek.base.BasePage;

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
    private final By resourcesTab = By.id("a_tab_resource_131218");
    private final By domainTab = By.id("a_tab_domain_131218");
    private final By closeInstanceTab = By.id("a_tab_danger_131218");
    
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
    public void clickInstanceLink() {
        WebElement link = wait.until(ExpectedConditions.elementToBeClickable(instanceLink));
        scrollIntoView(link);
        highlight(link);
        demoSleep(500);
        
        try {
            link.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", link);
        }
        demoSleep(1000);
    }

    public void clickResourcesTab() {
        WebElement tab = wait.until(ExpectedConditions.elementToBeClickable(resourcesTab));
        scrollIntoView(tab);
        highlight(tab);
        demoSleep(500);
        
        try {
            tab.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", tab);
        }
        demoSleep(1000);
    }

    public void clickDomainTab() {
        WebElement tab = wait.until(ExpectedConditions.elementToBeClickable(domainTab));
        scrollIntoView(tab);
        highlight(tab);
        demoSleep(500);
        
        try {
            tab.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", tab);
        }
        demoSleep(1000);
    }

    public void clickCloseInstanceTab() {
        WebElement tab = wait.until(ExpectedConditions.elementToBeClickable(closeInstanceTab));
        scrollIntoView(tab);
        highlight(tab);
        demoSleep(500);
        
        try {
            tab.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", tab);
        }
        demoSleep(1000);
    }

    public void clickUpgradePlan() {
        WebElement link = wait.until(ExpectedConditions.elementToBeClickable(upgradePlanLink));
        scrollIntoView(link);
        highlight(link);
        demoSleep(500);
        
        try {
            link.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", link);
        }
        demoSleep(1000);
    }

    public void clickAddPaymentMode() {
        WebElement link = wait.until(ExpectedConditions.elementToBeClickable(addPaymentModeLink));
        scrollIntoView(link);
        highlight(link);
        demoSleep(500);
        
        try {
            link.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", link);
        }
        demoSleep(1000);
    }

    public void clickMoreOptions() {
        WebElement div = wait.until(ExpectedConditions.elementToBeClickable(moreOptionsDiv));
        scrollIntoView(div);
        highlight(div);
        demoSleep(500);
        
        try {
            div.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", div);
        }
        demoSleep(1000);
    }

    public void clickAddAnotherInstance() {
        WebElement link = wait.until(ExpectedConditions.elementToBeClickable(addAnotherInstanceLink));
        scrollIntoView(link);
        highlight(link);
        demoSleep(500);
        
        try {
            link.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", link);
        }
        demoSleep(1000);
    }

    // ================= FORM FILLING - NEW INSTANCE =================
    public void selectService(String serviceValue) {
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(serviceDropdownSelection));
        scrollIntoView(dropdown);
        highlight(dropdown);
        demoSleep(500);
        
        try {
            // Click to open Select2 dropdown
            dropdown.click();
            demoSleep(500);
            
            // Use JavaScript to set value directly on hidden select element
            ((JavascriptExecutor) driver).executeScript(
                "arguments[0].value = arguments[1]; $(arguments[0]).trigger('change');", 
                driver.findElement(serviceDropdown), 
                serviceValue
            );
            demoSleep(500);
        } catch (Exception e) {
            System.out.println("Service selection error: " + e.getMessage());
        }
    }

    public void fillPassword(String password) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField));
        scrollIntoView(field);
        highlight(field);
        field.clear();
        field.sendKeys(password);
        demoSleep(500);
    }

    public void fillPassword2(String password) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(password2Field));
        scrollIntoView(field);
        highlight(field);
        field.clear();
        field.sendKeys(password);
        demoSleep(500);
    }

    public void fillSubdomain(String subdomain) {
        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(sldAndSubdomainField));
        scrollIntoView(field);
        highlight(field);
        field.clear();
        field.sendKeys(subdomain);
        demoSleep(500);
    }

    public void selectTld(String tldValue) {
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(tldDropdownSelection));
        scrollIntoView(dropdown);
        highlight(dropdown);
        demoSleep(500);
        
        try {
            // Click to open Select2 dropdown
            dropdown.click();
            demoSleep(500);
            
            // Use JavaScript to set value directly on hidden select element
            ((JavascriptExecutor) driver).executeScript(
                "arguments[0].value = arguments[1]; $(arguments[0]).trigger('change');", 
                driver.findElement(tldDropdown), 
                tldValue
            );
            demoSleep(500);
        } catch (Exception e) {
            System.out.println("TLD selection error: " + e.getMessage());
        }
    }

    public void clickCreate() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(createButton));
        scrollIntoView(button);
        highlight(button);
        demoSleep(500);
        
        try {
            button.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);
        }
        demoSleep(2000);
    }

    // ================= COMPLETE FORM =================
    public void fillNewInstanceForm(String service, String password, String subdomain, String tld) {
        selectService(service);
        fillPassword(password);
        fillPassword2(password);
        fillSubdomain(subdomain);
        selectTld(tld);
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
