package com.proyek.pages;

import com.proyek.base.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.List;

public class DownloadPage extends BasePage {

    private final String downloadUrl = "https://www.glpi-project.org/en/downloads/";

    public DownloadPage(WebDriver driver) {
        super(driver);
    }

    // ================= OPEN =================
    public void open() {
        driver.get(downloadUrl);
        acceptCookieIfPresent();
        demoSleep(1200);
    }

    // ================= PAGE TITLE =================
    private final By pageTitle = By.xpath("//h1[contains(.,'Download')]");

    public boolean isPageVisible() {
        return isVisible(pageTitle);
    }

    // ================= LINKS =================
    private final By downloadVersion = By.xpath("//a[.//div[contains(text(),'Download version')]]");
    private final By readDocumentation = By.linkText("Read the documentation");
    private final By checkSourceCode = By.linkText("Check the source code");
    private final By contactPartner = By.linkText("Contact one of our partners");
    private final By browseTestimonials = By.cssSelector("a[href*='youtube.com/playlist']");
    private final By exploreNow = By.cssSelector("section a[href*='/features/']");
    private final By startNow = By.xpath("//a[contains(text(),'Start Now')]");
    private final By newsletterEmail = By.cssSelector("input[name='email']");
    private final By newsletterCheckbox = By.cssSelector("input[type='checkbox'][name='checkbox-75[]']");
    private final By newsletterSubmit = By.cssSelector("input.wpcf7-submit");

    // ================= CONTACT FORM =================
    private final By lastName = By.name("your-name");
    private final By firstName = By.name("your-first-name");
    private final By company = By.name("societe");
    private final By country = By.name("pays");
    private final By email = By.name("your-email");
    private final By phone = By.name("your-phone");
    private final By message = By.name("your-message");
    private final By contactSubmit = By.cssSelector("input[type='submit'][value='Contact us']");

    // ================= HELPERS =================
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

    private void clickAndReturn(By locator) {
        WebElement el = wait.until(ExpectedConditions.elementToBeClickable(locator));
        String current = driver.getCurrentUrl();
        safeClick(el);
        demoSleep(1000);
        driver.navigate().to(current); // balik ke page download
        wait.until(ExpectedConditions.visibilityOfElementLocated(pageTitle));
    }

    // ================= TYPE HELPER =================
    protected void type(By locator, String text) {
        WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        scrollIntoView(el);
        highlight(el);
        el.clear();
        el.sendKeys(text);
        demoSleep(400);
    }

    // ================= ACTIONS =================
    public void clickDownloadVersion() {
        clickAndReturn(downloadVersion);
    }

    public void clickReadDocumentation() {
        clickOpenNewTab(wait.until(ExpectedConditions.elementToBeClickable(readDocumentation)));
    }

    public void clickCheckSourceCode() {
        clickOpenNewTab(wait.until(ExpectedConditions.elementToBeClickable(checkSourceCode)));
    }

    public void clickPartnerAndReturn() {
        String currentUrl = driver.getCurrentUrl(); // simpan halaman download

        WebElement el = wait.until(ExpectedConditions.elementToBeClickable(contactPartner));
        safeClick(el); // klik link → buka tab baru

        demoSleep(1500);
        driver.navigate().to(currentUrl); // balik ke halaman download
        wait.until(ExpectedConditions.visibilityOfElementLocated(pageTitle));
        demoSleep(600);
    }

    public void fillContactForm() {

        WebElement lastNameEl = wait.until(
                ExpectedConditions.visibilityOfElementLocated(lastName));
        scrollIntoView(lastNameEl);

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
        driver.findElement(message).sendKeys("Need pricing information");

        WebElement submit = wait.until(ExpectedConditions.visibilityOfElementLocated(contactSubmit));

        // Nonaktifkan pointer events header fix
        ((JavascriptExecutor) driver).executeScript(
                "document.querySelectorAll('header, .oxy-header-center').forEach(e => e.style.pointerEvents='none');");

        // Scroll ke tengah layar
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});", submit);

        // Klik pakai JS
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submit);

        demoSleep(1500);

        // Kembalikan pointer-events header jika mau
        ((JavascriptExecutor) driver).executeScript(
                "document.querySelectorAll('header, .oxy-header-center').forEach(e => e.style.pointerEvents='auto');");

    }

    public void openTestimonials() {
        clickOpenNewTab(wait.until(ExpectedConditions.elementToBeClickable(browseTestimonials)));
    }

    public void exploreAndReturn() {
        String url = driver.getCurrentUrl();
        safeClick(wait.until(ExpectedConditions.elementToBeClickable(exploreNow)));
        demoSleep(1500);
        driver.navigate().to(url);
        wait.until(ExpectedConditions.visibilityOfElementLocated(pageTitle));
        demoSleep(600);
    }

    public void clickStartNow() {
        clickOpenNewTab(wait.until(ExpectedConditions.elementToBeClickable(startNow)));
    }

    public void fillNewsletter(String emailText) {
        scrollToFooter();
        demoSleep(800);

        // ===== INPUT EMAIL =====
        WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(newsletterEmail));
        highlight(emailInput);
        emailInput.clear();
        emailInput.sendKeys(emailText);
        demoSleep(500);

        // ===== CHECKBOX =====
        WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(newsletterCheckbox));
        if (!checkbox.isSelected()) {
            scrollIntoView(checkbox);
            highlight(checkbox);
            demoSleep(300);
            checkbox.click();
            demoSleep(300);
        }

        // ===== SUBMIT =====
        WebElement submit = wait.until(ExpectedConditions.visibilityOfElementLocated(newsletterSubmit));

        // Nonaktifkan pointer-events header / sticky element
        ((JavascriptExecutor) driver).executeScript(
                "document.querySelectorAll('header, .oxy-header-center').forEach(e => e.style.pointerEvents='none');");

        // Scroll ke tombol submit
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});", submit);

        // Klik pakai JS
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submit);

        demoSleep(1200);

        // Restore pointer-events header
        ((JavascriptExecutor) driver).executeScript(
                "document.querySelectorAll('header, .oxy-header-center').forEach(e => e.style.pointerEvents='auto');");
    }

    // ================= FULL FLOW =================
    public void testAllElementsComplete() {
        isPageVisible();

        clickDownloadVersion();
        clickReadDocumentation();
        clickCheckSourceCode();
        clickPartnerAndReturn();
        fillContactForm();
        openTestimonials();
        exploreAndReturn();
        clickStartNow();
        fillNewsletter("demo@test.com");
    }
}
