package com.proyek.pages;

import com.proyek.base.BasePage;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.List;

public class HomePage extends BasePage {

    private final String homepageUrl = "https://www.glpi-project.org/en/";

    public HomePage(WebDriver driver) {
        super(driver);
    }

    // ================= URL =================
    public void open() {
        driver.get(homepageUrl);
        acceptCookieIfPresent();
        demoSleep(1200);
    }

    // ================= HERO =================
    private final By heroTitle = By.tagName("h1");
    private final By getStartedHeroBtn = By.xpath(
            "//a[contains(translate(.,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'get started')]");

    // ================= FEATURES =================
    private final By helpdeskCard = By.xpath(
            "//h3[normalize-space()='Helpdesk']/following::a[contains(.,'Get Started')][1]");
    private final By inventoryCard = By.xpath(
            "//h3[contains(normalize-space(),'Inventory')]/following::a[contains(.,'Get Started')][1]");
    private final By financialCard = By.xpath(
            "//h3[contains(normalize-space(),'Financial')]/following::a[contains(.,'Get Started')][1]");

    // ================= CTA =================
    private final By startNowBtn = By.xpath(
            "//a[contains(translate(.,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'start now')]");

    // ================= TESTIMONIAL & PARTNER =================
    private final By viewTestimonialsBtn = By.xpath(
            "//a[contains(text(),'View All Testimonials') or contains(text(),'View all testimonials')]");
    private final By discoverPartnersBtn = By.xpath(
            "//a[contains(text(),'Discover All') or contains(text(),'Discover all')]");

    // ================= NEWSLETTER =================
    private final By newsletterEmail = By.cssSelector("input[type='email']");
    private final By newsletterSubmit = By.cssSelector("input[type='submit']");
    // ================= SOCIAL =================
    private final By socialLinks = By.cssSelector("footer a[target='_blank'][href^='http']");

    // ================= VISIBILITY =================
    public boolean isHeroVisible() {
        return isVisible(heroTitle);
    }

    public boolean isGetStartedHeroVisible() {
        return isVisible(getStartedHeroBtn);
    }

    // ================= ACTIONS =================
    public void clickGetStartedHeroAndClose() {
        clickAndReturn(getStartedHeroBtn);
    }

    public void clickGetStartedHelpdeskAndClose() {
        clickAndReturn(helpdeskCard);
    }

    public void clickGetStartedInventoryAndClose() {
        clickAndReturn(inventoryCard);
    }

    public void clickGetStartedFinancialAndClose() {
        clickAndReturn(financialCard);
    }

    public void clickStartNowAndClose() {
        clickAndReturn(startNowBtn);
    }

    public void clickViewTestimonialsAndClose() {
        clickAndReturn(viewTestimonialsBtn);
    }

    public void clickDiscoverPartnersAndClose() {
        clickAndReturn(discoverPartnersBtn);
    }

    // ================= NEWSLETTER =================
    public void fillNewsletter(String email) {
        scrollToFooter();
        demoSleep(800);

        // isi email
        WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(newsletterEmail));
        highlight(emailInput);
        emailInput.clear();
        emailInput.sendKeys(email);
        demoSleep(600);

        // centang checkbox persetujuan jika belum dicentang
        By newsletterCheckbox = By.cssSelector("input[type='checkbox'][name*='checkbox']");
        try {
            WebElement checkbox = driver.findElement(newsletterCheckbox);
            if (!checkbox.isSelected()) {
                scrollIntoView(checkbox);
                highlight(checkbox);
                demoSleep(300);
                checkbox.click();
                demoSleep(300);
            }
        } catch (NoSuchElementException e) {
            System.out.println("Checkbox newsletter tidak ditemukan, lanjut submit");
        }

        // klik submit
        driver.findElement(newsletterSubmit).click();
        demoSleep(1000);
    }

    // ================= SOCIAL =================
    public void clickAllSocialMediaLinks() {
        scrollToFooter();
        demoSleep(800);

        String main = driver.getWindowHandle();
        List<WebElement> links = driver.findElements(socialLinks);

        for (WebElement link : links) {
            try {
                if (link.isDisplayed() && link.isEnabled()) {
                    scrollIntoView(link);
                    highlight(link);
                    demoSleep(600);
                    link.click();
                    demoSleep(2000);
                    closeNewTab(main);
                }
            } catch (Exception ignored) {
            }
        }
    }

    // ================= FULL DEMO FLOW =================
    public void testAllElementsComplete() {
        clickGetStartedHeroAndClose();
        clickGetStartedHelpdeskAndClose();
        clickGetStartedInventoryAndClose();
        clickGetStartedFinancialAndClose();
        clickViewTestimonialsAndClose();
        clickDiscoverPartnersAndClose();
        clickStartNowAndClose();

        fillNewsletter("demo@test.com"); // pastikan ada

        clickAllSocialMediaLinks(); // footer dihapus
    }

    // ================= HELPERS =================
    private void clickAndReturn(By locator) {
        String main = driver.getWindowHandle();
        WebElement el = driver.findElement(locator);
        scrollIntoView(el);
        highlight(el);
        demoSleep(600);
        el.click();
        demoSleep(2000);
        closeNewTab(main);
    }

    private void closeNewTab(String main) {
        try {
            for (String win : driver.getWindowHandles()) {
                if (!win.equals(main)) {
                    driver.switchTo().window(win);
                    demoSleep(1500);
                    driver.close();
                }
            }
            driver.switchTo().window(main);
            demoSleep(800);
        } catch (Exception e) {
            driver.switchTo().window(main);
        }
    }
}
