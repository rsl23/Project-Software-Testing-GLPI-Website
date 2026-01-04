package com.proyek.pages;

import com.proyek.base.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class FaqPage extends BasePage {

    private final String faqUrl = "https://www.glpi-project.org/en/faq/";

    public FaqPage(WebDriver driver) {
        super(driver);
    }

    // ================= OPEN =================
    public void open() {
        driver.get(faqUrl);
        acceptCookieIfPresent();
        demoSleep(1200);
    }

    // ================= PAGE =================
    private final By pageTitle = By.xpath("//h1[contains(.,'Frequently Asked Questions')]");

    public boolean isPageVisible() {
        return isVisible(pageTitle);
    }

    // ================= DROPDOWN =================
    private final By inventoryDropdown = By.xpath("//h4[text()='Inventory']/ancestor::button");
    private final By notificationsDropdown = By.xpath("//h4[text()='Notifications and receiver']/ancestor::button");
    private final By groupsDropdown = By.xpath("//h4[text()='Groups and users']/ancestor::button");
    private final By customiseDropdown = By.xpath("//h4[text()='Customise my instance']/ancestor::button");
    private final By pluginsDropdown = By.xpath("//h4[text()='GLPI and its plugins']/ancestor::button");
    private final By authSSODropdown = By.xpath("//h4[text()='Authentication and SSO']/ancestor::button");
    private final By scimDropdown = By.xpath("//h4[text()='Configure SCIM']/ancestor::button");
    private final By webhookDropdown = By.xpath("//h4[text()='How to configure a webhook?']/ancestor::button");

    // ================= BUTTONS =================
    private final By inventoryButton = By.xpath("//a[contains(text(),'Inventory my IT equipment')]");
    private final By notificationsButton = By.xpath("//a[contains(text(),'All about notifications')]");
    private final By groupsButton = By.xpath("//a[contains(text(),'Manage my groups and my users')]");
    private final By customiseButton = By.xpath("//a[contains(text(),'Customise my instance')]");
    private final By glpiPluginsButton = By.xpath("//a[contains(text(),'See the catalogue')]");
    private final By authSSOButton = By.xpath("//a[contains(text(),'Authenticate my users')]");
    private final By scimButton = By.xpath("//a[contains(text(),'Import my users')]");
    private final By webhookButton = By.xpath("//a[contains(text(),'Configure my Webhooks')]");
    private final By viewFullFAQButton = By.xpath("//a[contains(text(),'View the full FAQ')]");
    private final By seePluginsFaqButton = By.id("link_button-364-104");
    private final By viewAllTestimonials = By.xpath("//a[contains(text(),'View All Testimonials')]");
    private final By exploreNowButton = By.id("link_button-389-104");
    private final By startNowButton = By.id("link_button-428-104");

    // ================= CONTACT FORM =================
    private final By lastName = By.name("your-name");
    private final By firstName = By.name("your-first-name");
    private final By company = By.name("societe");
    private final By country = By.name("pays");
    private final By email = By.name("your-email");
    private final By phone = By.name("your-phone");
    private final By message = By.name("your-message");
    private final By contactSubmit = By.cssSelector("input[type='submit'][value='Contact us']");

    // ================= NEWSLETTER =================
    private final By newsletterEmail = By.cssSelector("input[name='email']");
    private final By newsletterCheckbox = By.cssSelector("input[type='checkbox'][name='checkbox-75[]']");
    private final By newsletterSubmit = By.cssSelector("input.wpcf7-submit.btn-nl");

    // ================= UTIL =================
    private void safeClick(WebElement el) {
        scrollIntoView(el);
        highlight(el);
        demoSleep(400);
        try {
            el.click();
        } catch (Exception e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
        }
        demoSleep(800);
    }

    private void clickOpenNewTab(WebElement el) {
        String main = driver.getWindowHandle();
        safeClick(el);
        demoSleep(1500);
        for (String win : driver.getWindowHandles()) {
            if (!win.equals(main)) {
                driver.switchTo().window(win);
                demoSleep(1000);
                driver.close();
            }
        }
        driver.switchTo().window(main);
        wait.until(ExpectedConditions.visibilityOfElementLocated(pageTitle));
        demoSleep(600);
    }

    // ================= DROPDOWN ACTIONS =================
    public void openInventoryDropdown() {
        safeClick(wait.until(ExpectedConditions.elementToBeClickable(inventoryDropdown)));
    }

    public void openNotificationsDropdown() {
        safeClick(wait.until(ExpectedConditions.elementToBeClickable(notificationsDropdown)));
    }

    public void openGroupsDropdown() {
        safeClick(wait.until(ExpectedConditions.elementToBeClickable(groupsDropdown)));
    }

    public void openCustomiseDropdown() {
        safeClick(wait.until(ExpectedConditions.elementToBeClickable(customiseDropdown)));
    }

    public void openPluginsDropdown() {
        safeClick(wait.until(ExpectedConditions.elementToBeClickable(pluginsDropdown)));
    }

    public void openAuthSSODropdown() {
        safeClick(wait.until(ExpectedConditions.elementToBeClickable(authSSODropdown)));
    }

    public void openScimDropdown() {
        safeClick(wait.until(ExpectedConditions.elementToBeClickable(scimDropdown)));
    }

    public void openWebhookDropdown() {
        safeClick(wait.until(ExpectedConditions.elementToBeClickable(webhookDropdown)));
    }

    // ================= BUTTON ACTIONS =================
    public void clickInventoryButton() {
        clickOpenNewTab(wait.until(ExpectedConditions.elementToBeClickable(inventoryButton)));
    }

    public void clickNotificationsButton() {
        clickOpenNewTab(wait.until(ExpectedConditions.elementToBeClickable(notificationsButton)));
    }

    public void clickGroupsButton() {
        clickOpenNewTab(wait.until(ExpectedConditions.elementToBeClickable(groupsButton)));
    }

    public void clickCustomiseButton() {
        clickOpenNewTab(wait.until(ExpectedConditions.elementToBeClickable(customiseButton)));
    }

    public void clickGlpiPluginsButton() {
        clickOpenNewTab(wait.until(ExpectedConditions.elementToBeClickable(glpiPluginsButton)));
    }

    public void clickAuthSSOButton() {
        clickOpenNewTab(wait.until(ExpectedConditions.elementToBeClickable(authSSOButton)));
    }

    public void clickScimButton() {
        clickOpenNewTab(wait.until(ExpectedConditions.elementToBeClickable(scimButton)));
    }

    public void clickWebhookButton() {
        clickOpenNewTab(wait.until(ExpectedConditions.elementToBeClickable(webhookButton)));
    }

    public void clickViewFullFAQ() {
        clickOpenNewTab(wait.until(ExpectedConditions.elementToBeClickable(viewFullFAQButton)));
    }

    public void clickSeePluginsFaq() {
        clickOpenNewTab(wait.until(ExpectedConditions.elementToBeClickable(seePluginsFaqButton)));
    }

    public void clickViewAllTestimonials() {
        clickOpenNewTab(wait.until(ExpectedConditions.elementToBeClickable(viewAllTestimonials)));
    }

    public void clickExploreNow() {
        String main = driver.getWindowHandle();
        safeClick(wait.until(ExpectedConditions.elementToBeClickable(exploreNowButton)));
        demoSleep(1500); // lihat halaman features sebentar
        driver.navigate().back();
        wait.until(ExpectedConditions.visibilityOfElementLocated(pageTitle));
        demoSleep(600);
    }

    public void clickStartNow() {
        clickOpenNewTab(wait.until(ExpectedConditions.elementToBeClickable(startNowButton)));
    }

    // ================= CONTACT FORM =================
    public void fillContactForm() {
        driver.findElement(lastName).clear();
        driver.findElement(lastName).sendKeys("Doe");
        driver.findElement(firstName).clear();
        driver.findElement(firstName).sendKeys("John");
        driver.findElement(company).clear();
        driver.findElement(company).sendKeys("ACME Corp");
        driver.findElement(country).clear();
        driver.findElement(country).sendKeys("Indonesia");
        driver.findElement(email).clear();
        driver.findElement(email).sendKeys("john.doe@test.com");
        driver.findElement(phone).clear();
        driver.findElement(phone).sendKeys("08123456789");
        driver.findElement(message).clear();
        driver.findElement(message).sendKeys("Need FAQ help");

        WebElement submit = wait.until(ExpectedConditions.presenceOfElementLocated(contactSubmit));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", submit);
        demoSleep(300);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submit);
        demoSleep(2000); // jeda lihat efek klik
    }

    // ================= NEWSLETTER =================
    public void fillNewsletter(String emailInput) {
        WebElement emailEl = wait.until(ExpectedConditions.visibilityOfElementLocated(newsletterEmail));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", emailEl);
        demoSleep(500);
        emailEl.clear();
        emailEl.sendKeys(emailInput);

        WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(newsletterCheckbox));
        if (!checkbox.isSelected()) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", checkbox);
            demoSleep(300);
        }

        WebElement submit = wait.until(ExpectedConditions.presenceOfElementLocated(newsletterSubmit));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", submit);
        demoSleep(400);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submit);
        demoSleep(1500);
    }

    // ================= FULL FLOW =================
    public void testAllElementsComplete() {
        isPageVisible();
        openInventoryDropdown();
        clickInventoryButton();
        openNotificationsDropdown();
        clickNotificationsButton();
        openGroupsDropdown();
        clickGroupsButton();
        openCustomiseDropdown();
        clickCustomiseButton();
        openPluginsDropdown();
        clickGlpiPluginsButton();
        openAuthSSODropdown();
        clickAuthSSOButton();
        openScimDropdown();
        clickScimButton();
        openWebhookDropdown();
        clickWebhookButton();

        clickViewFullFAQ();
        clickSeePluginsFaq();
        fillContactForm();
        clickViewAllTestimonials();

        clickExploreNow();
        clickStartNow();

        fillNewsletter("demo@test.com");
    }
}
