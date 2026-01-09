package com.proyek.pages.product;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.proyek.base.BasePage;

public class HelpdeskPage extends BasePage {

    private final String helpdeskUrl = "https://www.glpi-project.org/en/helpdesk-software/";

    public HelpdeskPage(WebDriver driver) {
        super(driver);
    }

    // ================= URL =================
    public void open() {
        driver.get(helpdeskUrl);
        acceptCookieIfPresent();
        demoSleep(1200);
    }

    // ================= LOCATORS =================
    private final By heroTitle = By.tagName("h1");

    private final Map<String, String> heroButtons = Map.of(
            "firstTicket", "open your first ticket",
            "startNowHero", "start now",
            "accessInstance", "access my glpi instance",
            "openMyInstance", "open my glpi instance");

    private final Map<String, String> ctaButtons = Map.of(
            "viewTestimonials", "View All Testimonials",
            "exploreNow", "Explore Now");

    private final By startNowFeaturesBtn = By.cssSelector(
            "a.ct-link-button.btn-primary[href*='register.php']:not(.btn-m)");

    private final By newsletterEmail = By.cssSelector("input[type='email']");
    private final By newsletterSubmit = By.cssSelector("input[type='submit']");
    private final By socialLinks = By.cssSelector("footer a[target='_blank'][href^='http']");

    // ================= VISIBILITY =================
    public boolean isHeroVisible() {
        return isVisible(heroTitle);
    }

    // ================= ACTIONS GENERIK =================
    private void clickButtonByText(String buttonText) {
        String main = driver.getWindowHandle();
        try {
            WebElement el = wait.until(ExpectedConditions.elementToBeClickable(
                    By.xpath("//a[contains(translate(.,'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'"
                            + buttonText.toLowerCase() + "')]")));
            scrollIntoView(el);
            highlight(el);
            demoSleep(600);

            // Klik pakai JS jika intercept
            try {
                el.click();
            } catch (ElementClickInterceptedException e) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
            }

            demoSleep(2000);
            closeNewTab(main);
        } catch (TimeoutException e) {
            System.out.println("Button not found: " + buttonText);
        }
    }

    public void clickHeroButton(String key) {
        if (heroButtons.containsKey(key)) {
            clickButtonByText(heroButtons.get(key));
        }
    }

    public void clickCtaButton(String key) {
        if (ctaButtons.containsKey(key)) {
            if ("exploreNow".equals(key)) {
                clickExploreNowAndReturn(ctaButtons.get(key));
            } else {
                clickButtonByText(ctaButtons.get(key));
            }
        }
    }

    private void clickExploreNowAndReturn(String buttonText) {
        String main = driver.getWindowHandle();
        WebElement el = driver.findElement(By.xpath("//a[contains(text(),'" + buttonText + "')]"));
        scrollIntoView(el);
        highlight(el);
        demoSleep(600);

        // Klik pakai JS jika intercept
        try {
            el.click();
        } catch (ElementClickInterceptedException e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", el);
        }

        demoSleep(2000);
        closeNewTab(main);

        // Kembali ke helpdesk
        driver.get(helpdeskUrl);
        demoSleep(1200);
    }

    // ================= TUTORIAL IFRAME =================
    public void clickTutorialIframe() {
        String mainWindow = driver.getWindowHandle();

        try {
            WebElement iframe = wait.until(ExpectedConditions.presenceOfElementLocated(
                    By.cssSelector("iframe[title*='Inventaire']")));
            driver.switchTo().frame(iframe);
            demoSleep(800);

            // Cari tombol "Discover the forms"
            try {
                WebElement discoverBtn = wait.until(ExpectedConditions.elementToBeClickable(
                        By.xpath("//button[.//span[contains(text(), 'Discover the forms')]]")));
                
                scrollIntoView(discoverBtn);
                highlight(discoverBtn);
                demoSleep(500);

                try {
                    discoverBtn.click();
                } catch (ElementClickInterceptedException e) {
                    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", discoverBtn);
                }
                demoSleep(1000);
                
            } catch (TimeoutException e) {
                System.out.println("Tombol 'Discover the forms' tidak ditemukan di iframe");
            }

        } catch (TimeoutException e) {
            System.out.println("Tutorial iframe tidak muncul");
        } finally {
            driver.switchTo().defaultContent();
            driver.switchTo().window(mainWindow);
        }
    }

    // ================= FEATURES =================
    public void clickStartNowFeatures() {
        String main = driver.getWindowHandle();
        try {
            WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(startNowFeaturesBtn));
            scrollIntoView(btn);
            highlight(btn);
            demoSleep(600);

            try {
                btn.click();
            } catch (ElementClickInterceptedException e) {
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", btn);
            }

            demoSleep(2000);
            closeNewTab(main);
        } catch (TimeoutException e) {
            System.out.println("Start Now Features button tidak ditemukan");
        }
    }

    // ================= NEWSLETTER =================
    public void fillNewsletter(String email) {
        scrollToFooter();
        demoSleep(800);

        WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(newsletterEmail));
        highlight(emailInput);
        emailInput.clear();
        emailInput.sendKeys(email);
        demoSleep(600);

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

                    try {
                        link.click();
                    } catch (ElementClickInterceptedException e) {
                        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", link);
                    }

                    demoSleep(2000);
                    closeNewTab(main);
                }
            } catch (Exception ignored) {
            }
        }
    }

    // ================= HELPERS =================
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

