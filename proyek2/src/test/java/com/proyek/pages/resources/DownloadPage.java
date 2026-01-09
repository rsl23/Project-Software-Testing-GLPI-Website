package com.proyek.pages.resources;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.proyek.base.BasePage;

public class DownloadPage extends BasePage {

    private final String downloadUrl = "https://www.glpi-project.org/en/downloads/";

    public DownloadPage(WebDriver driver) {
        super(driver);
    }

    // ================= OPEN =================
    public void open() {
        driver.get(downloadUrl);
        acceptCookieIfPresent();
        waitForPageLoad();
    }

    // ================= PAGE TITLE =================
    private final By pageTitle = By.xpath("//h1[contains(.,'Download')]");

    public boolean isPageVisible() {
        return isVisible(pageTitle);
    }

    // ================= LINKS / BUTTONS =================
    private final By downloadVersion = By.xpath("//a[.//div[contains(text(),'Download version')]]");
    private final By readDocumentation = By.linkText("Read the documentation");
    private final By checkSourceCode = By.linkText("Check the source code");
    private final By contactPartner = By.linkText("Contact one of our partners");
    private final By videoPlayButton = By.cssSelector("div.play");
    private final By browseTestimonials = By.cssSelector("a[href*='youtube.com/playlist']");
    private final By exploreNow = By.cssSelector("section a[href*='/features/']");
    private final By startNow = By.xpath("//a[contains(text(),'Start Now')]");

    public final By newsletterEmail = By.cssSelector("input[name='email']");
    private final By newsletterCheckbox = By.cssSelector("input[type='checkbox'][name='checkbox-75[]']");
    private final By newsletterSubmit = By.xpath("//input[@type='submit' and @value='Subscribe']");

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
        demoSleep(1000);
        for (String win : driver.getWindowHandles()) {
            if (!win.equals(main)) {
                driver.switchTo().window(win);
                waitForPageLoad();
                driver.close();
            }
        }
        driver.switchTo().window(main);
        waitForPageLoad();
    }

    private void clickAndReturn(By locator) {
        try {
            WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            String current = driver.getCurrentUrl();
            safeClick(el);
            demoSleep(500);
            driver.navigate().to(current); // balik ke page
            waitForPageLoad();
        } catch (TimeoutException e) {
            System.out.println("Element " + locator + " tidak muncul, skip action ini.");
        }
    }

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
        try {
            WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(readDocumentation));
            clickOpenNewTab(el);
        } catch (TimeoutException e) {
            System.out.println("Read Documentation link tidak muncul, skip.");
        }
    }

    public void clickCheckSourceCode() {
        try {
            WebElement el = wait.until(ExpectedConditions.visibilityOfElementLocated(checkSourceCode));
            clickOpenNewTab(el);
        } catch (TimeoutException e) {
            System.out.println("Check Source Code link tidak muncul, skip.");
        }
    }

    public void clickPartnerAndReturn() {
        clickAndReturn(contactPartner);
    }

    public void clickVideoPlay() {
        try {
            WebElement playBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(videoPlayButton));
            scrollIntoView(playBtn);
            highlight(playBtn);
            demoSleep(500);
            
            try {
                playBtn.click();
            } catch (Exception e) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", playBtn);
            }
            
            demoSleep(2000); // tunggu video load
            System.out.println("Video play button clicked successfully.");
        } catch (TimeoutException e) {
            System.out.println("Video play button tidak muncul, skip action ini.");
        }
    }

    public void fillContactForm() {
        type(lastName, "Doe");
        type(firstName, "John");
        type(company, "ACME Corp");
        type(country, "Indonesia");
        type(email, "john.doe@test.com");
        type(phone, "08123456789");
        type(message, "Need pricing information");

        WebElement submit = wait.until(ExpectedConditions.visibilityOfElementLocated(contactSubmit));

        // Nonaktifkan sticky header
        ((JavascriptExecutor) driver).executeScript(
                "document.querySelectorAll('header, .oxy-header-center').forEach(e => e.style.pointerEvents='none');");

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", submit);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submit);

        demoSleep(1500);

        // Restore header
        ((JavascriptExecutor) driver).executeScript(
                "document.querySelectorAll('header, .oxy-header-center').forEach(e => e.style.pointerEvents='auto');");
    }

    public void openTestimonials() {
        try {
            WebElement btn = wait.until(ExpectedConditions.visibilityOfElementLocated(browseTestimonials));
            scrollIntoView(btn);
            highlight(btn);

            // Nonaktifkan header overlay
            ((JavascriptExecutor) driver).executeScript(
                    "document.querySelectorAll('header, .oxy-header-center').forEach(e => e.style.pointerEvents='none');");

            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);

            String main = driver.getWindowHandle();
            for (String win : driver.getWindowHandles()) {
                if (!win.equals(main)) {
                    driver.switchTo().window(win);
                    waitForPageLoad();
                    driver.close();
                }
            }
            driver.switchTo().window(main);
            waitForPageLoad();

            ((JavascriptExecutor) driver).executeScript(
                    "document.querySelectorAll('header, .oxy-header-center').forEach(e => e.style.pointerEvents='auto');");

        } catch (TimeoutException e) {
            System.out.println("Browse Testimonials button tidak muncul, skip action ini.");
        }
    }

    public void exploreAndReturn() {
        clickAndReturn(exploreNow);
    }

    public void clickStartNow() {
        try {
            WebElement btn = wait.until(ExpectedConditions.visibilityOfElementLocated(startNow));
            clickOpenNewTab(btn);
        } catch (TimeoutException e) {
            System.out.println("Start Now button tidak muncul, skip.");
        }
    }

    public void fillNewsletter(String emailText) {
        scrollToFooter();
        demoSleep(500);

        WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(newsletterEmail));
        highlight(emailInput);
        emailInput.clear();
        emailInput.sendKeys(emailText);

        WebElement checkbox = wait.until(ExpectedConditions.elementToBeClickable(newsletterCheckbox));
        if (!checkbox.isSelected()) {
            scrollIntoView(checkbox);
            highlight(checkbox);
            demoSleep(200);
            checkbox.click();
        }

        WebElement submit = wait.until(ExpectedConditions.visibilityOfElementLocated(newsletterSubmit));

        ((JavascriptExecutor) driver).executeScript(
                "document.querySelectorAll('header, .oxy-header-center').forEach(e => e.style.pointerEvents='none');");
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", submit);
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", submit);
        demoSleep(1000);
        ((JavascriptExecutor) driver).executeScript(
                "document.querySelectorAll('header, .oxy-header-center').forEach(e => e.style.pointerEvents='auto');");
    }

    // ================= HELPER =================
    public void waitForPageLoad() {
        wait.until(webDriver -> ((JavascriptExecutor) webDriver)
                .executeScript("return document.readyState").equals("complete"));
    }
}

